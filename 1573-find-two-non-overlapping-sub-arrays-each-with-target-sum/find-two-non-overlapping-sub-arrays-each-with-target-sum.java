class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] minLen = new int[n];
        
        // Fill with a large value representing infinity
        final int INF = 1_000_000_000;
        java.util.Arrays.fill(minLen, INF);
        
        int minTotalSum = INF;
        int windowSum = 0;
        int left = 0;
        
        for (int right = 0; right < n; right++) {
            windowSum += arr[right];
            
            // Shrink window if the sum exceeds target
            while (windowSum > target) {
                windowSum -= arr[left];
                left++;
            }
            
            // Found a valid sub-array [left, right]
            if (windowSum == target) {
                int currentLen = right - left + 1;
                
                // If there's a valid non-overlapping sub-array to the left
                if (left > 0 && minLen[left - 1] != INF) {
                    minTotalSum = Math.min(minTotalSum, minLen[left - 1] + currentLen);
                }
                
                // Update minLen for current right index
                if (right > 0) {
                    minLen[right] = Math.min(minLen[right - 1], currentLen);
                } else {
                    minLen[right] = currentLen;
                }
            } else {
                // Carry over the minimum length seen so far
                if (right > 0) {
                    minLen[right] = minLen[right - 1];
                }
            }
        }
        
        return minTotalSum == INF ? -1 : minTotalSum;
    }
}