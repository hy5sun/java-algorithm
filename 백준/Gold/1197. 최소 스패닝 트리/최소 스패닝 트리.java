import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int vertexCnt, edgeCnt, weight;
    static int[][] edges;
    static int[] parents;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        vertexCnt = Integer.parseInt(st.nextToken());
        edgeCnt = Integer.parseInt(st.nextToken());

        make();

        edges = new int[edgeCnt][3];

        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            edges[idx] = new int[] {vertex1, vertex2, weight};
        }

        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));

        for (int[] edge : edges) {
            int vertex1 = edge[0];
            int vertex2 = edge[1];

            if (find(vertex1) != find(vertex2)) {
                union(vertex1, vertex2);
                weight += edge[2];
            }
        }

        System.out.println(weight);
    }

    private static void make() {
        parents = new int[vertexCnt+1];
        rank = new int[vertexCnt+1];

        for (int idx = 1; idx <= vertexCnt; idx++) {
            parents[idx] = idx;
        }
    }

    private static int find(int element) {
        if (element == parents[element]) return element;
        return parents[element] = find(parents[element]);
    }

    private static void union(int elementA, int elementB) {
        int parentA = find(elementA);
        int parentB = find(elementB);

        if (parentA == parentB) return;

        if (rank[parentA] > rank[parentB]) {
            parents[parentB] = parentA;
            return;
        }

        parents[parentA] = parentB;

        if (rank[parentA] == rank[parentB]) {
            rank[parentB]++;
        }
    }
}
