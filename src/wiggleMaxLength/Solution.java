package wiggleMaxLength;

/**
 * No.376 摆动序列
 *
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。第一个差（如果存在的话）可能是正数或负数。仅有一个元素或者含两个不等元素的序列也视作摆动序列。
 * - 例如， [1, 7, 4, 9, 2, 5] 是一个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负交替出现的。
 * - 相反，[1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
 * 子序列 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。
 *
 * 给你一个整数数组 nums ，返回 nums 中作为 摆动序列 的 最长子序列的长度 。
 */

public class Solution {

    // 贪心 or 动态规划
    public int wiggleMaxLength(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        // 找转折点
        int res = 1;    // 默认最右边的数也是一个摆动序列部分
        int preDiff = 0;    // 当前数 - 前一个数，假想0位置的数前面还有个与0相等的数，故初始为0
        int curdiff;    // 后一个数 - 当前数

        for (int i = 0; i < nums.length - 1; i++) {
            curdiff = nums[i + 1] - nums[i];
            // 规定：非同单调序列间的水平段，只有最后一个点处的转折被计入
            if ((preDiff <= 0 && curdiff > 0) || preDiff >= 0 && curdiff < 0) {
                res++;
                preDiff = curdiff;  // 只有遇到规则内的转折点才更新preDiff，避免两端单调的序列中间夹着水平段的情况多计了转折点
            }
        }
        return res;
    }
}
