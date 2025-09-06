import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int problemCnt, infoCnt;
    static int[] inFrontOfMeCnt;
    static List<List<Integer>> behindOfMe = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        problemCnt = Integer.parseInt(st.nextToken());
        infoCnt = Integer.parseInt(st.nextToken());

        inFrontOfMeCnt = new int[problemCnt+1];

        for (int idx = 0; idx <= problemCnt; idx++)
            behindOfMe.add(new ArrayList<>());

        for (int idx = 0; idx < infoCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int problem1Idx = Integer.parseInt(st.nextToken());
            int problem2Idx = Integer.parseInt(st.nextToken());

            behindOfMe.get(problem1Idx).add(problem2Idx);
            inFrontOfMeCnt[problem2Idx]++;
        }

        solve();

        System.out.println(sb.toString());
    }

    private static void solve() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int idx = 1; idx <= problemCnt; idx++) {
            if (inFrontOfMeCnt[idx] == 0) {
                queue.offer(idx);
            }
        }

        while (!queue.isEmpty()) {
            int currentIdx = queue.poll();

            sb.append(currentIdx).append(" ");

            List<Integer> nextProblems = behindOfMe.get(currentIdx);

            for (int next : nextProblems) {
                inFrontOfMeCnt[next]--;

                if (inFrontOfMeCnt[next] == 0) {
                    queue.offer(next);
                }
            }
        }
    }
}
