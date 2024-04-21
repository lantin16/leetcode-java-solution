package findLength;

/**
 * No.718 最长重复子数组
 *
 * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
 */

public class Solution {

    /**
     * 动态规划 + 二维dp
     * 如果将dp[i][j]定义为以nums1中以nums1[i]结尾和nums2中以nums2[j]结尾的最长公共子数组的长度
     * 优点：dp数组的定义更直观
     * 缺点：需要单独初始化第一行和第一列以免下面计算dp[i][j]时访问dp[i-1][j-1]发生越界
     * @param nums1
     * @param nums2
     * @return
     */
    // // 时间复杂度：O(mn)，其中m,n为分别为数组nums1和nums2的长度
    // // 空间复杂度：O(mn)
    // public int findLength(int[] nums1, int[] nums2) {
    //     int res = 0;
    //     int len1 = nums1.length;
    //     int len2 = nums2.length;
    //
    //     // dp[i][j]：以nums1中以nums1[i]结尾和nums2中以nums2[j]结尾的最长公共子数组的长度
    //     int[][] dp = new int[len1][len2];
    //
    //     // 递推公式
    //     // 如果nums1[i] = nums2[j]，则dp[i][j] = dp[i-1][j-1] + 1
    //     // 如果nums1[i] != nums2[j]，则dp[i][j] = 0
    //
    //     // 初始化
    //     // 由于dp[i][j]依赖于dp[i-1][j-1]，故需要初始化第一行和第一列
    //     for (int j = 0; j < len2; j++) {    // 第一行
    //         if (nums1[0] == nums2[j]) {
    //             dp[0][j] = 1;
    //             res = 1;    // 这里如果找到和第一个元素相同的，res要跟着更新，否则在下面的for循环中是收集不到这种情况的res的
    //         }
    //     }
    //     for (int i = 0; i < len1; i++) {    // 第一列
    //         if (nums1[i] == nums2[0]) {
    //             dp[i][0] = 1;
    //             res = 1;
    //         }
    //     }
    //
    //     for (int i = 1; i < len1; i++) {
    //         for (int j = 1; j < len2; j++) {
    //             if (nums1[i] == nums2[j]) {
    //                 dp[i][j] = dp[i-1][j-1] + 1;
    //                 res = Math.max(res, dp[i][j]);
    //             }
    //         }
    //     }
    //     return res;
    // }




    /**
     * 动态规划 + 二维dp
     * 改为将dp[i][j]定义为以nums1中第i个元素（即nums1[i-1]）结尾和以nums2中第j个元素（即nums2[j-1]）结尾的最长公共子数组的长度
     * 优点：不用单独初始化第一行和第一列，可以统一写法，代码更简洁
     * 缺点：dp[0][j]和dp[i][0]无实际意义，单纯只是为了统一写法下标不越界
     * @param nums1
     * @param nums2
     * @return
     */
    // // 时间复杂度：O(mn)，其中m,n为分别为数组nums1和nums2的长度
    // // 空间复杂度：O(mn)
    // public int findLength(int[] nums1, int[] nums2) {
    //     int res = 0;
    //     int len1 = nums1.length;
    //     int len2 = nums2.length;
    //
    //     // dp[i][j]：以nums1中第i个元素（即nums1[i-1]）结尾和以nums2中第j个元素（即nums2[j-1]）结尾的最长公共子数组的长度
    //     int[][] dp = new int[len1 + 1][len2 + 1];
    //
    //     // 递推公式
    //     // 如果nums1[i] = nums2[j]，则dp[i][j] = dp[i-1][j-1] + 1
    //     // 如果nums1[i] != nums2[j]，则dp[i][j] = 0
    //
    //     // 初始化
    //     // dp[i][0]和dp[0][j]其实是无实际意义的，为了使递推正确应该初始化为0
    //     // 举例：如果nums1[0]和nums2[k]相同，则对应dp数组的dp[1][k+1]，此时dp[1][k+1]应该为1，那么根据递推公式dp[1][k+1] = dp[0][k] + 1，dp[0][k]就应该为0
    //
    //     // 遍历顺序
    //     for (int i = 1; i <= len1; i++) {
    //         for (int j = 1; j <= len2; j++) {
    //             if (nums1[i-1] == nums2[j-1]) { // 第i个元素和第j个元素的下标分别是i-1和j-1
    //                 dp[i][j] = dp[i-1][j-1] + 1;
    //                 res = Math.max(res, dp[i][j]);
    //             }
    //             //若第i个元素和第j个元素不相等保持默认值0即可，代表这种情况没有公共子数组
    //         }
    //     }
    //     return res;
    // }




    /**
     * 动态规划 + 一维滚动数组
     * 由于每个dp[i][j]只依赖于上一行其左上角的dp[i-1][j-1]且只用一次，因此可以压缩为一维dp数组。相当于可以把上一层dp[i - 1][j]拷贝到下一层dp[i][j]来继续用。
     * 由于每次计算dp[j]实际上是用的上一行的dp[j-1]，因此在计算dp[j]时要避免前面的dp[j-1]被这一行的结果覆盖，所以内循环j要倒序遍历
     * 优化空间复杂度
     * @param nums1
     * @param nums2
     * @return
     */
    // 时间复杂度：O(mn)，其中m,n为分别为数组nums1和nums2的长度
    // 空间复杂度：O(n)
    public int findLength(int[] nums1, int[] nums2) {
        int res = 0;
        int len2 = nums2.length;

        // dp[i][j]：以nums1中第i个元素（即nums1[i-1]）结尾和以nums2中第j个元素（即nums2[j-1]）结尾的最长公共子数组的长度
        int[] dp = new int[len2 + 1];

        // 递推公式
        // 如果nums1[i] = nums2[j]，则dp[j] = dp[j-1] + 1
        // 如果nums1[i] != nums2[j]，则dp[i][j] = 0

        // 初始化
        // dp[i][0]和dp[0][j]其实是无实际意义的，为了使递推正确应该初始化为0
        // 举例：如果nums1[0]和nums2[k]相同，则对应dp数组的dp[1][k+1]，此时dp[1][k+1]应该为1，那么根据递推公式dp[1][k+1] = dp[0][k] + 1，dp[0][k]就应该为0

        // 遍历顺序
        for (int endNum1 : nums1) {
            // 由于每次计算dp[j]实际上是用的上一行的dp[j-1]，因此在计算dp[j]时要避免前面的dp[j-1]被这一行的结果覆盖，所以内循环j要倒序遍历
            for (int j = len2; j >= 1; j--) {
                if (endNum1 == nums2[j-1]) {
                    dp[j] = dp[j-1] + 1;
                    res = Math.max(res, dp[j]);
                } else {
                    // 之前的写法是因为每个dp[i][j]是独立的，不会覆盖的，因此末尾元素不相等的时候保持初始值0即可。
                    // 但这里由于dp[j]可能被前面几行的非0结果覆盖，所以不相等的时候要重新赋0，否则计算下一行的dp[j]时会出现错误
                    dp[j] = 0;  // 注意这里不相等的时候要有赋0的操作
                }
            }
        }

        return res;
    }
}
