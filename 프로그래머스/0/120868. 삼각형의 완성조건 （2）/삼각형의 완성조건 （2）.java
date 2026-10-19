import java.util.*;

class Solution {
    public int solution(int[] sides) {
        int answer = 0;
        Arrays.sort(sides);
        
        for (int length = 1; length < sides[0] + sides[1]; length++) {
            if (length < sides[1] && (length + sides[0] > sides[1])) {
                answer++;
            }
            
            else if (length >= sides[1] && (sides[0] + sides[1] > length)) {
                answer++;
            }
        }
        return answer;
    }
}