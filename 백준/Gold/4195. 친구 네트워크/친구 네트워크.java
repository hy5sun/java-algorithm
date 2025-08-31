import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, relationshipCnt;
    static Network[] relationship;
    static int[] parents;
    static int[] rank;
    static int[] networkCnt;
    static Map<String, Integer> users = new HashMap<>();

    public static void main(String[] args) throws IOException {
        testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            relationshipCnt = Integer.parseInt(br.readLine());

            relationship = new Network[relationshipCnt];
            users.clear();

            int userId = 0;
            for (int idx = 0; idx < relationshipCnt; idx++) {
                st = new StringTokenizer(br.readLine().trim());

                String userA = st.nextToken();
                String userB = st.nextToken();

                if (!users.containsKey(userA)) users.put(userA, userId++);
                if (!users.containsKey(userB)) users.put(userB, userId++);

                relationship[idx] = new Network(userA, userB);
            }

            make();

            for (int idx = 0; idx < relationshipCnt; idx++) {
                Network network = relationship[idx];

                int userAIdx = users.get(network.userA);
                int userBIdx = users.get(network.userB);
                
                union(userAIdx, userBIdx);

                sb.append(networkCnt[find(userAIdx)]).append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    private static void make() {
        parents = new int[users.size()];
        rank = new int[users.size()];
        networkCnt = new int[users.size()];

        for (int idx = 0; idx < users.size(); idx++) {
            parents[idx] = idx;
            networkCnt[idx] = 1;
        }
    }

    private static int find(int user) {
        if (user == parents[user]) return user;
        return parents[user] = find(parents[user]);
    }

    private static void union(int userA, int userB) {
        int parentA = find(userA);
        int parentB = find(userB);

        if (parentA == parentB) {
            return;
        }

        if (rank[parentA] > rank[parentB]) {
            parents[parentB] = parentA;
            networkCnt[parentA] += networkCnt[parentB];
            return;
        }

        parents[parentA] = parentB;
        networkCnt[parentB] += networkCnt[parentA];

        if (rank[parentA] == rank[parentB]) rank[parentB]++;
    }

    static class Network {
        String userA, userB;

        public Network(String userA, String userB) {
            this.userA = userA;
            this.userB = userB;
        }
    }
}
