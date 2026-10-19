import java.util.*;

class Solution {
    public int solution(String[] spell, String[] dic) {
        for (String word : dic) {
            if (word.length() != spell.length) continue;
            String[] sp = word.split("");
            
            Arrays.sort(sp);
            Arrays.sort(spell);
            
            if (Arrays.equals(sp, spell)) return 1;
        }
        
        return 2;
    }
}