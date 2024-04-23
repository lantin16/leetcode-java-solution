package countSubstrings;

/**
 * No.647 回文子串
 * <p>
 * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
 * 回文字符串 是正着读和倒过来读一样的字符串。
 * 子字符串 是字符串中的由连续字符组成的一个序列。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * @param s
     * @return
     */
//    // 时间复杂度：O(n^2)，其中n为s的长度
//    // 空间复杂度：O(n^2)，其中n为s的长度
//    public int countSubstrings(String s) {
//        char[] charArr = s.toCharArray();
//        int len = s.length();
//
//        // dp[i][j]：s下标i到j的子串是否为回文串，是则dp[i][j]=true
//        boolean[][] dp = new boolean[len][len];
//
//        // 递推公式
//        // 如果s[i] == s[j]且 j - i <= 1（即如单个字符"a"和两个字符"aa"），则dp[i][j] = true
//        // 如果s[i] == s[j]且 j - i > 1（即超过两个字符需要取决于内层子串是否是回文串），则dp[i][j] = dp[i+1][j-1]
//        // 如果s[i] != s[j]（即首尾字符不相等一定不是回文串），则dp[i][j] = false
//
//        // 初始化，全部初始化为false即可
//
//        int count = 0;  // 记录dp数组中true的个数，就是s中回文串的个数
//        // 遍历顺序：由于dp[i][j]依赖于dp[i+1][j-1]，即左下角的元素，因此在计算dp[i][j]时要保证dp[i+1][j-1]已经被计算
//        // 所以i需要倒序遍历，j需要正序遍历
//        // 并且i是开始下标，j是结束下标，所以i<=j，j从i开始向右遍历
//        // dp数组的左下三角部分（i>j的部分）都是非法部分，应该保持默认值false，后续遍历也不会计算那些位置的dp值
//        for (int i = len - 1; i >= 0; i--) {    // i倒序遍历，从下至上
//            for (int j = i; j < len; j++) {     // j正序遍历，从左至右
//                if (charArr[i] == charArr[j]) {
//                    if (j - i <= 1){    // 当前子串长度为1或2时
//                        dp[i][j] = true;
//                    } else {
//                        dp[i][j] = dp[i+1][j-1];
//                    }
//
//                    if (dp[i][j]) {
//                        count++;
//                    }
//                }
//                // 如果首尾字符不相等，则一定不是回文串，保持默认false即可
//            }
//        }
//
//        return count;
//    }


    /**
     * 双指针法
     * 空间复杂度相较于二维dp更低
     *
     * 确定回文串，就是找中心然后向两边扩散看是不是对称的就可以了。
     * 中心可能有两种基本情况：单字符（如"a"）或者相同的双字符（如"aa"）。其他更长的中心都可以向中间缩小到这两种基本情况
     *
     * @param s
     * @return
     */
    // 时间复杂度：O(n^2)，其中n为s的长度
    // 空间复杂度：O(1)
    public int countSubstrings(String s) {
        char[] charArr = s.toCharArray();
        int count = 0;

        // 遍历所有可能的中心（单字符和双字符的情况，至于双字符是否可以作为中心就交给spread方法内部去判断）
        for (int i = 0; i < s.length(); i++) {
            count += (spread(charArr, i, i) + spread(charArr, i, i + 1));
        }

        return count;
    }

    /**
     * 返回s[l:r]向两端扩散到头的回文串个数
     * @param charArr
     * @param l
     * @param r
     * @return
     */
    public int spread(char[] charArr, int l, int r) {
        int count = 0;
        // 如果l和r没越界且两端字符相同则当前s[l:r]是回文串
        // 因为是从中心向两端扩散，只有中间的子串是回文串才会判断扩散后的子串是否是回文串，因此只要两端相等就知道扩散后的是回文串，而不用再检查中间的是否是回文串
        while (l >= 0 && r < charArr.length && charArr[l] == charArr[r]) {
            count++;
            // 向两边扩散
            l--;
            r++;
        }
        return count;
    }
}
