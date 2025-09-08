import java.io.*;
import java.util.*;

/**
 * BJ9084. 동전
 * 
 * - 동전 종류가 있을 때, 주어진 금액을 만드는 모든 방법을 세는 프로그램
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 테스트 케이스 수
 *  - 동전 가지 수 N
 *  - 동전 금액이 오름차순으로 정렬되어 주어짐
 *  - N가지 동전으로 만들어야 할 금액 M
 * 
 * 2. 점화식
 *  dp[i] : i원을 만드는 방법의 수
 *  coin : 동전 종류
 * 
 *  dp[0] = 1 (아무것도 안 낸 것 자체를 하나의 방법으로 본다.)
 *  for (int i = coin; i <= M; i++) {
 *      dp[i] += dp[i-coin];
 *  }
 * 
 * 어떻게 유추함? 
 * > M원을 만드는 방법은 (M-coin)원을 만들고, 마지막에 coin을 추가하는 경우와 같다.
 * > dp[M] = sum(dp[M-coin]); (M은 coin 부터)
 * 
 * ex. 동전 : {1, 2}, M = 4
 *  1) coin = 1
 *      dp[1] += dp[0] -> dp[1] = 1 {{1}}
 *      dp[2] += dp[1] -> dp[2] = 1 {{1 + 1}}
 *      dp[3] += dp[2] -> dp[3] = 1 {{1 + 1 + 1}}
 *      dp[4] += dp[3] -> dp[4] = 1 {{1 + 1 + 1 + 1}}
 * 
 *  2) coin = 2
 *      dp[2] += dp[0] -> dp[2] = 2 {{1 + 1}, {2}}
 *      dp[3] += dp[1] -> dp[3] = 2 {{1 + 1 + 1}, {1 + 2}}
 *      dp[4] += dp[2] -> dp[4] = 3 {{1 + 1 + 1 + 1}, {1 + 1 + 2}, {2+2}}
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, coinTypeCnt, purposeMoney;
    static int[] coins;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        // - 테스트 케이스 수
        testCaseCnt = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            // - 동전 가지 수 N
            coinTypeCnt = Integer.parseInt(br.readLine());

            // - 동전 금액이 오름차순으로 정렬되어 주어짐
            coins = new int[coinTypeCnt];

            st = new StringTokenizer(br.readLine().trim());
            for (int idx = 0; idx < coinTypeCnt; idx++) {
                coins[idx] = Integer.parseInt(st.nextToken());
            }

            // - N가지 동전으로 만들어야 할 금액 M
            purposeMoney = Integer.parseInt(br.readLine());

            dp = new int[purposeMoney+1];
            dp[0] = 1;

            for (int coin : coins) {
                for (int i = coin; i <= purposeMoney; i++) {
                    dp[i] += dp[i-coin];
                }
            }

            sb.append(dp[purposeMoney]).append("\n");
        }
        
        System.out.println(sb.toString());
    }
}
