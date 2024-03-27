package dailyTemperatures;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.739 每日温度
 *
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 */

public class Solution {


    // // 栈
    // // 时间复杂度：O(n)，其中n为temperatures数组长度
    // // 空间复杂度：O(n)，其中n为temperatures数组长度。分析：极端情况下温度数组呈非升序排列，栈中最多存n+1个元组
    // public int[] dailyTemperatures(int[] temperatures) {
    //     int len = temperatures.length;
    //     int[] res = new int[len];
    //
    //     int i = 0;
    //     Deque<Integer[]> stk = new LinkedList<>();  // 每次存入一个元组，第一个元素是温度值，第二个是它在数组中的索引（存放索引是为了弹出它时知道该设置哪个res）
    //     stk.push(new Integer[]{Integer.MAX_VALUE, -1}); // 底部放一个最大值，谁都不能让它出栈，便于统一循环中的写法
    //
    //     while (i < len) {
    //         if (temperatures[i] <= stk.peek()[0]) { // 如果当前温度不大于栈顶元素的温度值，则入栈
    //             stk.push(new Integer[]{temperatures[i], i});
    //             i++;
    //         } else {    // 如果当前温度大于栈顶元素的温度值
    //             // 将栈顶元素出栈
    //             Integer[] tmp = stk.pop();
    //             // 更新它的res数组对应元素
    //             int idx = tmp[1];
    //             res[idx] = i - idx;
    //             // 这里不更新i，因为要将栈顶元素弹出到>=temperatures[i]才停止，这样第i个温度才能入栈，i才后移
    //         }
    //     }
    //
    //     // temperatures遍历完跳出循环后，如果栈中还有温度值元组，则将它们全部取出来，对应res都设为0
    //     while (stk.size() > 1) {    // 最底下的是MAX_VALUE，不用管
    //         Integer[] tmp = stk.pop();
    //         res[tmp[1]] = 0;
    //     }
    //     return res;
    // }



    // 单调栈
    // 可以维护一个存储下标的单调栈，从栈底到栈顶的下标对应的温度列表中的温度依次递减。如果一个下标在单调栈里，则表示尚未找到下一次温度更高的下标。
    // 其实跟上面自己写的差不多，只不过栈中只存储下标即可，因为通过下标就可以到temperatures数组中找到对应的温度值了
    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        int[] res = new int[len];

        Deque<Integer> stk = new LinkedList<>();

        for (int i = 0; i < len; i++) {
            // 如果当前温度大于栈顶下标对应的温度，则不断弹出栈顶元素并设置对应res直至当前温度不大于栈顶对应的温度，再将当前下标入栈
            while (!stk.isEmpty() && temperatures[stk.peek()] < temperatures[i]) {
                int preIdx = stk.pop();
                res[preIdx] = i - preIdx;
            }
            stk.push(i);
        }

        // 最后单调栈中可能还留有一个递减序列，这些元素下标本应出栈然后将对应的res部分设为0，但由于res初始化时默认是零，因此可以不用处理
        return res;
    }
}
