package minDistance_583;

/**
 * No.583 两个字符串的删除操作
 *
 * 给定两个单词 word1 和 word2 ，返回使得 word1 和  word2 相同所需的最小步数。
 * 每步 可以删除任意一个字符串中的一个字符。
 */

public class Solution {

    /**
     * 当两个单词的公共子序列最长时，将该子序列作为删除后的目标来删，总共需要的步数最少（要删的元素最少）
     * 转换为求两个字符串的最长公共子序列问题（No.1143），最后多一步计算将两个单词删到最长公共子序列需要删多少个字符就是要删多少步
     * @param word1
     * @param word2
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为word1和word2的长度
    // 时间复杂度：O(mn)
    public int minDistance(String word1, String word2) {
        int l1 = word1.length(), l2 = word2.length();
        char[] charArr1 = word1.toCharArray();
        char[] charArr2 = word2.toCharArray();

        // dp[i][j]：word1前i个字符组成的子串（word1[0:i-1]）与 word2前j个字符组成的子串（word2[0:j-1]） 的最长公共子序列长度
        int[][] dp = new int[l1 + 1][l2 + 1];

        // 递推公式
        // 如果word1[i-1] == word2[j-1]，则dp[i][j] = dp[i-1][j-1] + 1
        // 如果word1[i-1] != word2[j-1]，则dp[i][j] = max{dp[i-1][j], dp[i][j-1]}

        // 初始化
        // dp[0][j]和dp[i][0]均初始化为0

        // 遍历顺序
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (charArr1[i-1] == charArr2[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return l1 + l2 - dp[l1][l2] * 2;    // 最后word1和word2都删除到最长公共子序列所需的步数最少
    }
}
