import java.util.*;

public class Solution {
    public int[] solution(int[] arr) {
        Deque<Integer> answer = new ArrayDeque<>();
        
        for (int num : arr) {
            if (answer.isEmpty()) {
                answer.push(num);
                continue;
            }
            
            if (answer.peekLast() != num) {
                answer.addLast(num);
            }
        }

        return answer.stream()
            .mapToInt(Integer::intValue)
            .toArray();
    }
}