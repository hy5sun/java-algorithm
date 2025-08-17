import java.io.*;
import java.util.*;

public class Solution {
    private static final int TICKET_COUNT = 4;
    private static final int MONTH_COUNT = 12;

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    
    public static int[] price;
    public static int[] plan;

    public static int totalPrice;
    public static int minMoney;

    public static void main(String[] args) throws IOException {
        int testCaseCnt = Integer.parseInt(br.readLine().trim());

        price = new int[TICKET_COUNT];
        plan = new int[MONTH_COUNT];

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            totalPrice = 0;
            minMoney = Integer.MAX_VALUE;

            inputTicketPriceAndPlan();

            getMinPrice(0, 0);

            System.out.println("#" + testCase + " " + minMoney );
        }
    }

    private static void inputTicketPriceAndPlan() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < TICKET_COUNT; idx++)
            price[idx] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < MONTH_COUNT; idx++)
            plan[idx] = Integer.parseInt(st.nextToken());
    }
    
    private static void getMinPrice(int monthIdx, int totalMoney) {
        if (monthIdx >= MONTH_COUNT) {
            minMoney = Math.min(minMoney, totalMoney);
            return;
        }

        getMinPrice(monthIdx + 1, totalMoney + price[0] * plan[monthIdx]);
         getMinPrice(monthIdx + 1, totalMoney + price[1]);
        getMinPrice(monthIdx + 3, totalMoney + price[2]);
        getMinPrice(monthIdx + 12, totalMoney + price[3]);
    }
}