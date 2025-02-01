package 인프런.섹션4;

/*
마구간 정하기 (결정 알고리즘)

N개의 마구간이 수직선상에 있고, 각 마구간은 x1, x2, ... xN의 좌표를 갖는다.
C마리의 말들이 있으며, 각 마구간에는 한 마리의 말만 넣을 수 있다.
가장 가까운 두 말의 거리가 최대가 되게 말을 배치하려고 한다.
C마리의 말을 N개의 마구간에 배치했을 때, 가장 가까운 두 말의 거리의 최댓값을 구한다.

[ (강의 본 후) 알고리즘 설계 ]
rt와 lt를 좌표간의 거리로 생각하여, lt = 1, rt = max(좌표) - min(좌표)로 생각한다.
mid = (lt + rt)/2를 계산하면 mid가 가장 가까운 거리의 최댓값이 된다.
맨 앞 좌표부터 돌아가면서 말을 둘건데, 어차피 맨 앞 좌표에는 말을 무조건 둘 거니까 두번째 좌표부터 둔다고 가정하면서 가장 마지막에 설치한 말과의 거리를 계산한다. 해당 거리가 mid 보다 같거나 크면 된다. (mid는 최소 거리이기 때문)
mid 보다 작으면 해당 좌표에는 말을 두지 않고, 다음 좌표로 넘어간다. 그렇게 끝까지 갔을 때 설치한 말이 C와 일치하는지 확인하고, 작으면 최소 거리가 더 짧아야 된다는 것이므로 `rt--;` 처리를 한다.
만약 C보다 많거나 같다면 `lt++` 처리를 한다. 일치한다고 해도 mid보다 더 큰 값이 최소 거리가 될 수 있고, 더 많다고 해도
결과적으로 mid를 출력하면 된다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        // 마구간 개수
        int N = Integer.parseInt(input[0]);
        // 말 수
        int C = Integer.parseInt(input[1]);

        ArrayList<Integer> stable = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            stable.add(Integer.parseInt(br.readLine()));
        }

        Collections.sort(stable);

        int lt = 1, rt = stable.get(N-1) - stable.get(0);
        int mid, lastHorse, cnt, answer = 1;

        while (lt <= rt) {
            mid = (lt + rt) / 2;
            cnt = 1;
            lastHorse = stable.get(0);

            for (int i = 1; i < N; i++) {
                if (stable.get(i) - lastHorse >= mid) {
                    lastHorse = stable.get(i);
                    cnt++;
                }
                if (cnt == C) {
                    lt++;
                    answer = mid;
                    break;
                }
            }

            if (cnt != C) {
                rt--;
            }
        }

        System.out.println(answer);
    }
}
