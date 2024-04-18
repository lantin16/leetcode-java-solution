package rob_213;

/**
 * No.213 打家劫舍Ⅱ
 *
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 */

public class Solution {

    /**
     * 动态规划
     * 与No.198打家劫舍Ⅰ唯一的区别是：
     * 198题中的房屋是单排排列的。本题房屋是环状排列（首尾相接）的，因此首尾房屋还不能同时抢
     *
     * 环状排列意味着第一个房子和最后一个房子中最多只能选择一个偷窃（可能都不偷），因此可以把此环状排列房间问题约化为两个单排排列房间子问题：
     * 1. 在不偷窃第一个房子的情况下（即从nums[1:n-1]中按照单排排列偷），最大金额是 resWithoutFirst
     * 2. 在不偷窃最后一个房子的情况下（即从nums[0:n-2]中按照单排排列偷），最大金额是 resWithoutLast
     * 3. 综合偷窃最大金额： 为以上两种情况的较大值。
     *
     * 注意：首尾房子都不偷的情况被包含在1，2中考虑了（具体房间偷与不偷交给递推公式去抉择，如果首尾都不偷的情况并不能偷到最大金额，则不会被选到子结果中），
     * 因此只用取1，2的结果较大值即可
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)，其中n为nums数组的长度。分析：两次子问题dp，每次时间复杂度O(n)
    // 空间复杂度：O(n)。分析：用两个额外的一维dp数组
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int resWithoutLast = robAction(nums, 0, nums.length - 2);
        int resWithoutFirst = robAction(nums, 1, nums.length - 1);

        return Math.max(resWithoutFirst, resWithoutLast);
    }

    /**
     * 抽象出打家劫舍Ⅰ计算单排排列的结果
     * @param nums
     * @param start
     * @param end
     * @return
     */
    public int robAction(int[] nums, int start, int end) {
        int n = end - start + 1;
        if (n == 1) {
            return nums[start];
        }

        int[] dp = new int[n];

        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-2] + nums[start + i], dp[i-1]);
        }

        return dp[n-1];
    }
}
