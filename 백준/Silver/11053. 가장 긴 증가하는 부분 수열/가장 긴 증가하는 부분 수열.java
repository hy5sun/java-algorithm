import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    
    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < N; idx++) {
            arr[idx] = Integer.parseInt(st.nextToken());
        }

        // 최장 길이
        int maxLength = 0;
        int[] dp = new int[N]; // dp[i] : i가 끝일 때 수열의 최대 크기

        // DP로 해보기
        for (int idx = 0; idx < N; idx++) {
            dp[idx] = 1; // 자기 자신 포함한 1이 기본값
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                if (arr[idx] > arr[prevIdx]) {
                    dp[idx] = Math.max(dp[idx], dp[prevIdx] + 1);
                }
            }

            maxLength = Math.max(maxLength, dp[idx]);
        }

        System.out.println(maxLength);
    }
}
