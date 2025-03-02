package 인프런.섹션5;

/*
쇠막대기
막대기를 조건대로 겹쳐서 잘랐을 때, 결과적으로 몇 개의 막대기가 만들어지는지 구한다.

[조건]
- 자신보다 긴 쇠막대기 위에만 놓일 수 있다.
- 쇠막대기를 다른 쇠막대기 위에 놓는 경우, 완전히 포함되도록 놓되, 끝점은 겹치지 않게 놓는다.
- 각 쇠막대기를 자르는 레이저는 적어도 하나 존재한다.
- 레이저는 어떤 쇠막대기의 양 끝점과도 겹치지 않는다.
- "()" 가 레이저가 자른 부위이다.
- 쇠막대기의 왼쪽 끝은 "(", 오른쪽 끝은 ")"이다.

알고리즘 설계:
*cnt : 잘린 쇠막대기 개수
*last : 가장 최근에 입력된 괄호

입력받은 괄호를 루프문을 돌린다.

“(” 가 입력되면 막대기가 시작됐다는 것이므로 stack 에 넣는다.
“)”가 입력되면 쇠막대기가 끝났거나 레이저가 발사됐다는 것이므로 마지막으로 stack 에 들어간 “(”을 꺼낸다.
이때 두 상황 중 어떤 건지 파악하기 위해 last 문자열을 만들어, 직전에 입력된 괄호를 저장했다.

- “(” → “)” : 레이저 쏘기
    ⇒ 겹쳐진 쇠막대기 개수만큼 cnt에 더하기
    (겹쳐진 쇠막대기 개수 = stack에 저장된 “(”의 개수)

- “)” → “)” : 쇠막대기 끝남
    ⇒ 맨 오른쪽에 쇠막대기 하나가 남게 되므로 cnt에 1을 더하기
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] sticks = br.readLine().split("");

        int cnt = 0;
        String last = "";
        Stack<String> stack = new Stack<>();

        for (String s : sticks) {
            if (s.equals("(")) {
                stack.push("(");
                last = "(";
            } else {
                stack.pop();
                if (last.equals("(")) {
                    cnt += stack.size();
                } else {
                    cnt++;
                }
                last = ")";
            }
        }

        System.out.println(cnt);

    }
}
