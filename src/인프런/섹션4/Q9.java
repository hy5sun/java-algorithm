package 인프런.섹션4;

/*
증가수열 만들기 (그리디)
1부터 N까지 자연수로 구성된 수열이 주어진다.
해당 수열의 왼쪽 맨 끝 숫자 또는 오른쪽 맨 끝 숫자 중 하나를 가져와 나열해서 가장 긴 증가수열을 만들어야 한다.
이때 수열에서 가져온 숫자는 그 수열에서 제거된다.
단, 마지막에 남은 값은 왼쪽 끝으로 생각한다.

ex) 2 4 5 1 3 -> 2 3 4 5 증가수열 생성 가능

알고리즘 설계 (강의 본 후):
candidate에 {원소, “L” 혹은 “R”} 형식으로 튜플을 추가한다.
last = 0으로 초기화하고, last 값과 sequence[lt], sequence[rt] 값과 각각 비교한다.
증가수열이므로 last가 다음 후보 보다 작아야 하기 때문에 last 보다 큰 경우에만 튜플을 추가한다.
튜플이 비어있다는 것은 둘 다 last 보다 작다는 것이므로 루프를 끝낸다.
아니라면 후보 튜플을 오름차순 정렬하고, 맨 앞에 있는 원소들을 통해 answer에 “L” 혹은 “R”을 더한다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q9 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] sequence = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        int lt = 0, rt = N-1, last = 0;

        // {sequence[lt] 혹은 sequence[rt], "L" 혹은 "R"} 로 구성된 튜플
        HashMap<Integer, String> candidate = new HashMap<>();
        // 튜플 정렬용
        TreeMap<Integer, String> sortedCandidate;

        StringBuilder answer = new StringBuilder();

        while (lt <= rt) {
            if (sequence[lt] > last)
                candidate.put(sequence[lt], "L");
            if (sequence[rt] > last)
                candidate.put(sequence[rt], "R");

            if (candidate.isEmpty()) {
                break;
            } else {
                sortedCandidate = new TreeMap<>(candidate);
                last = sortedCandidate.firstKey();
                if (sortedCandidate.firstEntry().getValue() == "L") {
                    answer.append("L");
                    lt++;
                } else {
                    answer.append("R");
                    rt--;
                }
            }

            candidate.clear();
        }

        System.out.println(answer.length());
        System.out.println(answer);
    }
}
