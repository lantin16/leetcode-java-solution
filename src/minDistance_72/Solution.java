package minDistance_72;

/**
 * No.72 编辑距离
 *
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数。
 * 你可以对一个单词进行如下三种操作：
 * - 插入一个字符
 * - 删除一个字符
 * - 替换一个字符
 */

public class Solution {

    /**
     * 动态规划 + 二维dp（更好理解，掌握）
     *
     * 如何理解“dp[i-1][j-1]表示替换操作，dp[i-1][j]表示删除操作，dp[i][j-1]表示插入操作。”？
     *
     * 1. dp[i-1][j-1] (表示替换)：word1的前i-1个字符已经变换到word2的前j-1个字符的次数，说明word1的前i-1个已经完成到word2的前j-1个字符的转换；
     * 那么还剩一个word1的第i个怎么变成word2的第j个呢？这两个字符都存在，那么只能是替换了；所以dp[i][j] = dp[i-1][j-1]+1;
     *
     * 2. dp[i][j-1] (表示插入)：word1的前i个字符已经变换到word2的前j-1个字符的次数，当前word1的第i个字符已经用了，但是word2还差一个字符（因为当前只是处理了word2的前j-1个字符），
     * 那么在word1的第i个字符后面再插入一个word2的第j个字符就好了；所以dp[i][j] = dp[i][j-1]+1;
     *
     * 3. dp[i-1][j] (表示删除)：word1的前i-1个字符已经变换到word2的前j个字符的次数，当前word1仅用了前i-1个字符就完成了转换到word2的前j个字符的操作，
     * 所以word1的第i个字符其实没啥用了，那么删除就好了；所以dp[i][j] = dp[i-1][j]+1;
     *
     * @param word1
     * @param word2
     * @return
     */
    // // 时间复杂度：O(mn)，其中m，n分别为word1和word2的长度
    // // 时间复杂度：O(mn)
    // public int minDistance(String word1, String word2) {
    //     char[] charArr1 = word1.toCharArray();
    //     char[] charArr2 = word2.toCharArray();
    //     int l1 = word1.length(), l2 = word2.length();
    //
    //     // dp[i][j]：word1的前i个字符的子串转换为word2的前j个字符的子串需要的最少操作数
    //     // 即 word1[0:i-1]到word2[0:j-1]的编辑距离
    //     int[][] dp = new int[l1 + 1][l2 + 1];
    //
    //     // 递推公式
    //     // 如果最后一个字符是相同的，则不操作最后的字符是最优解
    //     // 即若word1[i-1] == word2[j-1]，则dp[i][j] = dp[i-1][j-1]
    //
    //     // 如果最后一个字符是不同的，则可能：
    //     // 1. 往word1的前i-1个字符后插入一个word2[j-1]，最少操作数为dp[i][j-1] + 1
    //     // 2. 将word1[i-1]删除，最少操作数为dp[i-1][j] + 1
    //     // 3. 将word1[i-1]替换为word2[j-1]，最少操作数为dp[i-1][j-1] + 1
    //     // 即若word1[i-1] != word2[j-1]，则dp[i][j] = min{dp[i][j-1] + 1, dp[i-1][j] + 1, dp[i-1][j-1] + 1}
    //
    //     // 初始化
    //     // 将word1[0:i-1]转换为空串需要删除i次
    //     for (int i = 0; i <= l1; i++) {
    //         dp[i][0] = i;
    //     }
    //     // 将空串转换为word2[0:j-1]需要增加j次
    //     for (int j = 0; j <= l2; j++) {
    //         dp[0][j] = j;
    //     }
    //
    //     // 遍历顺序
    //     for (int i = 1; i <= l1; i++) {
    //         for (int j = 1; j <= l2; j++) {
    //             if (charArr1[i-1] == charArr2[j-1]) {
    //                 // 不操作最优
    //                 dp[i][j] = dp[i-1][j-1];
    //             } else {
    //                 // 考虑最后一步是增、删、改三种情况的最少操作数，取三者的最小值
    //                 dp[i][j] = Math.min(Math.min(dp[i][j-1], dp[i-1][j]), dp[i-1][j-1]) + 1;
    //             }
    //         }
    //     }
    //     return dp[l1][l2];
    // }


    /**
     * 动态规划 + 一维滚动数组（性能更优）
     * 由于计算dp[j]只依赖于上一行的dp[j]和dp[j-1]以及这一行刚计算出的dp[j-1]，因此还可以进一步压缩dp数组，同时必须维护一个滚动变量来记录上一行的dp[j-1]（左上角）
     *
     * @param word1
     * @param word2
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为word1和word2的长度
    // 时间复杂度：O(n)，其中n为word2的长度
    public int minDistance(String word1, String word2) {
        char[] charArr1 = word1.toCharArray();
        char[] charArr2 = word2.toCharArray();
        int l1 = word1.length(), l2 = word2.length();

        // dp[j]：word1的前i个字符的子串转换为word2的前j个字符的子串需要的最少操作数
        // 即 word1[0:i-1]到word2[0:j-1]的编辑距离
        int[] dp = new int[l2 + 1];

        // 递推公式
        // 如果最后一个字符是相同的，则不操作最后的字符是最优解
        // 即若word1[i-1] == word2[j-1]，则dp[j] = lastPre

        // 如果最后一个字符是不同的，则可能：
        // 1. 往word1的前i-1个字符后插入一个word2[j-1]，最少操作数为dp[j-1] + 1
        // 2. 将word1[i-1]删除，最少操作数为dp[j] + 1
        // 3. 将word1[i-1]替换为word2[j-1]，最少操作数为lastPre + 1
        // 即若word1[i-1] != word2[j-1]，则dp[i][j] = min{dp[j-1] + 1, dp[j] + 1, lastPre + 1}

        // 初始化
        // 将空串转换为word2[0:j-1]需要增加j次
        for (int j = 0; j <= l2; j++) {
            dp[j] = j;
        }

        // 遍历顺序
        // 计算dp[j]依赖于上一行的dp[j]和dp[j-1]以及这一行刚计算出的dp[j-1]
        // 由于需要这一行刚计算的dp[j-1]，因此j必须正序遍历，但正序遍历又会将上一行的dp[j-1]覆盖，因此可以用一个滚动变量再记录上一行的dp[j-1]
        for (int i = 1; i <= l1; i++) {
            int lastPre = dp[0];    // 用于记录上一行的dp[j-1]，相当于二维的左上角dp[i-1][j-1]。在每一行开始时重置为上一行的dp[0]，即i-1
            dp[0] = i;  // 在二维的时候dp[i][0]是统一初始化的，但在一维需要在每个i的开始都为dp[0]赋值，就是二维的dp[i][0] = i
            for (int j = 1; j <= l2; j++) { // j必须正序遍历
                int tmp = dp[j];    // 计算前先记录上一行的dp[j]，否则下面会被这一行刚计算的dp[j]覆盖，后面更新lastPre时要用到
                if (charArr1[i-1] == charArr2[j-1]) {
                    // 不操作最优
                    dp[j] = lastPre;
                } else {
                    // 考虑最后一步是增、删、改三种情况的最少操作数，取三者的最小值
                    dp[j] = Math.min(Math.min(dp[j-1], dp[j]), lastPre) + 1;
                }
                lastPre = tmp;  // 更新lastPre
            }
        }
        return dp[l2];
    }
}
