import java.io.*;
import java.util.*;

public class Main {
    private static int N, M;
    private static int[] ch, nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        ch = new int[N+1];
        nums = new int[M];

        getPermutation(1);

    }

    private static void getPermutation(int L) {
        if (L == M+1) {
            for (int n : nums) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
        else {
            for (int i = 1; i <= N; i++) {
                if (ch[i] == 0) {
                    ch[i] = 1;
                    nums[L-1] = i;
                    getPermutation(L + 1);
                    ch[i] = 0;
                }
            }
        }
    }
}
