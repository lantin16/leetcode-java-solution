package canJump;

/**
 * No.55 跳跃游戏
 *
 * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
 */
public class Solution {

    /**
     * 贪心
     * 不用拘泥于每次究竟跳几步，而是看覆盖范围，覆盖范围内一定是可以跳过来的，不用管是怎么跳的。
     *
     * 如果某一个作为 起跳点 的格子可以跳跃的距离是 3，那么表示后面 3 个格子都可以作为 起跳点
     * 可以对每一个能作为 起跳点 的格子都尝试跳一次，把 能跳到最远的距离（覆盖范围） 不断更新
     * 如果可以一直跳到最后（覆盖范围包括了最后的下标），就成功了
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public boolean canJump(int[] nums) {
        int cover = nums[0];
        for(int i = 0; i <= cover && i < nums.length; i++) {
            cover = Math.max(cover, i + nums[i]);
        }

        return cover >= nums.length - 1;
    }
}
