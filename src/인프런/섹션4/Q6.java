package 인프런.섹션4;

/*
씨름 선수 (그리디)

N명의 지원자의 (키, 몸무게) 정보가 주어진다.
다른 모든 지원자와 일대일 비교를 통해 키와 몸무게 중 적어도 하나가 크거나, 무거운 지원자만 뽑아야 한다.
만약 지원자 A 보다 키가 크고, 몸무게가 무거운 지원자가 존재하면 A는 탈락한다.

알고리즘 설계:
자기보다 키, 몸무게 모두 큰 사람이 존재하면 탈락이다.
먼저 키를 기준으로 오름차순 정렬하고, 같은 키면 몸무게 내에서 다시 오름차순 정렬을 한다.
순서대로 리스트를 돌면서 키, 몸무게 모두 더 큰 사람이 존재하면 해당 사람은 탈락하도록 설계한다.
내 앞사람 보다는 무조건 키/몸무게 둘 중 하나는 크기 때문에 뒤에 있는 사람과만 비교하면 된다.

시간 복잡도 : O(N²)

수정된 설계:
먼저 키를 기준으로 내림차순 정렬을 하고, 따로 for 문으로 몸무게가 더 큰 사람이 있는지 확인한다.
키가 큰 사람부터 돌기 때문에 키가 더 작은 사람이 합격하려면 몸무게가 커야 된다.
한 사람이 합격할 때마다 그 사람의 몸무게보다 항상 더 크면 되기 때문에 largest 변수에 그 사람의 몸무게를 저장하고, 그 값과 뒤에 있는 사람들의 몸무게를 비교한다.

시간 복잡도 : O(NlogN)
 */

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Q6 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        List<int[]> apply = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int[] info = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            apply.add(info);
        }

        apply.sort(Comparator.comparing(a -> a[0], Comparator.reverseOrder()));

        int passedWeight = 0, cnt = 0;

        for (int[] a : apply) {
            if (a[1] > passedWeight) {
                cnt += 1;
                passedWeight = a[1];
            }
        }

        System.out.println(cnt);
    }
}
