package wordBreak;

import java.util.Arrays;
import java.util.List;

/**
 * No.139 单词拆分
 *
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 */

public class Solution {


    /**
     * 记忆化回溯
     * （提交的耗时居然比一维dp还快！）
     * @param s
     * @param wordDict
     * @return
     */
    // // 时间复杂度：O(ln)，其中l为s的长度，n为wordDict的单词数。分析：共l种状态每种状态计算一次，每次都最多需要遍历wordDict中的所有单词
    // // 空间复杂度：O(l)，其中l为s的长度
    // int[] memo; // 记忆数组，初始为0代表未计算过，1代表可以拼成，-1代表不能拼成
    // public boolean wordBreak(String s, List<String> wordDict) {
    //     int len = s.length();
    //     memo = new int[len + 1];    // s前i个字符对应的子串能否拼成记录在memo[i]，初始全为0
    //     memo[0] = 1; // 前0个字符即空串一定能拼成
    //
    //     return backtrack(s, len, wordDict) == 1 ? true : false;
    // }
    //
    // /**
    //  * 本轮要拼的字符串为s的前len个字符，计算并返回是否能拼成的结果
    //  * @param s
    //  * @param len
    //  * @param wordDict
    //  * @return
    //  */
    // public int backtrack(String s, int len, List<String> wordDict) {
    //     if (len < 0) {  // 搜到最前面还没拼成，那么这条路径无法拼成，返回false
    //         return -1;
    //     }
    //
    //     if (len == 0) { // 在上一层递归刚好拼成，这层已经不需要拼了，返回true
    //         return 1;
    //     }
    //
    //     if (memo[len] != 0) {   // 如果之前已经计算过则直接返回记忆数组中保存的结果
    //         return memo[len];
    //     }
    //
    //     String subStr = s.substring(0, len);
    //     int canBreak = -1;
    //     for (String word : wordDict) {
    //         if (subStr.endsWith(word)) {
    //             int res = backtrack(s, len - word.length(), wordDict);
    //             if (res == 1) { // 找到一种方法能拼成就不用再找了
    //                 canBreak = 1;
    //                 break;
    //             }
    //         }
    //     }
    //
    //     memo[len] = canBreak;   // 保存结果（能或不能拼成都保存）
    //     return canBreak;
    // }



    /**
     * 完全背包 + 一维滚动数组
     * 单词就是物品，字符串s就是背包，单词能否组成字符串s，就是看物品能不能把背包装满。
     * 这里改为了求物品装满背包所需的最少数量，如果最后dp[len]是一个小于max的有效值，说明它是存在一个有效的装满背包的最少数量，也就说明能装满背包的
     * 需要注意：在遍历每一个word时，不仅要 j >= len(word)，还要word能够匹配上subStr的后部才会考虑拼该word在后面是否有更少单词数的情况
     * 为什么没有判断j >= len(word)呢？——因为word比子串还长的情况一定匹配不上，利用endWith方法可以一起筛选了
     * @param s
     * @param wordDict
     * @return
     */
    // // 时间复杂度：O(ln)，其中l为s的长度，n为wordDict的单词数
    // // 空间复杂度：O(l)，其中l为s的长度
    // public boolean wordBreak(String s, List<String> wordDict) {
    //     int len = s.length();
    //     int max = len + 1;  // 由于wordDict中的字符串非空，因此最多需要len个字符串拼成s，max设为比len大1即可。不用MAX_VALUE是为了后面求dp[j]加1不会溢出
    //     // dp[j]：挑选出单词用于拼接出s的前j个字符对应的子串所需要的最少单词数，如 s = "leetcode"，j = 4代表的子串为"leet"，即s的前4个字符（与下标区分）
    //     int[] dp = new int[len + 1];
    //
    //     // 递推公式：dp[j] = min{dp[j], dp[j - len(wordDict[i])] + 1}，当然前提还得wordDict[i]能够匹配上
    //
    //     // 初始化
    //     Arrays.fill(dp, max);   // 若 dp[j] >= max 代表无法拼成s的前j个字符对应的子串，是无效值
    //     dp[0] = 0;  // 拼成空串最少需要0个单词
    //
    //     // 遍历顺序
    //     // 这里先背包再物品
    //     for (int j = 1; j <= len; j++) {
    //         String subStr = s.substring(0, j);  // 要拼成的子串
    //         for (String word : wordDict) {  // 遍历字典中的每一个单词，尝试拼到最后
    //             if (subStr.endsWith(word)) {    // 如果该单词能够成功匹配到subStr的后面部分，则要考虑
    //                 dp[j] = Math.min(dp[j], dp[j - word.length()] + 1);
    //             }
    //         }
    //     }
    //
    //     return dp[len] < max;   // 最终dp[len]如果是有效值（不再是初始值max）则代表可以拼成
    // }




    /**
     * 完全背包 + 一维滚动数组
     * 单词就是物品，字符串s就是背包，单词能否组成字符串s，就是看物品能不能把背包装满。
     * 所以dp数组中记录的直接是能否装满的布尔值，好处就是只要确定存在一种方案能拼成就可以break计算下一个dp[j]
     * 需要注意：本题必须先遍历背包，再遍历物品，因为本题其实求的是排列。组合不强调元素之间的顺序，排列强调元素之间的顺序。
     * 排列认为顺序不同就是不同的方案，如"apple" + "pen" + "apple" 和 "pen" + "apple" + "apple"是不同的
     * 组合认为顺序不同不影响，只要每种物品的个数对应相同就是一样的。
     *
     * 在实现上，组合保证了在选物品的时候按照物品在数组中的相对顺序进行选取，这样选出的结果就能保证一种组合只算一个。如goods={a,b}，只会选出ab,aab,abb,abbb这种
     * 排列由于先遍历了背包，对每个背包容量都会考虑一遍所有物品放入的可能性，因此就可以选出ab,ba这种，就算两种不同的方案
     * @param s
     * @param wordDict
     * @return
     */
    // 时间复杂度：O(ln)，其中l为s的长度，n为wordDict的单词数
    // 空间复杂度：O(l)，其中l为s的长度
    public boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        // dp[j]：挑选出单词是否能拼成s的前j个字符对应的子串（true/false）
        boolean[] dp = new boolean[len + 1];

        // 递推公式：dp[j] = dp[j] || dp[j - len(wordDict[i])]，当然前提还得wordDict[i]能够匹配上

        // 初始化
        // dp[j]默认是false
        dp[0] = true;  // 空串一定能拼成

        // 遍历顺序
        // 必须先遍历背包，再遍历物品
        // 如果先遍历物品，再遍历背包，则拼接一定只能按照单词在字典中的相对顺序进行拼接，这显然是不符合本题题意的。如dict={a,b}则只能出a,aa,aab,ab,abbb这样的，b永远无法拼到a的前面。
        for (int j = 1; j <= len; j++) {
            String subStr = s.substring(0, j);  // 要拼成的子串
            for (String word : wordDict) {  // 遍历字典中的每一个单词，尝试拼到最后
                // 如果该单词能够成功匹配到subStr的后面部分，且前半部分也能拼成，则subStr就是可以拼成的（至少以这个单词结尾的方案是可以拼成的）
                if (subStr.endsWith(word) && dp[j - word.length()]) {
                    dp[j] = true;   // 只要找到一种方案证明subStr可以拼成就可以将对应的dp[j]设为true
                    break;  // 后面的word就不用看了，因为已经证明能拼成了
                }
            }
        }

        return dp[len];
    }
}
