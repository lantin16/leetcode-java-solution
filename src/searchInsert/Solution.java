package searchInsert;

/**
 * No.35 搜索插入位置
 *
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * nums 为 无重复元素 的 升序 排列数组
 * 请必须使用时间复杂度为 O(log n) 的算法。
 */

public class Solution {

    /**
     * 为什么跳出循环后要插入target的位置就是left呢？
     * left > right跳出循环且相差不会超过1
     * 最后一次while循环有两种可能使得跳出循环：
     * 1. left == right，此时mid = left = right。若nums[mid] < target使得跳出循环。则left = mid + 1 = right + 1，跳出循环后left就是要插入的位置；
     * 若nums[mid] > target使得跳出循环。则right = mid - 1 = left - 1，left指向没变，left原来的数要腾位置给target，跳出循环后left就是要插入的位置。
     *
     * 2. left + 1 == right，此时mid = left = right - 1。若nums[mid] > target，则right = left - 1跳出循环，如[1,2]要搜索0，跳出循环后0应该放在1的位置（left的位置），1往后挪
     *
     *
     */
    // 二分查找，注意边界条件，记住下面这个模板
    // 时间复杂度：O(log n)，其中n为数组长度。
    // 空间复杂度：O(1)
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

}
