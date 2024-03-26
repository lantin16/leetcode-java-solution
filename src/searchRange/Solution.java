package searchRange;

import java.util.Arrays;

/**
 * No.34 在排序数组中查找元素的第一个和最后一个位置
 *
 * 你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 *
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 *
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 */

public class Solution {

    // 二分查找
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};   // 初始为未找到状态
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {  // 第一次找到target
                // 由于nums已经按照非递减顺序排列好了，因此target要是有多个那么一定是连续出现，这里用l，r指针向两边搜索直到target的边界
                int l = mid, r = mid;
                while (l-1 >= 0 && nums[l-1] == target) {   // 先判断旁边的没越界且等于target再移动指针，这样退出循环时，指针就指向最边上的target
                    l--;
                }
                while (r+1 < nums.length && nums[r+1] == target) {
                    r++;
                }

                res[0] = l;
                res[1] = r;
                return res;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return res;
    }
}
