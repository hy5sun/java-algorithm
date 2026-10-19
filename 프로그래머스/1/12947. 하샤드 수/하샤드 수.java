class Solution {
    public boolean solution(int x) {
        String[] nums = String.valueOf(x).split("");
        
        int sum = 0;
        
        for (int idx = 0; idx < nums.length; idx++)
            sum += Integer.parseInt(nums[idx]);
        
        if (x % sum == 0) return true;
        return false;
    }
}