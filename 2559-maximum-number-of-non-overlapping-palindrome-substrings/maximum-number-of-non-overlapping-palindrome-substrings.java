class Solution {
    public int maxPalindromes(String s, int k) {
        int count = 0;
        int n = s.length();
        int i = 0;

        while (i < n) {
            // Check for palindrome of length k starting at i
            if (isPalindrome(s, i, i + k - 1)) {
                count++;
                i = i + k; // Move pointer past this palindrome
            } 
            // Check for palindrome of length k + 1 starting at i
            else if (isPalindrome(s, i, i + k)) {
                count++;
                i = i + k + 1; // Move pointer past this palindrome
            } 
            else {
                i++;
            }
        }

        return count;
    }

    private boolean isPalindrome(String s, int left, int right) {
        if (right >= s.length()) return false;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}