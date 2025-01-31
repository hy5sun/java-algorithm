package 인프런.섹션4;

/*
랜선자르기 (결정 알고리즘)

K : 이미 가지고 있는 랜선의 수 (1 이상 10000 이하의 정수)
N : 필요한 랜선의 수 (1 이상 1000000 이하의 정수)

K개의 랜선이 주어지고, 랜선을 잘라서 N개 이상의 랜선을 생성한다고 할 때,
만들 수 있는 최대 랜선의 길이를 구한다.

** K보다 작다 = 숫자가 크다. => 숫자를 작게 해야 됨 => rt--
** K보다 크다 = 숫자가 작다. => 숫자를 크게 해야 됨 => lt++

알고리즘 설계:
내림차순으로 정렬한다. [802 743 457 539]
제일 큰 원소부터 나눴을 때 K를 넘는지 확인한다.
만약 K보다 작으면 다음 원소로 나누기를 반복한다.
K보다 크면 해당 원소를 lt로 잡고, rt를 그 전 원소로 잡는다.

이제 이분검색으로 적절한 길이를 구한다.
lt <= rt 가 만족하는 동안 아래 작업을 반복한다.
mid = (lt+rt)/2;를 통해 mid로 나눈 값이 K보다 작다면 rt--;
K보다 크다면 answer에 해당 길이를 저장하고, lt++;

해당 루프가 끝나면 answer를 프린트한다.
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Q2 {
    public static ArrayList<Integer> LAN = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int total = 0, answer= 0;

        String[] input = br.readLine().split(" ");

        int N = Integer.parseInt(input[0]);
        int K = Integer.parseInt(input[1]);

        for (int i = 0; i < N; i++) {
            LAN.add(Integer.parseInt(br.readLine()));
        }

        Collections.sort(LAN, Collections.reverseOrder());

        int lt = 0, rt = LAN.getLast(), mid;

        for (int i = 0; i < N; i++){
            int length = LAN.get(i);
            total = getLANCnt(length);

            if (total >= K) {
                lt = length;
                rt = LAN.get(i-1);
                answer = total;
                break;
            }
        }

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            total = getLANCnt(mid);
            if (total >= K) {
                answer = mid;
                lt++;
            }
            else {
                rt--;
            }
        }

        System.out.println(answer);
    }

    public static int getLANCnt(int length) {
        int total = 0;
        for (int l : LAN) {
            total += l / length;
        }
        return total;
    }
}
