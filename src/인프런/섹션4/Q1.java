package 인프런.섹션4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstInput = br.readLine().split(" ");

        int N = Integer.parseInt(firstInput[0]);
        int M = Integer.parseInt(firstInput[1]);

        String[] secondInput = br.readLine().split(" ");

        ArrayList<Integer> nums = new ArrayList<>(M);
        for (int i = 0; i < N; i++) {
            nums.add(Integer.parseInt(secondInput[i]));
        }

        Collections.sort(nums);

        int lt = 0;
        int rt = N-1;
        int mid;

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            if (nums.get(mid) == M) {
                System.out.println(mid+1);
                break;
            }
            else if (nums.get(mid) < M) {
                lt++;
            } else {
                rt--;
            }
        }
    }
}
