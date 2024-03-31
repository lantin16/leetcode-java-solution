package numTrees;

import java.util.Arrays;

/**
 * No.96 不同的二叉搜索树
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 */

public class Solution {

    /**
     * 动规
     */
    // 时间复杂度：O(n^2)
    // 空间复杂度：O(n)
    public static int numTrees(int n) {
        // 1. dp[i]表示由1到i共i个不同的整数组成的二叉搜索树有多少种
        int[] dp = new int[n + 1];

        // 2. 递推公式：i从1遍历到n，以数i为根节点，则小于i的(i-1)个数位于左子树，大于i的(n-i)个数位于右子树
        // 左右子树又分别是结点个数为(i-1)和(n-i)的二叉搜索数，因此递推公式为：dp[i] = Σ(dp[i-1] * dp[n-i])

        // 3. 初始化
        dp[0] = 1;  // 0个结点也是一棵空的二叉树，另外如果将dp[0]初始化为0的化，后面多个结点如果某棵子树结点数为0则乘上另一边子树的种类就变0了，显然不符合实际

        // 4. 遍历顺序：从前往后
        for (int i = 1; i <= n; i++) {
            // 遍历1~i的每一个结点都作为根节点的二叉搜素数种类
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j-1] * dp[i-j];
            }
        }

        // System.out.println(Arrays.toString(dp));

        return dp[n];
    }

    public static void main(String[] args) {
        numTrees(3);
    }
}
