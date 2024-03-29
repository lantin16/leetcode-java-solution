package trap;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.42 接雨水
 *
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 */

public class Solution {

    /**
     * 双指针
     * 按列计算
     * 当前列雨水面积：min(左边柱子的最高高度，右边柱子的最高高度) - 当前柱子高度。
     */
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(n)
    // public int trap(int[] height) {
    //     int sum = 0;
    //     int len = height.length;
    //     int[] maxLeft = new int[len];
    //     int[] maxRight = new int[len];
    //
    //     maxLeft[0] = height[0];
    //     maxRight[len - 1] = height[len - 1];
    //
    //     // 遍历两遍来计算每个位置的左边柱子最大高度和右边柱子最大高度
    //     for (int i = 1; i < len; i++) {
    //         maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]);   // 当前位置左边的最高高度是前一个位置的左边最高高度和本高度的最大值。
    //     }
    //     for (int i = len - 2; i >= 0; i--) {
    //         maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]);   // 当前位置右边的最高高度是后一个位置的右边最高高度和本高度的最大值。
    //     }
    //
    //     // 求每一列能接的雨水面积
    //     for (int i = 0; i < len; i++) {
    //         int h = Math.min(maxLeft[i], maxRight[i]);
    //         sum = sum + (h > height[i] ? h - height[i] : 0);    // 由于每一列的宽度是1，因此面积只需要看高度
    //     }
    //
    //     return sum;
    // }


    /**
     * 单调栈
     * 按行计算
     */
    public int trap(int[] height) {
        int sum = 0;
        Deque<Integer> stk = new LinkedList<>();    // 单调递减（不增）栈，栈中存的是下标，通过下标可以获取高度
        stk.push(0);    // 先将第一个柱子的下标入栈

        for (int i = 1; i < height.length; i++) {
            if (height[i] <= height[stk.peek()]) {   // 如果当前高度<=栈顶下标的高度，直接入栈
                stk.push(i);
            } else {    // 如果当前高度大于栈顶下标的高度
                while (!stk.isEmpty() && height[stk.peek()] < height[i]) {  // 注意这里是while，因为可能弹出栈顶后新的栈顶元素高度仍然当前高度
                    int mid = stk.pop(); // 栈顶元素（凹槽底的高度下标）
                    if (!stk.isEmpty()) {   // 栈中再下一个元素就是凹槽的左边界下标
                        int lh = height[stk.peek()];    // 凹槽左边的边界柱子高度
                        int h = Math.min(lh, height[i]) - height[mid];    // 这个凹槽装水的高度
                        int w = i - stk.peek() - 1; // 凹槽的宽度
                        sum += h * w;
                    }
                }
                stk.push(i);
            }
        }
        return sum;
    }
}
