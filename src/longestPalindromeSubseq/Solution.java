package longestPalindromeSubseq;

/**
 * No.516 最长回文子序列
 *
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 */

public class Solution {

    /**
     * 动态规划（超时）
     * 原因：计算dp[i][j]又花了O(n^2)去遍历r和j找innerMaxDp
     * 所指这种dp状态定义并不合适，想要找到一个子串内部的最长回文子序列还得靠两层for循环
     * @param s
     * @return
     */
    // public int longestPalindromeSubseq(String s) {
    //     int len = s.length();
    //     char[] charArr = s.toCharArray();
    //     int maxLen = 0; // 记录当前最长回文子序列的长度
    //
    //     // dp[i][j]：以下标i开头，下标j结尾的最长回文子序列的长度
    //     int[][] dp = new int[len][len];
    //
    //     // 递推公式
    //     // 如果s[i] == s[j]且 j - i <= 1（即如单个字符"a"和两个字符"aa"），则dp[i][j] = j - i + 1
    //     // 如果s[i] == s[j]且 j - i > 1（即超过两个字符需要取决于内层子串是否是回文串），则dp[i][j] = dp[r][l] + 2，其中 i < r < l < j
    //     // 如果s[i] != s[j]（即首尾字符不相等一定不是回文串），则dp[i][j] = 0
    //
    //     // 初始化，全部初始化为0
    //
    //     // 遍历顺序
    //     for (int i = len - 1; i >= 0; i--) {
    //         for (int j = i; j < len ; j++) {
    //             if (charArr[i] == charArr[j]) {
    //                 if (j - i <= 1) {
    //                     dp[i][j] = j - i + 1;
    //                 } else {
    //                     int innerMaxDp = 0; // 在i~j内部的最长回文子序列长度
    //                     for (int r = i + 1; r < j; r++) {
    //                         for (int l = r; l < j; l++) {
    //                             innerMaxDp = Math.max(innerMaxDp, dp[r][l]);
    //                         }
    //                     }
    //                     dp[i][j] = innerMaxDp + 2;  // 加上首尾两个相同的字符
    //                 }
    //                 maxLen = Math.max(maxLen, dp[i][j]);
    //             }
    //         }
    //     }
    //
    //     return maxLen;
    // }






    /**
     * 动态规划 + 二维dp
     * 将dp[i][j]定义为s从下标i到j的子串（即s[i:j]）的最长回文子序列的长度
     * 这样就可以直接获取子串/子状态的最长回文子序列长度
     * @param s
     * @return
     */
    // 时间复杂度：O(n^2)，其中n为s的长度
    // 空间复杂度：O(n^2)，其中n为s的长度
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        char[] charArr = s.toCharArray();

        // dp[i][j]：s从下标i到j的子串（即s[i:j]）的最长回文子序列的长度
        int[][] dp = new int[len][len];

        // 递推公式
        // 如果s[i] == s[j]且 j - i <= 1（即如单个字符"a"和两个字符"aa"），最长回文子序列就是其本身，则dp[i][j] = j - i + 1
        // 如果s[i] == s[j]且 j - i > 1（即超过两个字符），s[i]和s[j]都被算入才是最佳（可举例验证，带点贪心思想），则dp[i][j] = dp[i+1][j-1] + 2
        // 如果s[i] != s[j]，说明s[i]和s[j]最多有一个能算入s[i:j]的最长回文子序列中（都不算入的情况dp[i+1][j-1]的情况也被包括在其中，递推公式会自动选择），则dp[i][j] = max{dp[i+1][j], dp[i][j-1]}

        // 初始化，全部初始化为0

        // 遍历顺序
        // 由于dp[i][j]依赖于三个方向：dp[i][j-1]，dp[i+1][j]，dp[i+1][j-1]，因此在计算dp[i][j]时要保证这三个位置已经被计算出来
        // 所以i需要倒序遍历，j需要正序遍历
        // 并且i是开始下标，j是结束下标，所以i<=j，j从i开始向右遍历
        // dp数组的左下三角部分（i>j的部分）都是非法部分，应该保持默认值false，后续遍历也不会计算那些位置的dp值
        for (int i = len - 1; i >= 0; i--) {    // i从下往上倒序遍历
            for (int j = i; j < len ; j++) {    // j从左往右正序遍历
                if (charArr[i] == charArr[j]) {
                    if (j - i <= 1) {
                        dp[i][j] = j - i + 1;
                    } else {
                        dp[i][j] = dp[i+1][j-1] + 2;  // 加上首尾两个相同的字符一定最优
                    }
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);    // 左右两端分别尝试向内缩减
                }
            }
        }

        return dp[0][len - 1];
    }
}
