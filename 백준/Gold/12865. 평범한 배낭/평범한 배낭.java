import java.util.*;
import java.io.*;

/**
 * BJ12865. 평범한 배낭
 * 
 * [문제]
 * - 최대 K만큼 무게만 넣을 수 있는 배낭에 물건을 넣되, 최대한 많은 가치를 가지도록 한다.
 * 
 * [풀이]
 * 1. 입력 받기
 *  - 물품 수 N, 무게 K
 *  - 물건의 무게와 가치
 *      - 배열에 저장한다.
 * 
 * 2. 무게가 최대 1 ~ K 일 때까지 최대 가치값을 dp 배열에 저장한다.
 *  - dp[productIdx][weight] = weight가 최대 무게일 때, productIdx 까지 넣었을 경우의 최대 가치값
 *  
 *  2-1. 현재 담으려는 물건의 무게가 최대 무게 K를 초과했나?
 *      -> 물건을 담지 않는다.
 *      => dp[productIdx][weight] = dp[productIdx-1][weight];
 *  
 *  2-2. 현재 담으려는 물건을 배낭에 담을 수 있다.
 *      2-2-1. 물건을 담지 않는다.
 *          => dp[productIdx][weight] = dp[productIdx-1][weight];
 *      2-2-2. 물건을 담는다.
 *          => dp[productIdx][weight] = (productIdx의 가치) + dp[productIdx-1][weight - (productIdx의 무게)];
 *      => 2-2-1와 2-2-2 중 더 큰 가치를 가진 값을 dp[productIdx][weight] 에 저장한다.
 * 
 * 3. dp[N][K] 를 출력한다.
 */
public class Main {
    static final int WEIGHT = 0;
    static final int VALUE = 1;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    
    static int productCnt, maxWeight;
    static int[][] products;
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        // 입력 받기
        // - 물품 수 N, 무게 K
        st = new StringTokenizer(br.readLine().trim());

        productCnt = Integer.parseInt(st.nextToken());
        maxWeight = Integer.parseInt(st.nextToken());

        products = new int[productCnt][2];
        dp = new int[productCnt][maxWeight+1];

        for (int productIdx = 0; productIdx < productCnt; productIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            int weight = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            products[productIdx] = new int[]{weight, value};

            Arrays.fill(dp[productIdx], -1);
        }

        calculate(productCnt-1, maxWeight);

        System.out.println(dp[productCnt-1][maxWeight]);
    }

    private static int calculate(int productIdx, int weight) {
        if (productIdx < 0 || weight <= 0) return 0;

        if (dp[productIdx][weight] != -1) return dp[productIdx][weight];

        if (products[productIdx][WEIGHT] > weight) {
            return dp[productIdx][weight] = calculate(productIdx-1, weight);
        }
        
        else {
            return dp[productIdx][weight] = Math.max(calculate(productIdx-1, weight), products[productIdx][VALUE] + calculate(productIdx-1, weight - products[productIdx][WEIGHT]));
        }
    }
}
