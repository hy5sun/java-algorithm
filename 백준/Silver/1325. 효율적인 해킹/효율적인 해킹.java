import java.io.*;
import java.util.*;

/**
 * BJ1325. 효율적인 해킹
 * 
 * [문제]
 * - N개의 컴퓨터로 이루어짐
 * - 한 번의 해킹으로 여러 개의 컴퓨터를 해킹 가능
 * - 신뢰하는 관계와 신뢰하지 않은 관계로 존재. A가 B를 신뢰하는 경우, B를 해킹하면 A도 해킹 가능
 * - 한 번에 가장 많은 컴퓨터를 해킹할 수 이쓴 컴퓨터의 번호를 출력
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 컴퓨터 개수, 신뢰 관계 수
 * 
 * 2. 인접 리스트로 DFS
 * 
 * 3. 자신을 가장 많이 연결하고 있는 컴퓨터의 번호를 순서대로 출력함
 * 
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int computerCnt, relationCnt, maxDepth;
    static boolean[] visited;
    static int[] depths;
    static List<List<Integer>> canBelieve = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        computerCnt = Integer.parseInt(st.nextToken());
        relationCnt = Integer.parseInt(st.nextToken());

        for (int idx = 0; idx <= computerCnt; idx++) {
            canBelieve.add(new ArrayList<>());
        }

        for (int idx = 0; idx < relationCnt; idx++) {
            st = new StringTokenizer(br.readLine());

            int computer1 = Integer.parseInt(st.nextToken());
            int computer2 = Integer.parseInt(st.nextToken());

            canBelieve.get(computer2).add(computer1);
        }

        maxDepth = Integer.MIN_VALUE;
        depths = new int[computerCnt+1];

        for (int idx = 1; idx <= computerCnt; idx++) {
            visited = new boolean[computerCnt+1];
            depths[idx] = dfs(idx);
            maxDepth = Math.max(depths[idx], maxDepth);
        }

        for (int idx = 1; idx <= computerCnt; idx++) {
            if (maxDepth == depths[idx])
                sb.append(idx).append(" ");
        }

        System.out.println(sb.toString());
    }

    private static int dfs(int idx) {
        visited[idx] = true;
        int cnt = 1;

        List<Integer> next = canBelieve.get(idx);

        for (int nextComputer : next) {
            if (!visited[nextComputer]) {
                cnt += dfs(nextComputer);
            }
        }

        return cnt;
    }
}