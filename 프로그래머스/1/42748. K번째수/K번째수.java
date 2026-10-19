import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        List<Integer> arr;
        
        for (int idx = 0; idx < commands.length; idx++) {
            arr = new ArrayList<>();
            
            for (int jdx = commands[idx][0]-1; jdx < commands[idx][1]; jdx++) {
                arr.add(array[jdx]);
            }
            
            Collections.sort(arr);
            
            answer[idx] = arr.get(commands[idx][2]-1);
        }
        
        return answer;
    }
}