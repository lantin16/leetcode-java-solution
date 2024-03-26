package findMin;

/**
 * No.153 寻找旋转排序数组中的最小值
 *
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 *
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 *
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 */

public class Solution {

    // 旋转一次相当于将最后一个元素拿到最前面，无论旋转多少次，仍然是局部有序的两部分
    // 思路跟 No.33 搜索旋转排序数组 类似
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        int min = Integer.MAX_VALUE;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[left] <= nums[mid]) {  // left到mid为顺序区间
                min = Math.min(min, nums[left]);    // 该区间中left最小，只用跟nums[left]比就行
                left = mid + 1; // 下次到右边的区间找找有没有更小的
            } else {    // mid到right为顺序区间
                min = Math.min(min, nums[mid]);   // 该区间中mid最小
                right = mid - 1;    // 下次到左边的区间找找有没有更小的
            }
        }

        return min;
    }

}
