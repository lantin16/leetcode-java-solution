package numDistinct;

import java.util.Arrays;

/**
 * No.115 不同的子序列
 *
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 10^9 + 7 取模。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * @param s
     * @param t
     * @return
     */
    // // 时间复杂度：O(mn)，其中m，n分别为s和t的长度
    // // 时间复杂度：O(mn)，其中m，n分别为s和t的长度
    // public int numDistinct(String s, String t) {
    //     int sl = s.length(), tl = t.length();
    //
    //     // dp[i][j]：在s的前i个字符的子串的子序列中出现t的前j个字符的子串的个数
    //     // 即 在s[0:i-1]的子序列中出现t[0:j-1]的个数
    //     int[][] dp = new int[sl + 1][tl + 1];
    //
    //     // 递推公式
    //     // 如果末尾字符相同，即s[i-1] == t[j-1]，有两种情况：
    //     // 一种是s[i-1]要算在子序列中，那么就要继续在s的前i-1个字符放入子序列中匹配t[0:j-2]
    //     // 另一种是s[i-1]不算在子序列中，在s的前i-1个字符的子序列就已经可以匹配上t[0:j-1]
    //     // 故若s[i-1] == t[j-1]，dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
    //     //
    //     // 如果末尾字符不同，即s[i-1] != t[j-1]，只有一种情况：
    //     // dp[i][j] = dp[i-1][j]，即s[i-1]肯定不会被算在子序列中，只有到前面找t[0:j-1]
    //
    //     // 初始化
    //     // 第0列dp[i][0]都初始化为1，代表用空串去匹配s的前i个字符组成子串的子序列（即s[0:i-1]的子序列中）算一个
    //     // 第0行除了dp[0][0]外都初始化为0，代表拿t的一部分非空串去匹配一个空串的子序列（也是空串），当然匹配不上
    //     for (int i = 0; i <= sl; i++) {
    //         dp[i][0] = 1;
    //     }
    //
    //     // 遍历顺序
    //     for (int i = 1; i <= sl; i++) {
    //         for (int j = 1; j <= tl; j++) {
    //             if (s.charAt(i-1) == t.charAt(j-1)) {
    //                 dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
    //             } else {
    //                 dp[i][j] = dp[i-1][j];
    //             }
    //         }
    //     }
    //
    //     return dp[sl][tl];
    // }




    /**
     * 动态规划 + 一维滚动数组
     * 注意到求dp[i][j]只跟上一行的dp[i-1][j-1]和dp[i-1][j]有关，因此可以压缩dp数组
     * 优化空间复杂度
     *
     * 另外，如果在一开始就将字符串s和t转为字符数组存储起来，在用s[i-1]访问字符比起s.charAt(i-1)速度更快，
     * 但是会需要额外两个char数组的空间（空间换时间）
     *
     * @param s
     * @param t
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为s和t的长度
    // 时间复杂度：O(n)，其中m，n分别为s和t的长度。分析：字符数组的空间复杂度为O(m)和O(n)，dp数组的空间复杂度为O(n)
    public int numDistinct(String s, String t) {
        // 先将字符串s和t转为字符数组存储起来，后面用s[i-1]访问字符比起s.charAt(i-1)速度更快
        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();
        int sl = s.length(), tl = t.length();

        // dp[j]：在s[0:i-1]的子序列中出现t[0:j-1]的个数
        int[] dp = new int[tl + 1];

        // 递推公式
        // 如果s[i-1] == t[j-1]，则dp[j] = dp[j-1] + dp[j]
        // 如果s[i-1] != t[j-1]，则dp[j] = dp[j]

        // 初始化
        // dp[0] = 1，其余都初始化为0，其实就是二维dp的第0行
        dp[0] = 1;

        // 遍历顺序
        for (int i = 1; i <= sl; i++) {
            for (int j = tl; j >= 1; j--) { // 注意j必须倒序遍历
                if (sc[i-1] == tc[j-1]) {
                    dp[j] = dp[j-1] + dp[j];
                }
            }
        }

        return dp[tl];
    }


}
