import java.util.*;
class Solution {
    public int reverse(int x) {
       Scanner sc = new Scanner(System.in);
    int reverseNum = 0;
       while(x!=0){
        int lastdigit = x%10;
        x = x/10;

        if (reverseNum > Integer.MAX_VALUE / 10 || (reverseNum == Integer.MAX_VALUE / 10 && lastdigit > 7)) {
                return 0;
            }
            if (reverseNum < Integer.MIN_VALUE / 10 || (reverseNum == Integer.MIN_VALUE / 10 && lastdigit < -8)) {
                return 0;
            }
        reverseNum = (reverseNum*10)+lastdigit;
       }
       return reverseNum;
    }
}