package fib;

/**
 * No.509 斐波那契数
 *
 * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 给定 n ，请计算 F(n) 。
 */

public class Solution {

    /**
     * 动态规划五步曲：
     * 1. 确定dp数组及下标的含义
     * 2. 确定递推公式
     * 3. dp数组如何初始化
     * 4. 确定遍历顺序
     * 5. 举例推导验证dp数组
     */
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(n)
    // public int fib(int n) {
    //     if (n <= 1) {
    //         return n;
    //     }
    //
    //     int[] dp = new int[n + 1];  // 1. dp[i]代表数i的斐波那契值
    //
    //     // 2. 递推公式：题目已给，F(n) = F(n - 1) + F(n - 2)，其中 n > 1
    //
    //     // 3. dp数组初始化
    //     dp[0] = 0;
    //     dp[1] = 1;
    //
    //     // 4. 遍历顺序：由于每个斐波那契值都只与它前两个斐波那契值有关，因此从前往后遍历
    //     for (int i = 2; i <= n; i++) {
    //         dp[i] = dp[i-1] + dp[i-2];
    //     }
    //
    //     return dp[n];
    // }


    /**
     * 动态规划（优化空间复杂度）
     * 由于每个数只与前两个数有关，因此其实不需要用dp数组来存前面所有的状态，只用两个变量存前两个数即可，不断地更新这两个变量
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int pre2 = 0;   // 前两个
        int pre1 = 1;   // 前一个数
        int cur = 0;    // 当前的

        for (int i = 2; i <= n; i++) {
            cur = pre2 + pre1;
            pre2 = pre1;
            pre1 = cur;
        }
        return cur;
    }


    /**
     * 递归写法
     * 从后往前求，要算f(n)就得先算f(n-2)和f(n-1)
     * 存在许多重复计算，时间空间性能都不好，但代码简单
     */
    // // 时间复杂度：O(2^n)。分析每次递归操作要求两个fib，最后要求的接近于一棵满二叉树的结点，根据等比数列求和公式知结点数为O(2^n)
    // // 空间复杂度：O(n)。分析：算递归栈的深度为数的高度，为O(n)。
    // public int fib(int n) {
    //     if (n <= 1) {
    //         return n;
    //     }
    //
    //     return fib(n - 1) + fib(n - 2);
    // }



    /**
     * 递归，尾递归优化
     * 比较难理解
     * 从前往后求，初始 n = n，代表还要求n个斐波那契数列项（0直接返回，从1开始算到n，总共要求n个），初始pre2 = f(0) = 0, pre1 = f(1) = 1
     * @param n 还要求的斐波那契数列项的个数
     * @param pre2 前两个数
     * @param pre1 前一个数
     * @return
     */
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(1)。分析：尾递归优化，在递归后面函数结束前不需要其他操作，只有一个栈帧被不断地更新，因此空间复杂度为O(1)。
    // public static int fib(int n, int pre2, int pre1) {
    //     if (n == 0) {
    //         return pre2;
    //     }
    //     return fib(n-1, pre1, pre2 + pre1);
    // }
    //
    // public static void main(String[] args) {
    //     System.out.println(fib(4, 0, 1));   // 3
    // }

}
