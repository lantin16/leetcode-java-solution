package singleNumber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * No.136 只出现一次的数字
 *
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 */

public class Solution {


    /**
     * 用哈希表存储每个数字的出现次数，然后遍历哈希表找出只出现一次的数字
     * @param nums
     * @return
     */
    // // 时间复杂度：O(n)，其中n为nums的长度
    // // 空间复杂度：O(n)，其中n为nums的长度
    // public int singleNumber(int[] nums) {
    //     int res = 0;
    //     HashMap<Integer, Integer> map = new HashMap<>();
    //     for(int num : nums) {
    //         map.put(num, map.getOrDefault(num, 0) + 1);
    //     }
    //
    //     for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
    //         if (entry.getValue() == 1) {
    //             res = entry.getKey();
    //             break;
    //         }
    //     }
    //
    //     return res;
    // }




    /**
     * 先给nums排序，然后遍历找出只出现一次的数字
     * 单次元素只可能出现在排序好的数组的第奇数个
     * @param nums
     * @return
     */
    // // 时间复杂度：O(nlogn)，其中n为nums的长度。分析：排序的时间复杂度为O(nlogn)，遍历的时间复杂度为O(n)
    // // 空间复杂度：O(1)
    // public int singleNumber(int[] nums) {
    //     Arrays.sort(nums);
    //
    //     // 排序后出现两次的元素就相邻了
    //     for (int i = 0; i < nums.length - 1; i += 2) {
    //         if (nums[i] != nums[i+1]) {
    //             return nums[i];
    //         }
    //     }
    //
    //     // 如果没有在for循环里面return说明单次元素为最大的排在最后一个
    //     return nums[nums.length - 1];
    // }



    /**
     * 异或位运算（掌握）
     * 找出数组中奇数次出现次数的数字的经典解法
     *
     * 异或运算满足交换律，a^b^a=a^a^b=b,因此ans相当于nums[0]^nums[1]^nums[2]^nums[3]^nums[4].....
     * 然后再根据交换律把相等的合并到一块儿进行异或（结果为0），然后再与只出现过一次的元素进行异或，这样最后的结果就是，只出现过一次的元素（0^任意值=任意值）
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)，其中n为nums的长度。
    // 空间复杂度：O(1)
    public int singleNumber(int[] nums) {
        int res = nums[0];

        // 将所有元素求异或，最后留下的就是只出现一次的数字
        for (int i = 1; i < nums.length; i++) {
            res = res ^ nums[i];
        }

        return res;
    }
}
