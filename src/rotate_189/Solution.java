package rotate_189;

/**
 * leetcode No.189 轮转数组
 *
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 */
public class Solution {

    // 三次数组反转
    public static void rotate(int[] nums, int k) {
        // 通过部分反转再拼接来坐
        int len = nums.length;
        // 若轮转步数为长度的倍数相当于没轮转，所以只需要轮转k取模后的数
        k = k % len;

        // 将前(len-k)个元素和后k个元素分别在各自子数组段部分反转
        reverse(nums, 0, len-k-1);
        reverse(nums, len-k, len-1);

        // 将nums整体反转
        reverse(nums, 0, len-1);
    }

    /**
     * 反转数组：[1,2,3,4] -> [4,3,2,1]
     * @param nums
     * @return
     */
    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8,9};
        int k =3;
        rotate(nums, 3);
        for (int num : nums){
            System.out.println(num);
        }
    }
}
