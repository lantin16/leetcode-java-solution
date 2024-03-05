package maxSlidingWindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * leetcode No.239 滑动窗口最大值
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 */

public class Solution {

    // 单调队列实现
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<Integer>();   // 用双向链表来实现单调队列，deque存的是元素下标，下标对应的元素单调递减
        int len = nums.length;
        int i = 0;
        int[] ans = new int[len-k+1];

        // 入。从队尾/右边入队
        for (i = 0; i < k; i++) {   // 前k个元素入队时，队首元素不用出队，但要维护单调队列
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {    // 将队尾小于等于待入队元素的下标全部弹出
                deque.pollLast();
            }
            deque.offerLast(i); // 将下标入队
        }
        ans[0] = nums[deque.peekFirst()];

        for (i = k; i < len; i++) {
            // 出。如果滑动窗口已经不包含队首下标对应的元素，则将下标从队首/左边出队
            if (i - deque.peekFirst() + 1 > k) {
                deque.pollFirst();
            }

            // 入
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {    // 将队尾小于等于待入队元素的下标全部弹出
                deque.pollLast();
            }
            deque.offerLast(i); // 将下标入队

            // 向结果数组插入当前滑动窗口的最大值
            ans[i-k+1] = nums[deque.peekFirst()];
        }

        return ans;
    }
}
