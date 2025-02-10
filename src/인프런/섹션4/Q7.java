package 인프런.섹션4;

/*
창고 정리
창고 높이 조정을 하려고 한다.
창고 높이 조정은 가장 높은 곳에서 가장 낮은 곳으로 상자를 이동시키는 것이다.
가장 높은 곳이나 가장 낮은 곳이 여러 곳이면 그중에서 아무거나 선택한다.
M회 조정했을때, 가장 높은 곳과 가장 낮은 곳의 차이를 출력한다.

알고리즘 설계:
오름차순 정렬한 뒤, 맨 앞과 맨 뒤에 있는 원소를 빼고 더한다.
빼고 더한 뒤에는 다시 정렬을 한다.
마지막으로 맨 뒤에 있는 원소와 맨 앞에 있는 원소를 뺀다.

시간 복잡도 : O(M * LlogL)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Q7 {
    public static int[] heights;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 가로 길이
        int L = Integer.parseInt(br.readLine());

        // 창고 높이
        heights = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        // 높이 조정 횟수
        int M = Integer.parseInt(br.readLine());

        Arrays.sort(heights);

        for (int i = 0; i < M; i++) {
            heights[L-1] -= 1;
            heights[0] += 1;

            Arrays.sort(heights);

        }
        System.out.println(heights[L-1] - heights[0]);
    }
}