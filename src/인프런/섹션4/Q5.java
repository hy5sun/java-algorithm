package 인프런.섹션4;

/*
회의실 배정 (그리디)

한 개의 회의실이 있고, n개의 회의 사용표를 만들려고 한다.
각 회의에 대해 시작 시간과 끝나는 시간이 주어져 있고, 각 회의가 겹치지 않고 최대로 많은 회의를 해야 한다.
회의는 한번 시작하면 중간에 멈출 수 없고, 한 회의가 끝나는 동시에 다음 회의가 시작될 수 있다.

n : 회의 수

알고리즘 설계 (수정 전):
회의가 시작하는 시간을 기준으로 오름차순 정렬하고, 시간이 같으면 끝나는 시간을 기준으로 오름차순 정렬을 한다.
최대한 많은 회의를 하려면 시작 시간이 빠른 순서대로 처리해야 된다고 생각을 했기 떄문에 위와 같이 했다.
이중 for 문을 사용하여 스케줄 루프를 돌렸고, 해당 스케줄을 첫번째 회의라고 가정하고 진행했다.
for (int k = 0; k < N-1; k++) { cnt = 1; last = schedule.get(k)[1]; ... }
안에 있는 for 문은 k번째 인덱스부터 루프를 돌도록 하여 다음 회의 시작 시간이 이전 회의 끝 시간보다 같거나 클 때 해당 회의를 진행하는 것으로 했다.

알고리즘 설계 (수정 후):
회의가 끝나는 시간 기준으로 오름차순 정렬을 한다.
처음에는 시작 시간을 기준으로 정렬하고, 시간이 같으면 끝나는 시간을 기준으로 오름차순으로 정렬하려고 했다.
하지만 회의가 빨리 끝나야 다음 회의를 시작할 수 있기 떄문에 시작 시간은 이전 회의 끝 시간과 비교하는 용으로만 필요했다.
정렬된 후에는 루프를 돌려 다음 회의 시작 시간이 이전 회의 끝 시간과 같거나 크다면 해당 회의를 진행한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Q5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<int[]> schedule = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int[] time = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            schedule.add(time);
        }

        schedule.sort(Comparator.comparingInt(s -> s[1]));

        int last = 0, cnt = 0;

        for (int i = 0; i < n; i++) {
            if (last <= schedule.get(i)[0]) {
                cnt++;
                last = schedule.get(i)[1];
            }
        }

        System.out.println(cnt);
    }
}
