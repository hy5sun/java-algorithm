import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int cityCnt, travelCityCnt;
    static int[] parents;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        cityCnt = Integer.parseInt(br.readLine());
        travelCityCnt = Integer.parseInt(br.readLine());

        make();

        for (int cityIdx = 1; cityIdx <= cityCnt; cityIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int otherIdx = 1; otherIdx <= cityCnt; otherIdx++) {
                int operator = Integer.parseInt(st.nextToken());

                if (cityIdx != otherIdx && operator == 1) {
                    union(cityIdx, otherIdx);
                }
            }
        }

        st = new StringTokenizer(br.readLine().trim());
        int[] travel = new int[travelCityCnt];
        for (int idx = 0; idx < travelCityCnt; idx++) {
            travel[idx] = Integer.parseInt(st.nextToken());
        }

        for (int cityIdx = 0; cityIdx < travelCityCnt - 1; cityIdx++) {
            int from = travel[cityIdx];
            int to = travel[cityIdx+1];

            if (find(from) != find(to)) {
                System.out.println(sb.append("NO").toString());
                System.exit(0);
            }
        }

        System.out.println(sb.append("YES").toString());
    }

    private static void make() {
        parents = new int[cityCnt+1];
        rank = new int[cityCnt+1];

        for (int idx = 1; idx <= cityCnt; idx++)
            parents[idx] = idx;
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
