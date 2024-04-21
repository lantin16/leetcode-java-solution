package longestCommonSubsequence;

/**
 * No.1143 最长公共子序列
 *
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp（超时）
     * @param text1
     * @param text2
     * @return
     */
    // // 时间复杂度：O(m^2 n^2)，其中m，n分别为text1和text2的长度
    // // 时间复杂度：O(mn)，其中m，n分别为text1和text2的长度
    // public int longestCommonSubsequence(String text1, String text2) {
    //     int len1 = text1.length(), len2 = text2.length();
    //     int res = 0;    // 记录当前的最长公共子序列的长度
    //
    //     // dp[i][j]：以text1的第i个字符（即text1.charAt(i-1)）结尾和以text2的第j个字符（即text2.charAt(j-1)）结尾的最长公共子序列长度
    //     int dp[][] = new int[len1 + 1][len2 + 1];
    //
    //     // 递推公式
    //     // 如果text1[i-1] = text2[j-1]，则dp[i][j] = max{dp[r][l] + 1}，满足 r < i, l < j
    //     // 如果text1[i-1] != text2[j-1]，则dp[i][j] = 0
    //
    //     // 初始化，dp[0][j]和dp[i][0]均初始化为0，只是为了递推公式正确，无实际意义
    //
    //     // 遍历顺序，先遍历text1还是text2都可以
    //     for (int i = 1; i <= len1; i++) {
    //         for (int j = 1; j <= len2; j++) {
    //             if (text1.charAt(i-1) == text2.charAt(j-1)) {
    //                 int tmpLen = 0; // 记录dp[i][j]的当前最大值
    //                 for (int r = 0; r < i; r++) {
    //                     for (int l = 0; l < j; l++) {
    //                         tmpLen = Math.max(tmpLen, dp[r][l] + 1);
    //                     }
    //                 }
    //                 dp[i][j] = tmpLen;
    //                 res = Math.max(res, dp[i][j]);
    //             }
    //             // 如果末尾元素不相等则保持默认值0即可
    //         }
    //     }
    //
    //     return res;
    // }


    /**
     * 动态规划 + 二维dp
     * 优化时间复杂度
     * 每次遍历到相等的末尾元素时要向前遍历所有的dp[r][l]找最大值存在许多重复的计算
     * 因此可以用一个辅助二维数组maxDp来记录左上角区域的最大dp值（空间换时间）
     * @param text1
     * @param text2
     * @return
     */
    // // 时间复杂度：O(mn)，其中m，n分别为text1和text2的长度。每次遍历到两个相等的末尾元素最多只需要根据maxDp比较两次最大值得到左上角区域的最大dp值
    // // 时间复杂度：O(mn)，其中m，n分别为text1和text2的长度。分析：dp数组的空间复杂度为O(mn)，辅助数组的空间复杂度也为O(mn)
    // public int longestCommonSubsequence(String text1, String text2) {
    //     int len1 = text1.length(), len2 = text2.length();
    //     int res = 0;    // 记录当前的最长公共子序列的长度
    //
    //     // dp[i][j]：以text1的第i个字符（即text1.charAt(i-1)）结尾和以text2的第j个字符（即text2.charAt(j-1)）结尾的最长公共子序列长度
    //     int dp[][] = new int[len1 + 1][len2 + 1];
    //
    //     // 递推公式
    //     // 如果text1[i-1] = text2[j-1]，则dp[i][j] = maxDp[i-1][j-1] + 1
    //     // 如果text1[i-1] != text2[j-1]，则dp[i][j] = 0
    //
    //     // 初始化，dp[0][j]和dp[i][0]均初始化为0，只是为了递推公式正确，无实际意义
    //
    //     // maxDp[r][l]记录dp数组下标0~r，0~l的左上角区域的最大值
    //     int[][] maxDp = new int[len1][len2];
    //     // 由于dp[0][j]和dp[i][0]均初始化为0，因此maxDp[r][0]和maxDp[0][l]也初始化为0
    //
    //     // 遍历顺序，先遍历text1还是text2都可以
    //     for (int i = 1; i <= len1; i++) {
    //         for (int j = 1; j <= len2; j++) {
    //             int r = i - 1, l = j - 1;
    //             if (text1.charAt(i-1) == text2.charAt(j-1)) {
    //                 // 找出dp数组r < i, l < j的左上角区域的最大dp值，即maxDp[i-1][j-1]
    //                 if (r == 0 || l == 0) { // maxDp[r][0]和maxDp[0][l]都为0
    //                     dp[i][j] = 1;
    //                 } else {
    //                     maxDp[r][l] = Math.max(dp[r][l], Math.max(maxDp[r][l-1], maxDp[r-1][l]));
    //                     dp[i][j] = maxDp[r][l] + 1;
    //                 }
    //                 res = Math.max(res, dp[i][j]);
    //             } else {
    //                 // 如果末尾元素不相等则dp[i][j]保持默认值0即可
    //                 // 但还是要计算maxDp，否则maxDp[r][l]还是默认值0，在后面的计算中如果用到用0计算可能出现错误
    //                 if (r > 0 && l > 0) {
    //                     maxDp[r][l] = Math.max(dp[r][l], Math.max(maxDp[r][l-1], maxDp[r-1][l]));
    //                 }
    //             }
    //         }
    //     }
    //
    //     return res;
    // }








    /**
     * 动态规划 + 二维dp
     * 利用滚动数组继续优化空间复杂度
     * dp[i][j]只跟上一行的maxDp[i-1][j-1]有关，而计算maxDp[i-1][j-1]最多用到maxDp[i-2][j-1]，maxDp[i-1][j-2]，dp[i-1][j-1]
     * 因此数组dp和maxDp都可以压缩成一维
     *
     * @param text1
     * @param text2
     * @return
     */
    // // 时间复杂度：O(mn)，其中m，n分别为text1和text2的长度。每次遍历到两个相等的末尾元素最多只需要根据maxDp比较两次最大值得到左上角区域的最大dp值
    // // 时间复杂度：O(n)，其中n分别为text2的长度。分析：dp数组的空间复杂度为O(n)，辅助数组的空间复杂度也为O(n)
    // public int longestCommonSubsequence(String text1, String text2) {
    //     int len1 = text1.length(), len2 = text2.length();
    //     int res = 0;    // 记录当前的最长公共子序列的长度
    //
    //     // dp[j]：以text1的第i个字符（即text1.charAt(i-1)）结尾和以text2的第j个字符（即text2.charAt(j-1)）结尾的最长公共子序列长度
    //     int dp[] = new int[len2 + 1];
    //
    //     // 递推公式
    //     // 如果text1[i-1] = text2[j-1]，则dp[j] = maxDp[j-1] + 1
    //     // 如果text1[i-1] != text2[j-1]，则dp[j] = 0
    //
    //     // 初始化，dp[j]均初始化为0，只是为了递推公式正确，无实际意义
    //
    //     // maxDp[l]记录dp数组下标0~l的左上角区域的最大值
    //     int[] maxDp = new int[len2];
    //     // 由于第0行dp均初始化为0，因此第0行也初始化为0
    //
    //     // dp[j]依赖于上一行的maxDp[j-1]
    //     // 初始已知第0行的dp和maxDp
    //     // 因此可以先从左到右根据第0行的maxDp求第1行的dp，边求边更新maxDp为第1行的maxDp（每求完一个dp[j]，则上一行的maxDp[j-1]就没用了，可以更新为这一行的maxDp[j-1]）
    //
    //     // 遍历顺序，先遍历text1还是text2都可以
    //     for (int i = 1; i <= len1; i++) {
    //         for (int j = 1; j <= len2; j++) {   // j必须正序遍历，因为计算maxDp[j-1]需要这一行新计算的maxDp[j-2]，及覆盖后的值
    //             // 先根据上一行的maxDp求本行的dp
    //             if (text1.charAt(i-1) == text2.charAt(j-1)) {
    //                 dp[j] = maxDp[j-1] + 1;
    //                 res = Math.max(res, dp[j]);
    //             } else {
    //                 dp[j] = 0;
    //             }
    //
    //             // 求完一个本行的dp[j]就可以将对应的maxDp[j-1]更新为本行的maxDp[j-1]
    //             // 不管哪一行的maxDp[0]都保持0即可，其他的maxDp用下面的计算公式更新
    //             if (j >= 2) {
    //                 maxDp[j-1] = Math.max(Math.max(maxDp[j-1], maxDp[j-2]), dp[j-1]);
    //             }
    //         }
    //     }
    //
    //     return res;
    // }


    /**
     * 动态规划 + 二维dp
     *
     * 子序列本质是选或不选的问题
     * 现考虑两个子串的最后一个字符x,y（text1[i-1] = x, text2[j-1] = y），有四种可能：
     * - 这两个子串的最长公共序列 不选x，不选y，则dp[i][j] = dp[i-1][j-1]
     * - 这两个子串的最长公共序列 选x，不选y，则dp[i][j] = dp[i][j-1]
     * - 这两个子串的最长公共序列 不选x，选y，则dp[i][j] = dp[i-1][j]
     * - 这两个子串的最长公共序列 选x，选y，则dp[i][j] = dp[i-1][j-1] + 1
     *
     * 这四种可能什么时候该考虑哪些呢？
     * 1. 如果x == y，是可以都选的，4种可能都有，首先都选肯定比都不选的公共子序列要长（不选白不选），另外都选的长度是大于等于只选一个的（可以举例验证）
     * 所以末尾字符相同可以直接dp[i][j] = dp[i-1][j-1] + 1
     * 2. 如果x != y，则不能都选，只有3种可能，那么需不需要考虑都不选的情况呢？——是不需要的，因为dp[i-1][j-1]可以看作是求dp[i-1][j]不选y的情况或
     * dp[i][j-1]不选x的情况，而如果dp[i-1][j-1]很大，那么在计算dp[i-1][j]和dp[i][j-1]时已经被转移到了它们之中，所以它们是包含了dp[i-1][j-1]的值的，
     * 即dp[i-1][j] >= dp[i-1][j-1]，dp[i][j-1] >= dp[i-1][j-1]
     * @param text1
     * @param text2
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为text1和text2的长度
    // 时间复杂度：O(mn)
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length(), len2 = text2.length();

        // dp[i][j]：text1的前i个字符组成的子串（即text1[0:i-1]）和text2的前j个字符组成的子串（即text2[0:j-1]）的最长公共子序列长度
        // 这里不定义为下标0~i和下标0~j的子串的最长公共子序列长度是为了统一写法，否则i=0和j=0的情况需要根据首字符单独初始化，写法较繁琐
        int dp[][] = new int[len1 + 1][len2 + 1];

        // 递推公式
        // 如果最后一个字符相同，即text1[i-1] = text2[j-1]，则dp[i][j] = dp[i-1][j-1] + 1
        // 如果最后一个字符不同，即text1[i-1] != text2[j-1]，则dp[i][j] = max{dp[i-1][j], dp[i][j-1]}
        // 直观理解第二种情况：如果最后一个字符不同，则最多只有其中之一是包含在最长公共子序列中的，从dp[i][j]的定义上理解，dp[i-1][j-1]已经被dp[i-1][j]和dp[i][j-1]考虑到了

        // 初始化，dp[0][j]和dp[i][0]表示空串与另一个字符串的子串的最长公共子序列长度，当然为0

        // 遍历顺序，先遍历text1还是text2都可以
        for (int i = 1; i <= len1; i++) {   // i必须正序遍历
            for (int j = 1; j <= len2; j++) {   // j必须正序遍历
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[len1][len2];
    }
}
