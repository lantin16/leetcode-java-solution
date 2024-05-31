package jump;

import java.util.Arrays;
import java.util.Map;

/**
 * No.45 跳跃游戏Ⅱ
 * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
 * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
 * 0 <= j <= nums[i]
 * i + j < n
 * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
 */

public class Solution {

    /**
     * 动态规划，类似于爬楼梯
     * @param nums
     * @return
     */
    // 时间复杂度：O(n^2)。分析：对于每一个i，都要遍历其之前的所有j判断是否有更少次数的跳法
    // 空间复杂度：O(n)
    // public int jump(int[] nums) {
    //     int n = nums.length;
    //     // dp[i]：跳到索引i处的最少次数
    //     int dp[] = new int[n];
    //
    //     // 递推公式：dp[i] = min{dp[j] + 1}，其中j < i且从j能够一步跳到i（即j + nums[j] >= i）
    //
    //     // 初始化
    //     Arrays.fill(dp, Integer.MAX_VALUE);
    //     dp[0] = 0;
    //
    //     // 从左往右遍历
    //     for(int i = 1; i < n; i++) {
    //         for(int j = 0; j < i; j++) {
    //             if(j + nums[j] >= i) {  // 从j能够一步跳到i
    //                 dp[i] = Math.min(dp[i], dp[j] + 1);
    //             }
    //         }
    //     }
    //
    //     return dp[n-1];
    // }


    /**
     * 跳之前选择局部最优的下一个起点
     * 每次在可跳范围内选择可以使得下一次跳的更远的位置。
     *
     * 这种写法其实存在重复比较，因为每次都确定具体最优的起跳点，下次就从最优起跳点起跳，
     * 这样下一步的起跳点到上一步的终点之间的点其实完全不用考虑作为再下一步的起跳点，因为它们连上一步选最优起跳点都没选上，现在选作下一步的起跳点只会使步数更多效果却没更好
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    // public int jump(int[] nums) {
    //     int n = nums.length;
    //     int start = 0;  // 起点
    //     int cover = 0;  // 覆盖范围
    //     int step = 0;   // 覆盖范围到达cover的最少跳跃次数
    //
    //     // 只要覆盖范围没有覆盖到终点（索引n-1处）就得一直跳
    //     while (cover < n - 1) {
    //         step++; // 覆盖范围还没到终点，这一步必须得跳，先加上
    //         cover = start + nums[start];    // 这个start是上一步选出的最优的起点（不会小于上一次的覆盖范围），因此cover直接更新从这里跳的最远的位置
    //         if (cover >= n - 1) {  // 这一步已经可以跳到终点
    //             break;
    //         }
    //         // 如果这一步还跳不到终点，需要选择跳到哪里才能使下一步跳更远
    //         int nextStart = start;
    //         int nextCover = cover;  // 下一次的覆盖范围不会小于这一次的覆盖范围（因为这一次就从start跳都能跳到cover处）
    //         for (int j = start + 1; j <= start + nums[start]; j++) { // 尝试将start到start + nums[start]之间各位置分别作为下一步的起点，看能不能跳的更远
    //             if (j + nums[j] > nextCover) {
    //                 nextCover = j + nums[j];
    //                 nextStart = j;
    //             }
    //         }
    //         start = nextStart;  // 找出使得下一步跳的最远的起点更新start
    //     }
    //     return step;
    // }


    /**
     * 不关心每步从哪里起跳，只关心在每步的起跳范围内能否使覆盖范围更大
     * 事实上，每步的起跳范围内起跳一定有某个起跳点会使得覆盖返回更大，否则覆盖范围就无法继续增加，那么就到不了终点，这不符合题目保证的一定可以到达终点
     *
     * 如果第 i 步的起跳点范围为[start, end]，那么第 i 步能到达的最远位置就可以通过遍历[start,end]之间点来找到，最远到达的位置更新作为第(i+1)步的起跳点范围右边界
     * 第(i+1)步起跳点的左边界就从第i步起跳点的右边界开始即可（本来应该是从使得右边界最远的那个最优起跳点作为左边界的，但最优起跳点到右边界的这一段连上一次都没被选上，这一次就更不用考虑了）
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int jump(int[] nums) {
        int n = nums.length;
        int start = 0;  // 起点
        int end = 0;  // 能够到达的最远位置
        int step = 0;   // 覆盖范围到达cover的最少跳跃次数

        // 只要还不能到达终点（索引n-1处）就得一直跳
        while (end < n - 1) {
            step++; // 还没到终点，这一步必须得跳，先加上
            int canJump = 0;    // 记录下一步能够跳到的最远位置，方便更新end
            // 这一步的起跳点范围为[start, end]，遍历每一个可能的起跳点，更新canJump为覆盖到的最远位置（不用管具体从哪里起跳）
            for (int j = start; j <= end; j++) {
                canJump = Math.max(canJump, j + nums[j]);
            }
            start = end + 1;    // 下一步的起跳点只用从这一步的end后一位开始检查即可
            end = canJump;
        }
        return step;
    }




    /**
     * 优化写法，上一个while循环内的for循环j其实是从头走到尾的，因此整个可以只用一个for循环
     */
    // public int jump(int[] nums) {
    //     int n = nums.length;
    //     int end = 0;    // 终点
    //     int cover = 0;  // 覆盖范围
    //     int step = 0;   // 覆盖范围到达cover的最少跳跃次数
    //
    //     for (int i = 0; i < n - 1; i++) {
    //         cover = Math.max(cover, i + nums[i]);
    //         if (i == end) {
    //             end = cover;
    //             step++;
    //         }
    //     }
    //     return step;
    // }

}
