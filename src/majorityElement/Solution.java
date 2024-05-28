package majorityElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * No.169 多数元素
 *
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 */

public class Solution {

    /**
     * 暴力解法
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(n)
    // public int majorityElement(int[] nums) {
    //     Map<Integer, Integer> map = new HashMap<>();
    //     for(int num : nums) {
    //         map.put(num, map.getOrDefault(num, 0) + 1);
    //     }
    //     int res = 0;
    //     int maxCnt = 0;
    //     for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
    //         if(entry.getValue() > maxCnt) {
    //             maxCnt = entry.getValue();
    //             res = entry.getKey();
    //         }
    //     }
    //     return res;
    // }


    /**
     * 排序法，返回最中间的数一定是众数（反证法可证）
     * @param nums
     * @return
     */
    // 时间复杂度：O(nlogn)。分析：数组排序的时间复杂度为O(nlogn)
    // 空间复杂度：O(logn)。分析：Arrays.sort()方法做数组排序的需要O(logn)的栈空间
    // public int majorityElement(int[] nums) {
    //     Arrays.sort(nums);
    //     return nums[nums.length / 2];
    // }





    /**
     * 摩尔投票法
     *
     * 记遇到众数票数+1，非众数票-1，则一定有：所有数字的 票数和 > 0
     * 若数组前a个数字的票数和 = 0，代表前a个数字中众数和非众数的个数相等（相互抵消），那么后(n-a)个数字的票数和一定 > 0，即后(n-a)个数字的众数仍为全局的众数
     * 使得前面票数和=0有可能是局部众数和非众数抵消，即非众数之间内耗，但这并不影响结果，因为非众数的抵消只会进一步使得众数更占优势。
     *
     * 候选人初始化为nums[0]，票数初始化为1
     * 当遇到和候选人相同的数，则票数+1，否则-1
     * 检查票数是否=0，如果是，说明之前的候选人可能已经不具有多数，则将下一个数更换为候选人，票数重置为1
     * （更换候选人只是说明当前该候选人可能不符合，如果它真的是众数，后面会被再次换回来）
     * 最后留下的候选人就是全局的众数
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int majorityElement(int[] nums) {
        int n = nums.length;
        int vote = 1;
        int cand = nums[0];

        for(int i = 1; i < n; i++) {
            if (nums[i] == cand) {
                vote++;
            } else {
                vote--;
            }

            if (vote == 0 && i < n-1) {
                cand = nums[++i];
                vote = 1;
            }
        }

        return cand;
    }
}
