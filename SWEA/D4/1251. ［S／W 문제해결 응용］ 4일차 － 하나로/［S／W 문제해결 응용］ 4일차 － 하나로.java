import java.util.*;
import java.io.*;

/**
 * SW1251. 하나로
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
 * 2. 인접 행렬에 간선별 환경 부담금을 저장한다.
 * 
 * 3. 첫 번째 섬에서부터 시작한다.
 *  - 첫 번째 섬을 큐에 넣는다.
 *  - 첫 번째 섬을 방문 처리한다.
 *  - 첫 번째 섬과 연결되어 있는 간선을 조회한다.
 *  - 연결되어 있는 섬을 우선순위 큐에 추가한다.
 *  - 또 꺼내서 방문 여부 확인하고, 방문 처리하고, 비용값에 더하고, 연결되어 있는 섬을 조회하는 과정을 큐가 빌 때까지 반복한다.
 * 
 * 4. 최소 비용을 출력한다.
 */
public class Solution {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int islandCnt;
    static int[][] islandLocation;
    static double tax;
    static double[][] costs;
    static double totalTax;

    static class Node {
        int islandIdx;
        double cost;

        public Node(int islandIdx, double cost) {
            this.islandIdx = islandIdx;
            this.cost = cost;
        }
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return Double.compare(n1.cost, n2.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 섬의 개수 N
            islandCnt = Integer.parseInt(br.readLine());

            islandLocation = new int[islandCnt][2];

            costs = new double[islandCnt][islandCnt];

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

            // 2. 인접 행렬에 간선별 환경 부담금을 저장한다.
            for (int idx = 0; idx < islandCnt-1; idx++) {
                for (int nextIdx = idx + 1; nextIdx < islandCnt; nextIdx++) {
                    double length = getLength(idx, nextIdx);
                    double cost = calculateTax(length);

                    costs[idx][nextIdx] = cost;
                    costs[nextIdx][idx] = cost;
                }
            }

            totalTax = 0;

            // 3. 첫 번째 섬에서부터 시작한다.
            connect(0);

            sb.append("#").append(testCase).append(" ").append(Math.round(totalTax)).append("\n");
        }

        //  4. 최소 비용을 출력한다.
        System.out.println(sb.toString());
    }

    // 3. 첫 번째 섬에서부터 시작한다.
    // *  - 첫 번째 섬을 큐에 넣는다.
    // *  - 첫 번째 섬을 방문처리한다.
    // *  - 첫 번째 섬과 연결되어 있는 간선을 조회한다.
    // *  - 연결되어 있는 섬을 우선순위 큐에 추가한다.
    // *  - 또 꺼내서 방문 여부 확인하고, 방문 처리하고, 비용값에 더하고, 연결되어 있는 섬을 조회하는 과정을 큐가 빌 때까지 반복한다.
    private static void connect(int islandIdx) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        boolean[] visited = new boolean[islandCnt];

        visited[islandIdx] = true;

        for (int nextIsland = 0; nextIsland < islandCnt; nextIsland++) {
            if (costs[islandIdx][nextIsland] != 0) {
                queue.offer(new Node(nextIsland, costs[islandIdx][nextIsland]));
            }
        }

        while (!queue.isEmpty()) {
            Node nextisland = queue.poll();

            int currentIdx = nextisland.islandIdx;

            if (visited[currentIdx]) continue;

            visited[currentIdx] = true;
            totalTax += nextisland.cost;

            for (int nextIsland = 0; nextIsland < islandCnt; nextIsland++) {
                if (costs[currentIdx][nextIsland] != 0) {
                    queue.offer(new Node(nextIsland, costs[currentIdx][nextIsland]));
                }
            }
        }
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
