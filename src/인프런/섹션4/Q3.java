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
원소를 순서대로 더해가면서 mid와 같거나 넘으면 cnt (DVD 간의 경계선) 를 더해준다.
이때 sum이 mid와 값이 같으면 다음 원소는 0부터 다시 더해야 되기 때문에 sum = 0 으로 설정해주고,
mid 보다 크면 sum = 마지막으로 더한 원소로 해준다. 이전 DVD에 포함되지 않은 원소부터 다음 원소와 더해야 되기 때문이다.
주의할 점은 끝까지 다 더했는데, mid 보다 sum이 큰 상태여서 마지막 원소가 DVD에 포함되지 않은 상태를 고려해야 된다는 점이다.
따라서 sum이 0이 아니라면 (마지막으로 성립된 조건이 sum == mid 가 아니라면) cnt++를 해줘서 마지막 원소도 비디오에 포함되도록 한다.
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

        int total = 0;
        for (int length : song) {
            total += length;
        }

        int lt = 1, rt = total, mid;
        int cnt = 0, sum, answer = total;

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            sum = 0;
            cnt = 0;

            for (int i = 0; i < N; i++) {
                sum += song[i];
                if (sum == mid) {
                    sum = 0;
                    cnt++;
                } else if (sum > mid) {
                    sum = song[i];
                    cnt++;
                }
            }

            if (sum != 0) {
                cnt++;
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