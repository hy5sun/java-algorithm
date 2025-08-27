
import java.util.*;
import java.io.*;

/**
 * SW3124. 최소 스패닝 트리
 * 
 * @author 김효선
 * 
 * [문제]
 * - 최소 스패닝 트리 구하기
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 정점의 개수 V , 간선의 개수 E
 *  - E개 만큼 A 정점, B 정점, C 가중치
 *      - 인접 리스트에 저장하기
 * 
 * 2. 1부터 시작해서 Prim 알고리즘을 실행한다.
 *  - 1 방문처리 하기
 *  - 1과 연결된 다른 정점들 찾아서 큐에 넣기
 *  - 큐가 빌 때까지 다음을 반복
 *      - 큐에서 맨 앞 요소 꺼내기
 *      - 방문 여부 확인하기
 *          - 방문 했으면 중지, 안 했으면 다음을 진행
 *      - 방문 처리하기
 *      - 가중치 더하기
 *      - 현재 정점과 연결되어 있는 다른 정점을 찾아서 큐에 넣기
 * 
 * 3. 가중치 출력하기
 * 
 */
public class Solution {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int vertexCnt, edgeCnt;
    static List<List<Node>> edges;
    static long totalWeight;

    static class Node {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return n1.weight - n2.weight;
        }
    }

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // - 테스트 케이스 수
        int testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 정점의 개수 V , 간선의 개수 E
            st = new StringTokenizer(br.readLine().trim());
            vertexCnt = Integer.parseInt(st.nextToken());
            edgeCnt = Integer.parseInt(st.nextToken());

            edges = new ArrayList<>();

            for (int idx = 0; idx <= vertexCnt; idx++)
                edges.add(new ArrayList<>());

            // - E개 만큼 A 정점, B 정점, C 가중치
            //     - 인접 리스트 저장하기
            for (int idx = 0; idx < edgeCnt; idx++) {
                st = new StringTokenizer(br.readLine().trim());

                int vertex1 = Integer.parseInt(st.nextToken());
                int vertex2 = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edges.get(vertex1).add(new Node(vertex2, weight));
                edges.get(vertex2).add(new Node(vertex1, weight));
            }

            totalWeight = 0;

            // 2. 1부터 시작해서 Prim 알고리즘을 실행한다.
            prim(1);

            // 3. 가중치 출력하기
            sb.append("#").append(testCase).append(" ").append(totalWeight).append("\n");
        }

        System.out.println(sb.toString());
    }
    
    // 2. 1부터 시작해서 Prim 알고리즘을 실행한다.
    //    *  - 1 방문처리 하기
    //    *  - 1과 연결된 다른 정점들 찾아서 큐에 넣기
    //    *  - 큐가 빌 때까지 다음을 반복
    //    *      - 큐에서 맨 앞 요소 꺼내기
    //    *      - 방문 여부 확인하기
    //    *          - 방문 했으면 중지, 안 했으면 다음을 진행
    //    *      - 방문 처리하기
    //    *      - 가중치 더하기
    //    *      - 현재 정점과 연결되어 있는 다른 정점을 찾아서 큐에 넣기
    private static void prim(int startIdx) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        boolean[] visited = new boolean[vertexCnt+1];

        visited[startIdx] = true;

        List<Node> nodes = edges.get(startIdx);
        for (Node node : nodes)
            queue.offer(new Node(node.vertex, node.weight));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            int currentIdx = current.vertex;
            
            if (visited[currentIdx]) continue;

            visited[currentIdx] = true;
            totalWeight += current.weight;

            nodes = edges.get(currentIdx);
            for (Node node : nodes)
                queue.offer(new Node(node.vertex, node.weight));
        }
    }
}
