import java.util.*;
import java.io.*;

/**
 * SW1251. 하나로
 * 
 * 
 * [문제]
 * - N개의 섬들을 연결.
 * - 환경 부담 세율 (E) * 해저 터널 길이 (L) ^ 2
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 섬의 개수 N
 *  - 각 섬의 X좌표
 *  - 각 섬의 Y좌표
 *  - 해저터널 건설의 환경 부담 세율 실수 E
 * 
 * 2. 간선별 환경 부담금을 저장한다.
 * 
 * 3. 2번에서 만든 거 오름차순 정렬
 * 
 * 4. rank, parent 배열 초기화
 * 
 * 5. 2번에서 저장한 배열을 하나씩 돌려보는데,
 *  - 2개의 섬에 대해 부모가 동일한지 확인한다.
 *      - 동일하면 pass
 *      - 동일하지 않으면
 *          - union 작업하기
 *          - 비용 더하기
 * 
 * 6. 최소 비용을 출력한다.
 */
public class Solution {
        static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int islandCnt;
    static int[][] islandLocation;
    static double tax;
    static double[][] costs;

    static int[] parent;
    static int[] rank;
    static double totalTax;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 섬의 개수 N
            islandCnt = Integer.parseInt(br.readLine());

            islandLocation = new int[islandCnt][2];
            costs = new double[islandCnt * (islandCnt - 1) / 2][3];

            // - 각 섬의 X좌표
            st = new StringTokenizer(br.readLine().trim());
            for (int idx = 0; idx < islandCnt; idx++)
                islandLocation[idx][0] = Integer.parseInt(st.nextToken());

            // - 각 섬의 Y좌표
            st = new StringTokenizer(br.readLine().trim());
            for (int idx = 0; idx < islandCnt; idx++)
                islandLocation[idx][1] = Integer.parseInt(st.nextToken());

            // - 해저터널 건설의 환경 부담 세율 실수 E
            tax = Double.parseDouble(br.readLine());

            // 2. 간선별 환경 부담금을 저장한다.
            int costIdx = 0;

            for (int idx = 0; idx < islandCnt-1; idx++) {
                for (int nextIdx = idx + 1; nextIdx < islandCnt; nextIdx++) {
                    costs[costIdx][0] = idx;
                    costs[costIdx][1] = nextIdx;

                    double length = getLength(idx, nextIdx);
                    costs[costIdx][2] = calculateTax(length);

                    costIdx++;
                }   
            }

            // 3. 2번에서 만든 거 오름차순 정렬
            Arrays.sort(costs, Comparator.comparingDouble(c -> c[2]));

            totalTax = 0;
            
            // 4. rank, parent 배열 초기화
            make();

            // 5. 2번에서 저장한 배열을 하나씩 돌려보는데,
            // - 2개의 섬에 대해 부모가 동일한지 확인한다.
            //     - 동일하면 pass
            //     - 동일하지 않으면
            //         - union 작업하기
            //         - 비용 더하기
            for (double[] cost : costs) {
                int costAParent = find((int) cost[0]);
                int costBParent = find((int) cost[1]);

                if (costAParent == costBParent) continue;

                union((int) cost[0], (int)cost[1]);

                totalTax += cost[2];
            }

            totalTax = Math.round(totalTax);

            sb.append("#").append(testCase).append(" ").append(Math.round(totalTax)).append("\n");
        }

        //  6. 최소 비용을 출력한다.
        System.out.println(sb.toString());
    }

    private static void make() {
        parent = new int[islandCnt];
        rank = new int[islandCnt];

        for (int idx = 0; idx < islandCnt; idx++)
            parent[idx] = idx;
    }

    private static int find(int element) {
        if (element == parent[element]) return element;

        return parent[element] = find(parent[element]);
    }

    private static void union(int elementA, int elementB) {
        int parentA = find(elementA);
        int parentB = find(elementB);

        if (parentA == parentB) return;

        if (rank[parentA] > rank[parentB]) {
            parent[parentB] = parentA;
            return;
        }

        parent[parentA] = parentB;

        if (rank[parentA] == rank[parentB])
            rank[parentB]++;
    }

    // 환경 부담 세율 (E) * 해저 터널 길이 (L) ^ 2
    private static double calculateTax(double length) {
        return tax * length;
    } 

    // 두 섬 간의 거리를 계산한다.
    private static double getLength(int idx1, int idx2) {
        double dx = Math.abs(islandLocation[idx1][0] - islandLocation[idx2][0]);
        double dy = Math.abs(islandLocation[idx1][1] - islandLocation[idx2][1]);
        return dx * dx + dy * dy;
    }
}
