package 인프런.섹션4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q4_answer {
    public static ArrayList<Integer> stable = new ArrayList<>();
    public static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        int C = Integer.parseInt(input[1]);

        for (int i = 0; i < N; i++) {
            stable.add(Integer.parseInt(br.readLine()));
        }

        Collections.sort(stable);

        int lt = stable.get(0), rt = stable.get(N-1), mid;
        int answer = 0;

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            if (getHorseCnt(mid) >= C) {
                answer = mid;
                lt = mid + 1;
            } else {
                rt = mid - 1;
            }
        }

        System.out.println(answer);
    }

    public static int getHorseCnt(int length) {
        int cnt = 1;
        int lastHorse = stable.get(0);

        for (int i = 1; i < N; i++) {
            if (stable.get(i) - lastHorse >= length) {
                cnt++;
                lastHorse = stable.get(i);
            }
        }

        return cnt;
    }
}
