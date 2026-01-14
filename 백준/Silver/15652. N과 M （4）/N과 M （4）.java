import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * N과 M (4)
 *
 * [문제]
 * - 1부터 N까지 자연수 중에서 M개를 고른 수열
 * - 같은 수를 여러 번 골라도 된다.
 * - 고른 수열은 비내림차순이어야 한다.
 *
 * [풀이]
 * 중복조합
 */
public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int n, m;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[m];

        bfs(1, 0);

        System.out.println(sb.toString());
    }

    static void bfs(int start, int length) {
        if (length >= m) {
            for (int idx = 0; idx < m; idx++) {
                sb.append(arr[idx]).append(" ");
            }

            sb.append("\n");
            return;
        }

        for (int idx = start; idx <= n; idx++) {
            arr[length] = idx;
            bfs(idx, length+1);
        }
    }
}
