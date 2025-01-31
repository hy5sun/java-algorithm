package 인프런.섹션4;

/*
뮤직비디오 (결정알고리즘)

총 N개의 곡을 M개 이하의 DVD에 최대 용량을 같은 크기로 설정하여 넣는다. 곡의 순서는 유지되어야 한다.
이때 한 개의 DVD의 최소 크기를 구한다.

ex) N = 9, M = 3, 1 2 3 4 5 6 7 8 9 => (1, 2, 3, 4, 5) (6, 7) (8, 9) : answer = 17

첫 번째 줄에 N과 M이, 두 번째 줄에 부른 곡의 시간(분)이 순서대로 주어진다.
부른 곡의 길이는 10000분을 넘지 않는다.

알고리즘 설계 (강의 듣고서 재설계):
lt는 최소 용량, rt는 최대 용량으로 설정한다. 최대 용량의 초깃값은 모든 음악의 합이다.
mid = (lt + rt) / 2로 설정한다.
song을 순회하면서 무작정 더하지 말고, 더했을 때 mid 값을 넣는지 먼저 확인한다. -> 실행 시간을 단축할 수 있다.
넘는다면 sum = 현재 원소로 설정하고, cnt(비디오 개수)를 더해준다.
넘지 않는다면 기존 sum에 원소를 계속 더한다.
cnt가 M(최대 비디오 개수)보다 작거나 같으면 조건을 충족한 것이므로 최솟값을 비교하여 answer에 저장하도록 한다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int N = input[0], M = input[1];

        int[] song = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int total = Arrays.stream(song).sum();

        int lt = 1, rt = total, mid;
        int cnt, sum, answer = total;

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            sum = 0;
            cnt = 1;

            for (int s : song) {
                if (s + sum > mid) {
                    sum = s;
                    cnt++;
                } else {
                    sum += s;
                }
            }

            if (cnt <= M) {
                answer = mid;
                rt = mid - 1;
            } else {
                lt = mid + 1;
            }
        }
        System.out.println(answer);
    }
}