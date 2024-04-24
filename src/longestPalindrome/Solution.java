package longestPalindrome;

import java.util.Arrays;

/**
 * No.5 最长回文子串
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * @param s
     * @return
     */
    // // 时间复杂度：O(n^2)，其中n为s的长度
    // // 时间复杂度：O(n^2)，其中n为s的长度
    // public String longestPalindrome(String s) {
    //     char[] charArr = s.toCharArray();
    //     int len = s.length();
    //     int maxLen = 0; // 当前最长回文子串的长度
    //     String res = "";
    //
    //     // dp[i][j]：s[i:j]子串中是否为回文子串
    //     boolean[][] dp = new boolean[len][len];
    //
    //     // 递推公式
    //     // 如果s[i] == s[j]且 j - i <= 1，dp[i][j] = true
    //     // 如果s[i] == s[j]且 j - i > 1，dp[i][j] = dp[i+1][j-1]
    //     // 如果s[i] != s[j]，dp[i][j] = false
    //
    //     // 初始化，全初始化为false
    //
    //     // 遍历顺序
    //     for (int i = len - 1; i >= 0; i--) {
    //         for (int j = i; j < len; j++) {
    //             if (charArr[i] == charArr[j]) {
    //                 if (j - i <= 1) {
    //                     dp[i][j] = true;
    //                 } else {
    //                     dp[i][j] = dp[i+1][j-1];
    //                 }
    //             }
    //
    //             // 如果找到更长的回文子串
    //             if (dp[i][j] && j - i + 1 > maxLen) {
    //                 maxLen = j - i + 1;
    //                 res = s.substring(i, j + 1);
    //             }
    //         }
    //     }
    //
    //     return res;
    // }


    /**
     * 中心扩散法
     * 回文子串的中心只有两种基本情况：单字符或两个相同的字符
     * @param s
     * @return
     */
    // 时间复杂度：O(n)，其中n为s的长度
    // 时间复杂度：O(1)，其中n为s的长度
    public String longestPalindrome(String s) {
        char[] charArr = s.toCharArray();
        int len = s.length();
        String res = "";

        for (int i = 0; i < len; i++) {
            String res1 = spread(charArr, i, i, len); // 单字符为中心
            res = res.length() >= res1.length() ? res : res1;
            if (i + 1 < len && charArr[i] == charArr[i+1]) {
                String res2 = spread(charArr, i, i + 1, len); // 两个相同字符为中心
                res = res.length() >= res2.length() ? res : res2;
            }
        }

        return res;
    }


    public String spread(char[] charArr, int l, int r, int len) {
        while (l >= 0 && r < len && charArr[l] == charArr[r]) {
            l--;
            r++;
        }
        // 因为是扩散了一次后才不符合条件退出，所以得缩回去才是真正的最长回文子串
        l++;
        r--;
        return new String(charArr, l, r-l+1); // 从l开始截取r-l+1个字符创建字符串
    }
}
