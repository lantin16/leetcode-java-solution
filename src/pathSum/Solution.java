package pathSum;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * No.437 路径总和 III
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 */


public class Solution {

    // // 深度优先搜索 + 递归
    // // 递归遍历每一个节点，对于每一个节点，求以该节点为起点的满足条件的路径数目，然后将这些路径的数目加起来即为返回结果
    // // 时间复杂度：O(n^2)，其中 n 为该二叉树节点的个数。分析：对于每一个节点，求以该节点为起点的路径数目时，则需要遍历以该节点为根节点的子树的所有节点，
    // // 因此求该路径所花费的最大时间为 O(n)，我们会对每个节点都求一次以该节点为起点的路径数目，因此时间复杂度为 O(n^2)。
    // //
    // public int pathSum(TreeNode root, int targetSum) {
    //     if (root == null) {
    //         return 0;
    //     }
    //
    //     int res = rootSum(root, (long) targetSum); // 以root为起点的路径条数
    //     res += pathSum(root.left, targetSum);   // 不需要root
    //     res += pathSum(root.right, targetSum);  // 不需要root
    //     return res;
    // }
    //
    // /**
    //  * 递归统计以node为起始结点的符合路径总和为tar的路径条数
    //  * @param node
    //  * @param tar
    //  * @return
    //  */
    // public int rootSum(TreeNode node, long tar) {
    //     if (node == null) {
    //         return 0;
    //     }
    //
    //     int sum = 0;
    //     if (node.val == tar) {  // 如果node自身就为tar
    //         sum++;
    //     }
    //
    //     // 统计以左右孩子为起点，路经总和为(tar - node.val)的路径条数
    //     return sum + rootSum(node.left, tar - node.val) + rootSum(node.right, tar - node.val);  // 当val过大，tar一直做减法可能越界，因此tar用long类型
    // }



    // 前缀和
    // 定义节点的前缀和为：由根结点到当前结点的路径上所有节点的和
    // 时间复杂度：O(n)，利用前缀和只需要遍历一次二叉树即可
    // 空间复杂度：O(n)
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefix = new HashMap<>();    // 存储从根节点到当前节点的前一个结点路径上所有结点的前缀和，key：前缀和，value：路径上前缀和为key值的结点个数
        prefix.put(0L, 1); // 空结点的前缀和为0
        return dfs(root, prefix, targetSum, 0L);
    }


    /**
     * 深度优先搜索
     * @param node
     * @param prefix
     * @param targetSum
     * @param parentPreSum node的父节点的前缀和，用于求node的前缀和
     * @return
     */
    public int dfs(TreeNode node, Map<Long, Integer> prefix, int targetSum, long parentPreSum) {
        if (node == null) {
            return 0;
        }

        int ret = 0;

        // 当前节点的前缀和
        long curPreSum = parentPreSum + node.val;
        // 检查从根节点到当前结点的前一个结点的路径上是否存在 某一个结点的前缀和 = 当前结点的前缀和 - targetSum
        ret += prefix.getOrDefault(curPreSum - targetSum, 0);   // 这一步其实是找的从根节点到当前节点的路径上以当前结点为结束结点的路径条数
        // 将当前结点的前缀和加入哈希表
        prefix.put(curPreSum, prefix.getOrDefault(curPreSum, 0) + 1);   // 如果哈希表中已经有了该前缀和值则+1
        // 向左右子树深入，检查以左右子树中的某节点为结束节点的路径条数
        ret += dfs(node.left, prefix, targetSum, curPreSum);
        ret += dfs(node.right, prefix, targetSum, curPreSum);
        // 退出当前节点时，记得将它的前缀和在prefix中次数减一
        prefix.put(curPreSum, prefix.get(curPreSum) - 1);

        return ret;
    }
}
