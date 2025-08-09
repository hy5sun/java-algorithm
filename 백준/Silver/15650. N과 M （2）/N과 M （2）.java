import java.io.*;
import java.util.*;

public class Main {

    public static BufferedReader br;
    public static StringTokenizer st;
    public static StringBuilder sb;

    public static int N, M;
    public static int[] nums;
    public static boolean[] isSelected;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        nums = new int[M];
        isSelected = new boolean[N+1];

        sb = new StringBuilder();

        combination(1, 0);

        System.out.println(sb.toString());
    }

    private static void combination(int startNum, int index) {
        if (index == M) {
            for (int num : nums) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        for (int nextNum = startNum; nextNum <= N; nextNum++) {
            nums[index] = nextNum;
            combination(nextNum + 1, index + 1);
        }
    }
}