package sortColors;

import java.util.Arrays;

/**
 * No.75 颜色分类
 *
 * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
 */

public class Solution {

    /**
     * 冒泡排序
     * @param nums
     */
    // 时间复杂度：O(n^2)
    // 空间复杂度：O(1)
    // public void sortColors(int[] nums) {
    //     int n = nums.length;
    //     boolean swap = true;   // 交换标志
    //     for(int i = 1; i < n && swap; i++) {    // 第i轮冒泡可以确定排序后倒数第i个数
    //         swap = false;   // 每轮冒泡开始重置交换标志
    //         for(int j = 0; j < n-i; j++) {  // 比较交换指针
    //             if (nums[j] > nums[j+1]) {  // 大的往后冒
    //                 swapArr(nums, j, j+1);
    //                 swap = true;
    //             }
    //         }
    //     }
    // }



    public void swapArr(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }



    /**
     * 单指针 + 两次遍历
     * 1. 在第一次遍历中，将数组中所有的 0 交换到数组的头部。
     * 2. 在第二次遍历中，我们将数组中所有的 1 交换到头部的 0 之后。
     * 3. 此时，所有的 2 都出现在数组的尾部
     * @param nums
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    // public void sortColors(int[] nums) {
    //     int n = nums.length;
    //     int cur = 0;    // 当前待交换的位置
    //
    //     for(int i = 0; i < n; i++) {
    //         if(nums[i] == 0) {
    //             swapArr(nums, cur++, i);
    //         }
    //     }
    //
    //     for(int j = cur; j < n; j++) {
    //         if(nums[j] == 1) {
    //             swapArr(nums, cur++, j);
    //         }
    //     }
    // }


    /**
     * 双指针 + 单次遍历
     * 用指针p0来记录0需要交换到的位置，p2交换2需要交换到的位置
     * p0初始值为0，从左往右移动。p2初始值为n-1，从右往左移动
     * cur指向当前检查的元素
     * 在遍历的过程中，我们需要找出所有的 0 交换至数组的头部，并且找出所有的 2 交换至数组的尾部。
     * 对于0和2，交换过来的数字仍然可能是0或2，可能需要多次交换，因此对于0和2，cur不右移，而是一直循环交换直至遇到1才右移
     *
     * @param nums
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public void sortColors(int[] nums) {
        int n = nums.length;
        int cur = 0;
        int p0 = 0, p2 = n - 1;

        while(cur <= p2) {  // 如果遍历位置超过了p2则停止，因为p2右边都已经是归位的2了
            if (nums[cur] == 0) {   // 找到了0
                swapArr(nums, p0, cur); // 与nums[p0]交换
                p0++;
                cur = Math.max(cur, p0);  // cur必须保证不落后于p0，因为p0左边全是0，不需要检查
            } else if (nums[cur] == 2) {    // 找到了2
                swapArr(nums, p2, cur); // 与nums[p2]交换
                p2--;
            } else {    // 找到了1
                cur++;  // cur跳过1，不作交换处理
            }
        }

    }

}
