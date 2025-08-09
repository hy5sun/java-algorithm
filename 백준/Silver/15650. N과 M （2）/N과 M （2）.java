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
    }

    private static void combination(int selectedIndex, int elementIndex) {
        if (elementIndex == M) {
            for (int num : nums) {
                sb.append(num).append(" ");
            }
            System.out.println(sb.toString());
            sb.setLength(0);
            return;
        }
        
        for (int numIdx = selectedIndex; numIdx <= N; numIdx++) {
            nums[elementIndex] = numIdx;
            combination(numIdx + 1, elementIndex + 1);
        }
    }
}
