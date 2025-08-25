import java.io.*;
import java.util.*;

/**
 * BJ13023. ABCDE
 *
 * [문제]
 * - 사람 번호 : 0 ~ N-1 번
 * - A 와 B는 친구, B와 C는 친구, C와 D는 친구, D와 E는 친구 관계가 성립하면 1, 성립하지 않으면 0
 *
 * [풀이]
 * 1. 입력 받기
 *  - 사람 수 N, 친구 관계 수 M
 *  - 관계
 *      - 인접 리스트로 저장한다.
 *
 * 2. DFS 사용 0부터 했을 때 깊이가 5이면 1을 반환하고 종료한다.
 *
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static List<List<Integer>> relationship = new ArrayList<>();
    static int peopleCnt, relationshipCnt, answer;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // *  - 사람 수 N, 친구 관계 수 M
        // *  - 관계
        // *      - 인접 리스트로 저장한다.
        st = new StringTokenizer(br.readLine().trim());

        peopleCnt = Integer.parseInt(st.nextToken());
        relationshipCnt = Integer.parseInt(st.nextToken());

        answer = 0;

        // 인접 리스트 초기화
        for (int idx = 0; idx < peopleCnt; idx++)
            relationship.add(new ArrayList<>());

        for (int idx = 0; idx < relationshipCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            relationship.get(a).add(b);
            relationship.get(b).add(a);
        }

        for (int idx = 0; idx < peopleCnt; idx++) {
            visited = new boolean[peopleCnt];
            visited[idx] = true;
            dfs(idx, 1);
        }

        sb.append(0);
        System.out.println(sb.toString());
    }

    // 2. DFS 사용 0부터 했을 때 깊이가 5이면 1을 반환하고 종료한다.
    private static void dfs(int personIdx, int depth) {
        // 조건을 만족하는 A, B, C, D, E를 찾으면 정답 갱신 및 종료 (깊이가 5면)
        if (depth == 5) {
            sb.append(1);
            System.out.println(sb.toString());
            System.exit(0);
        }

        List<Integer> nextPeople = relationship.get(personIdx);

        for (int nextPersonIdx : nextPeople) {
            if (!visited[nextPersonIdx]) {
                visited[nextPersonIdx] = true;
                dfs(nextPersonIdx, depth + 1);
                visited[nextPersonIdx] = false;
            }
        }
    }
}
