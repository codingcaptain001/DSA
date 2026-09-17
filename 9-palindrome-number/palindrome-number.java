class Solution {
    public boolean isPalindrome(int x) {
        // int n = sc.nextInt();
        int duplicate = x;
            if(x<0) return false;
        int revNo = 0;
        while(x!=0){
            int ld = x%10;
            revNo = (revNo*10)+ld;
            x = x/10;
        }
        if(duplicate == revNo) return true;
        else return false;
    }
}