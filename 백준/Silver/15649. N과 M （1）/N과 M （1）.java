import java.io.*;
import java.util.*;

public class Main {
    private static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        List<Integer> nums = new ArrayList<>();

        getPermutation(1, nums);

    }

    private static void getPermutation(int L, List<Integer> nums) {
        if (L == M+1) {
            for (int n : nums)
                System.out.print(n+ " ");
            System.out.println();
        }
        else {
            for (int i = 1; i <= N; i++) {
                if (!nums.contains(i)) {
                    nums.add(i);
                    getPermutation(L + 1, nums);
                    nums.remove(nums.size() - 1);
                }
            }
        }
    }
}
