package generateParenthesis;

import java.util.ArrayList;
import java.util.List;

/**
 * No.22 括号生成
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 */

public class Solution {

    // 回溯法
    // 时间复杂度：略
    // 空间复杂度：O(n)。分析：我们所需要的空间取决于递归栈的深度，每一层递归函数需要 O(1) 的空间，最多递归 2n 层，因此空间复杂度为 O(n)。
    List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        dfs(0, 0, new StringBuilder(), n);
        return res;
    }

    public void dfs(int leftNum, int rightNum, StringBuilder sb, int n) {
        // if1
        if (leftNum == n && rightNum == n) {    // 如果左右括号都用完了
            res.add(sb.toString());
            return;
        }
        // if2 这两个if其实只要写一个就行，都可以作为递归中止条件，if2包括了if1的情况，if2主要是为了加快递归的结束速度
        if (leftNum == n && rightNum < n) { // 如果左括号用完了，剩下的全加右括号（就在这一层完成，免得到下面花好几层递归，每层只加一个右括号）
            int preSize = sb.length();
            int delta = n - rightNum;   // 需要追加的右括号数量
            for (int i = 0; i < delta; i++) {
                sb.append(")");
            }
            res.add(sb.toString());
            sb.delete(preSize, sb.length());    // 撤销操作
            return;
        }

        // 每个位置有两种选择：右括号或左括号
        if (leftNum < n) {  // 如果有左括号先尝试加左括号
            sb.append("(");
            dfs(leftNum + 1, rightNum, sb, n);
            sb.deleteCharAt(sb.length()-1); // 撤销操作
        }

        if (rightNum < leftNum) {   // 如果已经加的右括号比左括号少才能加右括号
            sb.append(")");
            dfs(leftNum, rightNum + 1, sb, n);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
