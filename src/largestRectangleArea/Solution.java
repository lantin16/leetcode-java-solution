package largestRectangleArea;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.84 柱状图中的最大的矩形
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 *
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 单调栈的介绍可以看下这篇题解：
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/solutions/108083/84-by-ikaruga/?envType=study-plan-v2&envId=top-100-liked
 */

public class Solution {

    /**
     * 思路：遍历每个高度，是要以当前高度为基准，寻找最大的宽度。组成最大的矩形面积那就是要找左边第一个小于当前高度的下标left，再找右边第一个小于当前高度的下标right，
     * 那宽度就是这两个下标之间的距离了，但是要排除这两个下标，所以是right-left-1， 用单调栈就可以很方便确定这两个边界了
     */
    // 单调栈
    // 细节：1. 这里单调栈存的是索引，根据索引就可以找到对应高度。 2. 在遍历完数组的所有元素后，单调栈中是很可能留有一个单调序列的，因为没有元素使其出栈，根据实际来处理最后的元素。
    public static int largestRectangleArea(int[] heights) {
        int maxArea = Integer.MIN_VALUE;
        Deque<Integer> stk = new LinkedList<>();    // 单调递增栈，栈中记录的是下标，通过下标就可以获得高度

        for (int i = 0; i < heights.length; i++) {
            // 这个 while 很关键，因为有可能不止一个柱形的最大宽度可以被计算出来
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                int idx = stk.pop();
                int h = heights[idx];   // 弹出一个元素就计算以弹出的高度为高，向两边扩展的最大举行面积
                int right = i;  // h右边第一个小于h的下标为i
                // 由于h左边可能有与h相等的多个柱，因此弹出左边栈顶高度相等的，继续向左边寻找比h小的第一个柱子
                while (!stk.isEmpty() && heights[stk.peek()] == h) {
                    stk.pop();
                }

                int left;
                if (!stk.isEmpty()) {
                    left = stk.peek(); // 在上面循环中将h左边挨着的高度等于h的柱子都弹出后，次数栈顶的元素就是h左边第一个小于h的下标
                } else {    // 如果是由于栈空跳出循环
                    left = -1;  // 说明在idx前面没有比h还小的，以h为高度的矩形向左可以扩展到第一个柱子，相当于在-1处有个更矮的柱子阻止了继续扩展
                }

                maxArea = Math.max(maxArea, h * (right - left - 1));
            }
            stk.push(i);    // 当前柱高 >= 栈顶元素柱高时，将当前下标入栈
        }

        // 如果最后栈中还留有一个递增序列，但是右边没有更矮的柱子让它们出栈，也就没有计算以它们为高度的矩形面积
        while (!stk.isEmpty()) {
            int h = heights[stk.pop()];
            while (!stk.isEmpty() && heights[stk.peek()] == h) {    // h左边可能有跟h相等的，要将它们弹出找到第一个比h小的
                stk.pop();
            }
            int curWidth;
            if (stk.isEmpty()) {
                curWidth = heights.length;
            } else {
                curWidth = heights.length - stk.peek() - 1;
            }
            maxArea = Math.max(maxArea, h * curWidth);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        largestRectangleArea(new int[]{1,0,1,0,1});
    }
}
