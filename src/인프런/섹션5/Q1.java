package 인프런.섹션5;

/*
가장 큰 수
숫자가 주어지면, m개의 숫자를 제외시켜 가장 큰 수를 만든다.

알고리즘 설계:
[5, 2, 7, 6, 8, 2, 3]
max = 5 -> 2랑 비교 (2가 더 작음) -> 넣어! [2]
        -> 7이랑 비교 (7이 더 큼) -> 5 넣기 [2, 5]
max = 7 -> 6이랑 비교 (6이 더 작음) -> 6 넣기 [2,5,6] 3개 다 넣음! 굿~
[5, 2] [7]

[9, 9, 7, 7, 2, 5, 2, 6, 4, 1]
max = 9 -> 9랑 비교 (같음) -> 냅둬
max = 9 -> 7이랑 비교 (작음) -> 냅둬야하는데... (틀림)

강의 알고리즘 설계:
가장 큰 수가 되기 위한 조건: 제일 큰 숫자를 앞쪽에 위치시킨다.
따라서 앞에 자신보다 작은 숫자가 오지 못하도록 하면 된다.
단, 그 앞에 작은 숫자를 삭제하는 횟수가 m을 넘지 않도록 한다.
stack을 생성하여 stack이 비어있다면 원소를 추가해준다.
stack 맨 위에 있는 원소와 nums의 다음 원소를 비교한다. 이때 stack 안에 있는 모든 원소들과 비교해야 된다.
따라서 while 문을 사용하여 stack이 안 비어 있으면서 m이 0이 아닐 때 원소를 비교하도록 한다.
m이 0이 되지 않았는데도 더이상 앞에 더 작은 숫자가 오는 경우가 없어서 모두 stack에 들어가 있는 경우도 있다.
따라서 마지막에 m 만큼 stack의 맨 위 원소를 pop 해줘야 한다.

 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        int[] nums = Arrays.stream(input[0].split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        int m = Integer.parseInt(input[1]);
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            if (stack.empty()) {
                stack.push(nums[i]);
            } else {
                while (!stack.empty() && m != 0 && stack.peek() < nums[i]) {
                    stack.pop();
                    m -= 1;
                }
                stack.push(nums[i]);
            }
        }

        for (int i = 0; i < m; i++)
            stack.pop();

        for (int s : stack) {
            System.out.print(s);
        }
    }
}