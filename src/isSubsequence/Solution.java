package isSubsequence;

/**
 * No.392 判断子序列
 *
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 *
 * 进阶：
 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 */

public class Solution {

    /**
     * 从前往后边遍历t边与s的每个字符匹配，如果最后s的字符能按顺序被全部匹配上，则s是t的子序列
     *
     * 但如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，每检查一个s就得
     * 遍历一遍t，故这种写法并不适合大量输入的场景。
     *
     * @param s
     * @param t
     * @return
     */
    // // 时间复杂度：O(n)，其中n是t的长度。分析：最长的情况下s不是t的子序列需要将t遍历完
    // // 空间复杂度：O(1)
    // public boolean isSubsequence(String s, String t) {
    //     int cur = 0;    // 指向s当前正在匹配到的位置
    //
    //     // 遍历t，并与s的每一个字符匹配
    //     for (char c : t.toCharArray()) {
    //         if (cur < s.length() && c == s.charAt(cur)) {
    //             cur++;
    //         }
    //     }
    //
    //     // 如果s匹配上完了证明是子序列
    //     return cur == s.length();
    // }








    /**
     * 动态规划
     * 建立好dp数组后，对于每个输入s，只需要从后往前遍历s的每个字符，在dp数组中查找它在t中的位置即可，最多将s遍历完
     * 如果s不是t的子序列，则不用遍历完s就退出循环了
     * s的长度大多数不超过t（因为如果s的长度大于t的长度则直接就得出s不是t的子序列），因此将所有s遍历一遍比每次都遍历一遍t更节省时间
     * 对于大量输入的情况可能性能更好？
     *
     * @param s
     * @param t
     * @return
     */
    // 时间复杂度：O(n)，其中n是t的长度。分析：建立dp数组耗时O(n)，遍历s耗时小于遍历t因此最多也为O(n)
    // 空间复杂度：O(n)，其中n是t的长度
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) {  // 如果s为空，t无论是否为空都满足s是t的子序列
            return true;
        }
        if (s.length() > t.length()) {  // 如果s不为空，且s的长度大于t的长度，则s一定不是t的子序列
            return false;
        }

        char[] c = new char[26];    // 存放26个小写字母
        for (int i = 0; i < 26; i++) {
            c[i] = (char) ('a' + i);
        }

        int sl = s.length(), tl = t.length();

        // dp[i][j]：t的前i个字符组成的子串中字符c[j]最晚在第dp[i][j]个，如果c[j]没出现为默认值0
        int[][] dp = new int[tl + 1][26];

        // 递推公式
        // 如果t[i-1] == c[j]，则dp[i][j] = i
        // 如果t[i-1] != c[j]，则dp[i][j] = dp[i-1][j]

        // 初始化
        // 空串当然没有字母出现，因此dp[0][j]均初始化为0

        // 遍历顺序：先遍历t，再遍历小写字母表
        for (int i = 1; i <= tl; i++) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i-1) == c[j]) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }


        // 利用建立好的dp数组检查s是否为t的子序列
        // 从后往前检查
        int len = t.length();  // 在t的前len个字符的子串中找当前字符
        for (int k = sl - 1; k >= 0; k--) {
            int cur = s.charAt(k) - 'a';    // 当前检查的字符在字母数组中对应的下标

            // 如果t的前len个字符中不存在当前字符，说明s不是t的子序列
            if (dp[len][cur] == 0) {
                return false;
            }

            // 如果t的前len个字符中存在当前字符，且最晚出现在第dp[len][cur]个，则当前字符匹配上，下一个字符应该到t的前dp[len][cur]个中匹配
            len = dp[len][cur] - 1;
        }

        return true;
    }
}
