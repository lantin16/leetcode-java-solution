package maxUncrossedLines;

/**
 * No.1035 不相交的线
 *
 * 在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
 * 现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足：
 *  - nums1[i] == nums2[j]
 * - 且绘制的直线不与任何其他连线（非水平线）相交。
 * 请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
 *
 * 以这种方法绘制线条，并返回可以绘制的最大连线数。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * 直线不能相交，相当于在nums1和nums2中找到一个公共子序列，且这个子序列不能改变相对顺序，只要相对顺序不改变，链接相同数字的直线就不会相交。
     * 转换为了最长公共子序列问题
     *
     * 参考题解：https://leetcode.cn/problems/uncrossed-lines/solutions/788174/gong-shui-san-xie-noxiang-xin-ke-xue-xi-bkaas
     *
     * @param nums1
     * @param nums2
     * @return
     */
    // 时间复杂度：O(mn)，其中m，n分别为nums1和nums2的长度
    // 时间复杂度：O(mn)
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;

        // dp[i][j]：nums1的前i个数（即nums1[0:i-1]）和nums2的前j个数（即nums2[0:j-1]）之间绘制的不相交的最大连线数
        //「状态定义」只是说「考虑前 i 个和考虑前 j 个字符」，并没有说「一定要包含第 i 个或者第 j 个字符」（这也是「最长公共子序列 LCS」与「最长上升子序列 LIS」状态定义上的最大不同）。
        int[][] dp = new int[len1 + 1][len2 + 1];

        // 递推公式
        // dp[i][j]的来源有三个方向：dp[i-1][j-1]，dp[i][j-1]， dp[i-1]][j]
        // 如果最后一个数相等，nums1[i-1] == nums2[j-1]，则dp[i][j] = dp[i-1][j-1] + 1
        // 如果最后一个数不等，nums1[i-1] != nums2[j-1]，则dp[i][j] = max{dp[i][j-1], dp[i-1]][j]}

        // 初始化
        // dp[i][0]和dp[0][j]都初始化为0，代表空数组与另一个子数组之间连线，数都没有当然连线数也是0

        // 遍历顺序：先1后2还是先2后1都可以，但是i，j都得正序遍历
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (nums1[i-1] == nums2[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }

        return dp[len1][len2];
    }
}
