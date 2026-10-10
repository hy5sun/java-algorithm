import java.util.*;

class Solution {
    
    static Map<String, PriorityQueue<String>> map;
    static List<String> answer;
    
    public String[] solution(String[][] tickets) {
        answer = new ArrayList<>();
        
        map = new HashMap<>();
        
        for (String[] ticket : tickets) {
            if (!map.containsKey(ticket[0])) {
                map.put(ticket[0], new PriorityQueue<>());
            }
            
            PriorityQueue<String> queue = map.get(ticket[0]);
            queue.offer(ticket[1]);
        }
        
        dfs("ICN");
        
        Collections.reverse(answer);
        
        return answer.toArray(String[]::new);
    }
    
    void dfs(String from) {
        
        PriorityQueue<String> queue = map.get(from);
        
        while (queue != null && !queue.isEmpty()) {
            String next = queue.poll();
            dfs(next);
        }
        
        answer.add(from);
    }
}