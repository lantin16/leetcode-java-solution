package productExceptSelf;

/**
 * leetcode No.238 除自身以外数组的乘积
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
 *
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 *
 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
 */

public class Solution {

    // 前缀积、后缀积的思想
    // 由于输出数组不算在空间复杂度内，因此可以就用输出数组来存储preProductExceptSelf，然后再用一次遍历边计算postProductExceptSelf边乘到输出数组中
    // 空间复杂度 O(1)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // 定义：preProductExceptSelf[i] ：nums[i]之前的（不包括nums[i]）的元素的乘积
        // 就用输出数组来存储preProductExceptSelf
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i-1] * nums[i-1];
        }

        // 再用一次遍历边计算postProductExceptSelf边乘到输出数组中得到最终结果
        int post = 1;
        for (int j = 1; j < n; j++) {
            answer[n-j-1] = post * nums[n-j] * answer[n-j-1];   // 这里的post * nums[n-j]其实就等于postProductExceptSelf[n-j-1]
            post *= nums[n-j];
        }

        return answer;
    }


    // 前缀积、后缀积的思想
    // preProductExceptSelf和postProductExceptSelf均需要额外占用空间，空间复杂度 O(N)
    // public int[] productExceptSelf(int[] nums) {
    //     int n = nums.length;
    //     int[] answer = new int[n];
    //
    //     // 定义：
    //     // preProductExceptSelf[i] ：nums[i]之前的（不包括nums[i]）的元素的乘积
    //     // postProductExceptSelf[i] ：nums[i]之后的（不包括nums[i]）的元素的乘积
    //     int[] preProductExceptSelf = new int[n];
    //     int[] postProductExceptSelf = new int[n];
    //
    //     preProductExceptSelf[0] = 1;
    //     postProductExceptSelf[n-1] = 1;
    //     for (int i = 1; i < n; i++) {
    //         preProductExceptSelf[i] = preProductExceptSelf[i-1] * nums[i-1];
    //         postProductExceptSelf[n-i-1] = postProductExceptSelf[n-i] * nums[n-i];
    //     }
    //
    //     // 计算结果
    //     for (int k = 0; k < n; k++) {
    //         answer[k] = preProductExceptSelf[k] * postProductExceptSelf[k];
    //     }
    //
    //     return answer;
    // }
}
