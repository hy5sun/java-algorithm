import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, S, answer;
    static int[] numbers;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        numbers = new int[N];
        visited = new boolean[N];

        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < N; idx++) {
            numbers[idx] = Integer.parseInt(st.nextToken());
        }

        subset(0);

        System.out.println(answer);
    }

    private static void subset(int idx) {
        if (idx == N) {
            int sum = 0, elementCnt = 0;
            for (int i = 0; i < N; i++) {
                if (visited[i]) {
                    sum += numbers[i];
                    elementCnt++;
                }
            }
            if (sum == S && elementCnt > 0) {
                answer++;
            }
            return;
        }

        visited[idx] = true;
        subset(idx+1);

        visited[idx] = false;
        subset(idx+1);
    }
}
