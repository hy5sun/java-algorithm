package 인프런.섹션4;

/*
역수열 (그리디)
1부터 n까지의 수를 한 번씩만 사용해서 이루어진 수열이 있다.
1부터 n까지 각각의 수 앞에 놓여 있는 자신보다 큰 수의 개수를 수열로 표현한 것을 역수열이라고 한다.

ex) 4 8 6 2 5 1 3 7
1 앞에 놓인 1보다 큰 수는 4, 8, 6, 2, 5, 1 (6)
2 앞에 놓인 2보다 큰 수는 4, 8, 6 (3)
3 앞에 놓인 3보다 큰 수는 4, 8, 6, 5 (4)
...

알고리즘 설계:
0으로 구성된 배열을 생성한다.
0이라는 것은 아직 숫자가 배치되지 않았다는 의미이다.
숫자를 배치할 수 있는 인덱스를 반환하는 함수를 만든다.
아직 cnt가 특정 개수를 다 채우지 못했다면,
해당 배열을 돌면서 0이거나 현재 배치할 숫자보다 수가 크다면 cnt 개수를 늘린다.
이미 cnt와 특정 개수가 일치하다고 해도, 현재 인덱스에 다른 숫자가 배치되어 있을 수 있기 때문에 해당 인덱스에 0이 배치되어 있는지, 다른 숫자가 배치되어 있는지 확인해야 한다.
다른 숫자가 배치되어 있을 때는 다음 인덱스로 넘어가도록 하고,
0이 배치되어 있다면 해당 인덱스를 반환한다.
마지막으로 위 함수에서 반환하는 인덱스에 숫자를 배치하면 된다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Q10 {
    public static int n;
    public static int[] answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        int[] sequence = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        answer = new int[n];

        for (int i = 0; i < n; i++) {
            answer[getNumIdx(sequence[i], i+1)] = i+1;
        }

        String result = Arrays.stream(answer)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        System.out.println(result);

    }

    public static int getNumIdx(int underCnt, int num) {
        int idx = 0, cnt = 0;

        for (int i = 0; i < n; i++) {
            if (cnt != underCnt) {
                if (answer[i] > num || answer[i] == 0)
                    cnt++;
            } else {
                if (answer[i] == 0) {
                    idx = i;
                    break;
                }
            }
        }

        return idx;
    }
}

/*
last = 8;

[ 0 0 0 0 1 2 0 ]
1 -> _ _ _ _ _ 1 _ _ : if (last > 5) : a[5] = 1; last = 5;
2 -> _ _ 2 _ _ 1 _ _ : if (last > 3) : s[3] = 2; last = 3;
3 -> _ _ 2 _ _ 1 3 _ : if (last < 4) : s[4+2] = 3; last = 4;
4 -> 4 _ 2 _ _ 1 3 _ : if (last > 0) : s[0] = 0; last = 0;
5 -> 4 _ 2 _ 5

[0, 1, 2, 3, 6]


 */