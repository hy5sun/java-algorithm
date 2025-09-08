import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int appCnt, needMemory;
    static int[] memories;
    static int[] costs;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        // 1. 입력 받기
        st = new StringTokenizer(br.readLine().trim());

        appCnt = Integer.parseInt(st.nextToken());
        needMemory = Integer.parseInt(st.nextToken());

        memories = new int[appCnt];
        costs = new int[appCnt];

        int totalCost = 0;

        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < appCnt; idx++) {
            memories[idx] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < appCnt; idx++) {
            costs[idx] = Integer.parseInt(st.nextToken());
            totalCost += costs[idx];
        }

        dp = new int[totalCost+1]; // dp[i] : i만큼 비용을 냈을 때, 확보되는 최대 메모리양

        // 각각의 cost를 기준으로 Math.max(dp[cost], dp[cost - c[app]] + m[app]);
        // 해당 어플을 비활하지 않을 경우의 메모리 양 vs 현재 앱을 비활했을 경우의 메모리 양
        for (int appIdx = 0; appIdx < appCnt; appIdx++) {
            for (int cost = totalCost; cost >= costs[appIdx]; cost--) {
                dp[cost] = Math.max(dp[cost], dp[cost - costs[appIdx]] + memories[appIdx]);
            }
        }

        /**
         * 3. dp[i]가 M보다 같은 애가 있으면 i가 정답.
         *  * 없다면 M보다 큰 애 중에 가장 작은 dp[i]에서 i가 정답
         */
        for (int cost = 0; cost <= totalCost; cost++) {
            if (dp[cost] >= needMemory) {
                System.out.println(cost);
                System.exit(0);
            }
        }
    }
}