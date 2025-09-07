import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * BJ1149. RGB거리
 *
 * [문제]
 * - N개의 집이 존재함. (1 ~ N번)
 * - 빨, 초, 파 중 하나의 색으로 칠해야 함.
 * - 아래 규칙을 만족하는 비용의 최솟값 구하기
 *  1. 1번 집의 색은 2번 집의 색과 달라야 한다.
 *  2. N번 집의 색은 N-1번 집의 색과 달라야 한다.
 *  3. i(2<=i<=N-1)번 집의 색은 i-1번, i+1번 집의 색과 달라야 한다.
 *
 * [풀이]
 * 1. dp[homeIdx][colorIdx] : homeIdx 번째 집까지 칠했을 때의 최소 비용. homeIdx 번째 집은 colorIdx로 칠해져 있음
 * 2. cost[homeIdx][colorIdx] : homeIdx 번째 집을 colorIdx 로 칠하는 비용
 *
 * ex. homeIdx 번째 집을 colorIdx(0) 으로 칠하는 경우
 *  => homeIdx-1 번째 집은 반드시 colorIdx가 1, 2 중 하나여야 함
 *  => dp[homeIdx][0] = cost[homeIdx][0] + Min((homeIdx-1 번째 집까지 1로 칠한 최소 비용) 과 (homeIdx-1 번째 집까지 2로 칠한 최소 비용))
 *  dp[homeIdx][1] = cost[homeIdx][1] + Min(dp[homeIdx-1][0], dp[homeIdx-1][2])
 *  dp[homeIdx][2] = cost[homeIdx][2] + Min(dp[homeIdx-1][0], dp[homeIdx-1][1])
 */
public class Main {
    static final int COLOR_CNT = 3;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int homeCnt;
    static int[][] minCosts;
    static int[][] costs;

    public static void main(String[] args) throws IOException {
        homeCnt = Integer.parseInt(br.readLine());

        minCosts = new int[homeCnt][COLOR_CNT];
        costs = new int[homeCnt][COLOR_CNT];

        for (int home = 0; home < homeCnt; home++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int color = 0; color < 3; color++)
                costs[home][color] = Integer.parseInt(st.nextToken());
        }

        for (int colorIdx = 0; colorIdx < COLOR_CNT; colorIdx++)
            minCosts[0][colorIdx] = costs[0][colorIdx];

        for (int homeIdx = 1; homeIdx < homeCnt; homeIdx++)
            for (int colorIdx = 0; colorIdx < COLOR_CNT; colorIdx++)
                calculate(homeIdx, colorIdx);

        System.out.println(Math.min(minCosts[homeCnt-1][0], Math.min(minCosts[homeCnt-1][1], minCosts[homeCnt-1][2])));
    }

    private static int calculate(int homeIdx, int colorIdx) {
        if (minCosts[homeIdx][colorIdx] != 0) return minCosts[homeIdx][colorIdx];

        return minCosts[homeIdx][colorIdx] = costs[homeIdx][colorIdx] + Math.min(calculate(homeIdx-1, (colorIdx + 1) % 3), calculate(homeIdx-1, (colorIdx + 2) % 3));
    }
}
