package 인프런.섹션4;

/*
씨름 선수 (그리디)

N명의 지원자의 (키, 몸무게) 정보가 주어진다.
다른 모든 지원자와 일대일 비교를 통해 키와 몸무게 중 적어도 하나가 크거나, 무거운 지원자만 뽑아야 한다.
만약 지원자 A 보다 키가 크고, 몸무게가 무거운 지원자가 존재하면 A는 탈락한다.

알고리즘 설계:
자기보다 키, 몸무게 모두 큰 사람이 존재하면 탈락이다.
먼저 키를 기준으로 내림차순 정렬하고, 같은 키면 몸무게 내에서 다시 내림차순 정렬을 한다.
순서대로 리스트를 돌면서 키, 몸무게 모두 더 큰 사람이 존재하면 해당 사람은 탈락하도록 설계한다.
내 앞사람 보다는 무조건 키/몸무게 둘 중 하나는 크기 때문에 뒤에 있는 사람과만 비교하면 된다.

시간 복잡도 : O(N²)
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

        apply.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        });

        int ht, wt, cnt = N;

        for (int i = 0; i < N; i++) {
            ht = apply.get(i)[0];
            wt = apply.get(i)[1];
            for (int j = i; j < N; j++) {
                if (ht < apply.get(j)[0] && wt < apply.get(j)[1]) {
                    cnt-=1;
                    break;
                }
            }
        }

        System.out.println(cnt);
    }
}
