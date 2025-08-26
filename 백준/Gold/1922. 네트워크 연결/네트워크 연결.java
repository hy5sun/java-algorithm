import java.util.*;
import java.io.*;

/**
 * BJ1922. 네트워크 연결
 * 
 * 
 * [문제]
 * - 컴퓨터와 컴퓨터를 모두 연결한다.
 * - 각 컴퓨터를 연결하는 데 필요한 비용이 주어졌을 때, 최소 비용 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 컴퓨터 수 (정점)
 *  - 연결할 수 있는 선의 수 (간선)
 *  - 컴퓨터 연결하는 데 드는 비용 (컴a, 컴b, 비용)
 *      - 배열에 저장한다.
 * 
 * 2. 크루스칼 알고리즘 사용하기
 *  2-1. 비용을 기준으로 오름차순 정렬하기
 *  2-2. 같은 부모를 가지고 있는지 확인하기
 *      - 같은 부모 가지고 있으면 중지
 *      - 다른 부모면 union find 해서 합치기
 *      - 그리고 비용 더하기
 * 
 * 3. 총 비용 출력하기
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int computerCnt, connectionCnt, totalCost;
    static int[][] edges;
    static int[] parent;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 컴퓨터 수 (정점)
        computerCnt = Integer.parseInt(br.readLine());

        // *  - 연결할 수 있는 선의 수 (간선)
        connectionCnt = Integer.parseInt(br.readLine());

        edges = new int[connectionCnt][3];
        totalCost = 0;

        // *  - 컴퓨터 연결하는 데 드는 비용 (컴a, 컴b, 비용)
        //          - 배열에 저장한다.
        for (int idx = 0; idx < connectionCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int computerA = Integer.parseInt(st.nextToken());
            int computerB = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[idx] = new int[]{computerA, computerB, cost};
        }

        // 2-1. 비용을 기준으로 오름차순 정렬하기
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));

        make();

        for (int[] edge : edges) {
            // 2-2. 같은 부모를 가지고 있는지 확인하기
            int computerAParent = find(edge[0]);
            int cumputerBParent = find(edge[1]);

            // - 같은 부모 가지고 있으면 중지
            if (computerAParent == cumputerBParent) continue;

            // - 다른 부모면 union find 해서 합치기
            union(edge[0], edge[1]);

            totalCost += edge[2];
        }

        // 3. 총 비용 출력하기
        System.out.println(sb.append(totalCost).toString());
    }

    private static void make() {
        parent = new int[computerCnt+1];
        rank = new int[computerCnt+1];

        for (int idx = 1; idx <= computerCnt; idx++)
            parent[idx] = idx;
    }

    private static int find(int element) {
        if (parent[element] == element) return element;

        return find(parent[element] = find(parent[element]));
    }

    private static void union(int element1, int element2) {
        int element1Parent = find(element1);
        int element2Parent = find(element2);

        if (element1Parent == element2Parent) return;

        if (rank[element1Parent] > rank[element2Parent]) {
            parent[element2Parent] = element1Parent;
            return;
        }

        parent[element1Parent] = element2Parent;

        if (rank[element1Parent] == rank[element2Parent])
            rank[element2Parent]++;
    }
}
