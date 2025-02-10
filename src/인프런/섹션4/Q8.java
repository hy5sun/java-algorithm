package 인프런.섹션4;

/*
침몰하는 타이타닉 (그리디)
유람선에 N명의 승객이 구명보트를 타고 탈출해야 한다.
구명보트는 2명 이하로만 탈 수 있고, 보트 한 개에 탈 수 있는 총 무게도 M kg 이하로 제한되어 있다.
승객 모두가 탈출하기 위한 구명보트의 최소 개수를 출력한다.

N: 승객 몸무게 (5<=N<=1000)
M: 구명보트 최대 몸무게 제한 (70<=M<=250)

알고리즘 설계:
오름차순으로 정렬한 뒤, 맨 앞과 맨 뒤를 rt와 lt로 설정한다.
(몸무게 최대 제한) - (제일 무거운 승객)을 계산했을 때, 해당 값과 (제일 가벼운 승객)의 몸무게를 비교하여
해당 값이 더 적다면 함께 태울 수 있는 승객이 없는 것이므로 제일 무거운 승객 혼자 태운다.
반대로 제일 가벼운 승객이 더 작으면 그 둘을 함께 태운다.
이때는 제일 무거운 승객과 제일 가벼운 승객을 태운 것이므로 rt++, lt-- 처리를 해준다.
이 과정을 lt <= rt, 즉 모두 다 태울 때까지 반복한다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q8 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int N = input[0], M = input[1];

        int[] weights = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        Arrays.sort(weights);

        int lt = 0, rt = N-1, cnt = 0;

        while (lt <= rt) {
            if (M - weights[rt] < weights[lt]) {
                rt--;
            } else {
                lt++;
                rt--;
            }
            cnt++;
        }

        System.out.println(cnt);
    }
}
