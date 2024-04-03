package lastStoneWeightII;

/**
 * No.1049 最后一块石头的重量Ⅱ
 *
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 * - 如果 x == y，那么两块石头都会被完全粉碎；
 * - 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
 */

public class Solution {

    /**
     * 01背包问题，动态规划
     * 如何转化？——将这堆石头划分成尽可能重量相同的两堆，这两堆不断相撞后剩下的石头最小（证明可参考官方题解）
     * 为什么多次两两单个相撞可以等效于两堆相撞？一种理解方式：
     * 先分成两个数值相近的group，每个group里的元素分别相撞，就等效于两组group都同时减去一个小的值，
     * 这样分别减下去就会把较小的元素都消掉，最后只剩一个group有一个或没有元素了，这样就相当于两个相撞的结果
     */
    // 时间复杂度：O(nc)，其中n为石头总数量，c为石头总重量的一半
    // 空间复杂度：O(c)
    public int lastStoneWeightII(int[] stones) {
        int len = stones.length;
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }

        // 转化为在所有石头中任意挑选，划分出最接近 sum/2重量的一堆
        int target = sum / 2;

        // 背包容量为target，每个石头的重量和价值相等，都为stones[i]
        // 1. dp[j] 表示容量为j的背包所能装的石头的最大重量/价值为dp[j]
        int[] dp = new int[target + 1];

        // 2. 递推公式：dp[j] = max{dp[j], dp[j-stones[i]] + stones[i]}

        // 3. 初始化，dp[0]一定为0，其他也初始化为0即可，那么i就要从0开始遍历

        // 4. 遍历顺序，先物品再背包
        for (int i = 0; i < len; i++) {
            for (int j = target; j >= stones[i] ; j--) {    // j倒序
                dp[j] = Math.max(dp[j], dp[j-stones[i]] + stones[i]);
            }
        }

        // 遍历完所有i后的dp[target]就为容量为 sum/2的背包所能装的石头的最大重量
        return sum - dp[target] * 2;    // 由于 sum/2是向下取整，故重量为dp[target]的那一堆最后肯定撞没了，另一堆也得减去相等的重量，剩下的就是结果

    }
}
