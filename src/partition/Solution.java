package partition;

import java.util.ArrayList;
import java.util.List;

/**
 * No.131 分割回文串
 *
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。返回 s 所有可能的分割方案。
 */

public class Solution {
    // 回溯法
    // 时间复杂度：O(n × 2^n)，其中n为s的长度。对于每个字母s[i]，都有两种选择：选或不选则在它这结束一个可能的回文串，递归次数为一个满二叉树的节点个数，
    // 那么一共会递归 O(2^n)次（等比数列和），再算上判断回文和加入答案时需要O(n)的时间，所以时间复杂度为 O(n × 2^n)。
    // 空间复杂度：O(n)，其中n为s的长度。分析：存放临时结果的集合tmp最大size为n，递归深度最大也为n，当每个子串都为一个字母时。
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        backtrack(res, new ArrayList<>(), s, 0);
        return res;
    }

    public List<List<String>> backtrack(List<List<String>> res, List<String> tmp, String s, int start) {
        if (start == s.length()) {    // 检查到s尾部
            res.add(new ArrayList<>(tmp));  // 添加tmp的拷贝而不是tmp本身，因为tmp在最后会由于回溯的撤销操作而清空
            return res;
        }

        // 检查start到end是否是回文串
        for (int end = start; end < s.length(); end++) {
            String cur = s.substring(start, end + 1);   // 当前正在检查的子串，start~end（包括end）
            if (isPalindrome(cur)) {
                tmp.add(cur);
                backtrack(res, tmp, s, end + 1);    // 下一个子串从end+1开始检查
                tmp.remove(tmp.size()-1);   // 撤销操作
            }
        }

        return res;
    }

    /**
     * 判断字符串是否为回文串
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        for (int i = 0, j = s.length()-1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
