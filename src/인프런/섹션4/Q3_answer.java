package 인프런.섹션4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Q3_answer {
    public static int[] song;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] input = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int N = input[0], M = input[1];

        song = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int total = 0;
        for (int length : song) {
            total += length;
        }

        int lt = 1, rt = total, mid, answer = 0;
        int maxLength = Arrays.stream(song).max().getAsInt();

        while (lt <= rt) {
            mid = (lt+rt) / 2;
            if (mid > maxLength && getVideoCnt(mid) <= M) {
                answer = mid;
                rt = mid - 1;
            } else {
                lt = mid + 1;
            }
        }

        System.out.println(answer);
    }

    public static int getVideoCnt (int capacity) {
        int cnt = 1, sum = 0;
        for (int s : song) {
            if (sum + s > capacity) {
                cnt++;
                sum = s;
            } else {
                sum += s;
            }
        }
        return cnt;
    }
}
