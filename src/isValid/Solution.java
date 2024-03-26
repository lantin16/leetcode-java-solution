package isValid;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.20 有效的括号
 *
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 *
 * 有效字符串需满足：
 * 1. 左括号必须用相同类型的右括号闭合。
 * 2. 左括号必须以正确的顺序闭合。
 * 3. 每个右括号都有一个对应的相同类型的左括号。
 */

public class Solution {

    // 栈
    // 时间复杂度：O(n)，其中n为s长度。
    // 空间复杂度：O(n)，其中n为s长度。分析：最坏的情况s全是左括号，栈需要将所有括号全部遍历入栈后才能返回false
    public boolean isValid(String s) {
        Deque<Character> stk = new LinkedList<>();
        for (char c : s.toCharArray()) {
            // 左括号就入栈
            if (c == '(' || c == '[' || c == '{') {
                stk.push(c);
            } else if (!stk.isEmpty()) {    // 当前为右括号且栈不为空，则与栈顶括号匹配
                char top = stk.peek();  // 查看栈顶元素但不移除
                if ((top == '(' && c == ')') || (top == '[' && c == ']') || (top == '{' && c == '}')) {
                    // 匹配得上则将栈顶的左括号出栈
                    stk.pop();
                } else {
                    // 没匹配上则返回false
                    return false;
                }
            } else {
                return false;
            }
        }
//        if (stk.isEmpty()) {    // 最后栈空则括号全部匹配正确
//            return true;
//        } else {
//            return false;
//        }
        return stk.isEmpty();
    }
}
