package search;

/**
 * No.33 搜索旋转排序数组
 *
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 *
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 *
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 */

public class Solution {

    // // 二分查找的变体
    // // 旋转后整体而言大数在左边，小数在右边，局部仍然升序
    // // 时间复杂度：O(log n)
    // // 空间复杂度：O(1)
    // public int search(int[] nums, int target) {
    //     int left = 0, right = nums.length - 1;
    //
    //     while (left <= right) {
    //         int mid = left + (right - left) / 2;
    //         // 已经找到target
    //         if (nums[mid] == target) {
    //             return mid;
    //         }
    //
    //         // 此区间仍跨两边
    //         if (nums[left] > nums[right]) {
    //             // mid有两种可能：落在左边的大数部分 or 落在右边的小数部分
    //             if (nums[mid] >= nums[left]) {   // mid落在左边大数部分
    //                 if (target > nums[mid] || target <= nums[right]) {  // target属于比nums[mid]大的大数部分或者属于小数部分都要往右边找
    //                     left = mid + 1;
    //                 } else {    // target属于比nums[mid]小的大数部分，往左边找
    //                     right = mid - 1;
    //                 }
    //             } else {    // mid落在右边小数部分
    //                 if (target < nums[mid] || target >= nums[left]) {   // target属于比nums[mid]小的小数部分或者属于大数部分都要往左边找
    //                     right = mid - 1;
    //                 } else {    // target属于比nums[mid]大的小数部分，往右边找
    //                     left = mid + 1;
    //                 }
    //             }
    //             continue;
    //         }
    //
    //         // 此区间已经在同一边了，正常二分查找
    //         if (nums[left] <= nums[right]) {
    //             if (nums[mid] < target) {
    //                 left = mid + 1;
    //             } else {
    //                 right = mid - 1;
    //             }
    //         }
    //     }
    //     return -1;
    // }


    /**
     * 要点：
     * 定理一：只有在顺序区间内才可以通过区间两端的数值判断target是否在其中。
     * 定理二：判断顺序区间还是乱序区间，只需要对比 left 和 right 是否是顺序对即可，nums[left] <= nums[right]，顺序区间，否则乱序区间。
     * 定理三：每次二分都会至少存在一个顺序区间。
     *
     * 通过不断的用Mid二分，根据定理二，将整个数组划分成顺序区间和乱序区间，然后利用定理一判断target是否在顺序区间，如果在顺序区间，下次循环就直接取顺序区间，如果不在，那么下次循环就取乱序区间。
     */
    // 二分查找
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {  // left到mid是顺序区间
                // 判断target是否在顺序区间内
                // 一种思想：先判断简单的，剩下的丢给else
                if (target >= nums[left] && target < nums[mid]) {  // target在顺序区间内
                    right = mid - 1;    // 将下一次搜索的范围设到顺序区间
                } else {
                    left = mid + 1;
                }
            } else {    // 否则mid到right就是顺序区间
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

        }

        return -1;
    }
}
