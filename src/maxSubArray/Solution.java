package maxSubArray;

import java.util.Arrays;

/**
 * leetcode No.53 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组是数组中的一个连续部分。
 */

public class Solution {


    // 滑动窗口
    public int maxSubArray(int[] nums) {
        int l, r;

        int maxPre = Integer.MIN_VALUE; // nums在遇到第一个正数前的最大的数
        // 寻找第一个正数，期间记录前面非正数中的最大值
        for (l = 0; l < nums.length; l++) {
            if (nums[l] > 0) {  // 左指针指向的为正数
                break;
            } else {    // 左指针指向的不为正数
                maxPre = Math.max(maxPre, nums[l]);
            }
        }

        // 如果nums没有正数，则最大子数组和等于该数组中最大的非正数
        if (l == nums.length) {
            return maxPre;
        }

        int res = 0;
        int firstP, lastP; // nums中第一个和最后一个正数的下标
        firstP = l;
        for (r = nums.length-1; r >= firstP; r--) {
            if (nums[r] > 0) {
                break;
            }
        }
        lastP = r;

        if (firstP == lastP) {  // 如果nums中只有一个正数
            return nums[firstP];
        }

        // 如果nums至少有两个正数，则最大子数组和的两端必为两个正数
        // 将遇到的第一个正数位置确定为滑动窗口的左边界
        for (l = firstP; l <= lastP; l++) {
            if (nums[l] <= 0) {
                continue;
            }
            int maxSum = nums[l];  // 以l开头的当前最大的子数组和
            int curSum = nums[l];   // 当前的子数组和
            for (r = l + 1; r <= lastP; r++) {
                curSum += nums[r];
                if (nums[r] > 0) {  // r所指的为正数，需要比较下子数组和
                    maxSum = Math.max(curSum, maxSum);
                }
            }
            res = Math.max(res, maxSum);    // 将以l开头的最大子数组和与全局最大子数组和比较
        }
        return res;
    }



    // // 双指针
    // // 超时，推测是两端为正数的情况太多，而每次子问题求子数组的最大和又需要先遍历整个传入的子数组求初始res，导致超时
    // public int maxSubArray(int[] nums) {
    //     // 如果仅有一个元素，则该元素的值就为最小子数组和
    //     if (nums.length == 1) {
    //         return nums[0];
    //     }
    //
    //     int res = 0;
    //     int l = 0, r = nums.length-1; // 左右指针从两边向中间逼近
    //
    //     while (l < r) {
    //         if (nums[l] > 0 && nums[r] > 0) {   //子数组的左右两端应该是两个正数
    //             int sum = 0;    // l~r的子数组和
    //             for (int i = l; i <= r; i++) {
    //                 sum += nums[i];
    //             }
    //             int sumWithoutL = maxSubArray(Arrays.copyOfRange(nums, l+1, r+1));  // 假如移出左边的正数后的子数组的最大子数组和
    //             int sumWithoutR = maxSubArray(Arrays.copyOfRange(nums, l, r));  // 假如移出右边的正数后的子数组的最大子数组和
    //             res = Math.max(sum, Math.max(sumWithoutL, sumWithoutR));
    //             return res;
    //         } else { // 两端至少有一端不大于0，去掉较小数的那一端，子数组和一定不会减小
    //             if (nums[l] < nums[r]) {    // 去掉左端那一个
    //                 l++;
    //             } else {    // 去掉右端那一个
    //                 r--;
    //             }
    //         }
    //     }
    //
    //     // l=r.最后只剩一个数（nums中的正数少于2个时才会执行到这），只需要将这个数返回即可，无论是正负还是零
    //     res = nums[l];
    //     return res;
    // }
}
