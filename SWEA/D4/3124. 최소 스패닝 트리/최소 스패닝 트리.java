import java.util.*;
import java.io.*;

/**
 * SW3124. 최소 스패닝 트리
 * 
 * 
 * [문제]
 * - 최소 스패닝 트리 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 정점의 개수 V , 간선의 개수 E
 *  - E개 만큼 A 정점, B 정점, C 가중치
 *      - 2차원 배열에 저장하기
 * 
 * 2. 오름차순 정렬하기
 * 
 * 3. rank, parent 초기화하기
 * 
 * 4. 저장한 간선 개수만큼 돌기
 *  - 부모가 같은지 확인
 *      - 같으면 멈추기
 *      - 다르면 합치고, 가중치 더하기
 */
public class Solution {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int vertexCnt, edgeCnt;
    static int[][] edges;
    static long totalWeight;

    static int[] parent;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 정점의 개수 V , 간선의 개수 E
            st = new StringTokenizer(br.readLine().trim());
            vertexCnt = Integer.parseInt(st.nextToken());
            edgeCnt = Integer.parseInt(st.nextToken());

            edges = new int[edgeCnt][3];

            // - E개 만큼 A 정점, B 정점, C 가중치
            //     - 2차원 배열에 저장하기
            for (int idx = 0; idx < edgeCnt; idx++) {
                st = new StringTokenizer(br.readLine().trim());

                int vertex1 = Integer.parseInt(st.nextToken());
                int vertex2 = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edges[idx] = new int[]{vertex1, vertex2, weight};
            }

            // 2. 오름차순 정렬하기
            Arrays.sort(edges, Comparator.comparingInt(edge -> edge[2]));

            totalWeight = 0;

            // 3. rank, parent 초기화하기
            make();

            // 4. 저장한 간선 개수만큼 돌기
            //    *  - 부모가 같은지 확인
            //    *      - 같으면 멈추기
            //    *      - 다르면 합치고, 가중치 더하기
            for (int[] edge : edges) {
                int parentA = find(edge[0]);
                int parentB = find(edge[1]);

                if (parentA == parentB) continue;

                union(edge[0], edge[1]);

                totalWeight += edge[2];
            }

            sb.append("#").append(testCase).append(" ").append(totalWeight).append("\n");
        }

        System.out.println(sb.toString());
    }
    
    private static void make() {
        parent = new int[vertexCnt+1];
        rank = new int[vertexCnt+1];

        for (int idx = 1; idx <= vertexCnt; idx++)
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
}
