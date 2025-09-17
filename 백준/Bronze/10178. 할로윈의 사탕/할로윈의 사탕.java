import java.io.*;
import java.util.*;

/**
 * BJ10178. 할로윈의 사탕
 * 
 * 자식들한테 최대한 많은 사탕을 나누어주고, 자신한테 몇 개 남는지 구한다.
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int testCaseCnt, candyCnt, childCnt;

    public static void main(String[] args) throws IOException {
        testCaseCnt = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            st = new StringTokenizer(br.readLine().trim());

            candyCnt = Integer.parseInt(st.nextToken());
            childCnt = Integer.parseInt(st.nextToken());

            int candyPerChild = candyCnt / childCnt;
            int candyForDaddy = candyCnt % childCnt;

            sb.append("You get ")
                .append(candyPerChild)
                .append(" piece(s) and your dad gets ")
                .append(candyForDaddy)
                .append(" piece(s).")
                .append("\n");
        }

        System.out.println(sb.toString());
    }
}
