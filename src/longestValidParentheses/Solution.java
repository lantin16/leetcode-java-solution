package longestValidParentheses;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.32 最长有效括号
 *
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 */

public class Solution {

    /**
     * 题目要求找到括号匹配成功的最长连续字符，可以进行问题转化：
     * 1.匹配成功。用栈来实现，并且为了给第2步做准备，需要在匹配成功时做个记号，这里开辟一个数组，匹配成功时，在'('和")'的索引位置处记为1。
     * 2.最长连续。统计数组里面连续1的个数，最长的那个就是结果
     *
     * @param s
     * @return
     */
    // 时间复杂度：O(n)。分析：遍历两遍，一遍遍历s，第二遍遍历flags
    // 空间复杂度：O(n)
    // public int longestValidParentheses(String s) {
    //     Deque<Integer> stk = new LinkedList<>();    // 小技巧：栈中存储索引而不是具体的括号，因为括号可以通过索引知道，但如果存括号则后面即使匹配上也不知道具体位置
    //     int res = 0;
    //     char[] pars = s.toCharArray();
    //     int n = s.length();
    //     int[] flags = new int[n];   // 标记数组，匹配成功的括号对应位置会被设为1
    //
    //     for (int i = 0; i < n; i++) {
    //         if (pars[i] == '(') {    // 左括号直接入栈
    //             stk.push(i);
    //         } else {    // 右括号则与栈顶左括号匹配
    //             if (!stk.isEmpty()) {    // 由于右括号我们并不入栈，故栈只要非空就全是左括号，所以这里不用继续判断栈顶是否为左括号
    //                 flags[stk.pop()] = 1;   // 弹出栈顶的左括号并设置对应标志位为1
    //                 flags[i] = 1;   // 设置当前有有括号的标志位也为1
    //             }
    //             // 为什么右括号匹配失败不入栈？——因为如果右括号也入栈，那么它在栈中的下面压的只可能是右括号，也就是说栈中右括号都在下层，后面也不可能有括号能使右括号弹出，所以右括号入栈并没有意义
    //         }
    //     }
    //
    //     // 统计连续的1的个数
    //     int curLen = 0;
    //     for (int i : flags) {
    //         if (i == 1) {
    //             curLen += 1;
    //         } else {
    //             res = Math.max(res, curLen);
    //             curLen = 0;
    //         }
    //     }
    //     res = Math.max(res, curLen);    // 最后一次可能没截断，多比较一次
    //     return res;
    // }


    /**
     * 动态规划
     * @param s
     * @return
     */
    // 时间复杂度：O(n)。分析：遍历一遍s
    // 空间复杂度：O(n)
    public int longestValidParentheses(String s) {
        int res = 0;
        char[] pars = s.toCharArray();  // 为访问方便先转成字符数组
        int n = s.length();

        // dp[i]：以pars[i]的括号结尾的最长括号子串长度
        int[] dp = new int[n];

        // 递推公式，dp[i]取决于当前括号和前一个括号，并且可能加上前面的某个dp

        // 初始化：dp[i] = 0

        // 从左往右遍历
        for(int i = 0; i < n; i++) {
            if(pars[i] == '(') {    // 如果当前为左括号
                dp[i] = 0;  // 因为任何有效括号子串一定是以右括号结尾
            } else {    // 当前为右括号，则需要考虑往前多少是有效括号
                if(i - 1 >= 0) {    // 看前一个括号
                    if(pars[i-1] == '(') {  // 前一个是左括号，则一定能跟当前的右括号组成长度为2的有效括号子串
                        dp[i] = 2 + (i - 2 >= 0 ? dp[i-2] : 0); // 进一步考虑前一个之前的的情况（如果到pars[i-2]是有效的，加上后面这个长度为2的有效一定也有效）
                    } else {    // 前一个是右括号
                        int midLen = dp[i-1];   // 以前一个右括号结尾的有效长度
                        // 存在以前一个右括号结尾的有效括号且跳过这一段有效继续再找前一个是左括号，则代表当前右括号能够和较前的一个左括号匹配上
                        if(midLen != 0 && i - midLen - 1 >= 0 && pars[i - midLen - 1] == '(') {
                            dp[i] = 2 + midLen + (i - midLen - 2 >= 0 ? dp[i - midLen - 2] : 0);    // 后面加的dp[i - midLen - 2]当匹配上后其实更前面还有可以加的有效长度，如()(())
                        }
                        // 若以前一个右括号结尾构不成有效括号或者能够成但再前一个不是左括号则当前右括号无法匹配上，维持dp[i]=0
                    }
                    res = Math.max(dp[i], res); // 更新结果保持最大连续长度
                }
            }
        }
        return res;
    }
}

