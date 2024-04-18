package rob_337;

import java.util.HashMap;

/**
 * No.337 打家劫舍Ⅲ
 *
 * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
 * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
 * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
 */

public class Solution {


    /**
     * 记忆化递归
     * 如果计算过孙子了，那么计算孩子的时候可以复用孙子节点的结果，减少重复计算
     *
     * 每个节点可选择偷或者不偷两种状态，根据题目意思，相连节点不能一起偷：
     * 当前节点选择偷时，那么两个孩子节点就不能选择偷了，只能从孙子开始偷起（孙子偷不偷没关系）
     * 当前节点选择不偷时，两个孩子节点只需要拿最多的钱出来就行(两个孩子节点偷不偷没关系)
     *
     * @param root
     * @return
     */
    // // 时间复杂度：O(n)，其中n为节点个数
    // // 空间复杂度：O(n)，其中n为节点个数。分析：记忆辅助哈希表空间复杂度为O(n)，递归栈的空间复杂度为O(h)，其中h为树的高度，不超过n。
    // HashMap<TreeNode, Integer> memo;    // 用来记忆已经计算过的子树区域的最高金额
    // public int rob(TreeNode root) {
    //     memo = new HashMap<>();
    //     return robField(root);
    // }
    //
    // /**
    //  * 抢劫root为根的子树地区得到的最高金额
    //  * @param root
    //  * @return
    //  */
    // public int robField(TreeNode root) {
    //     if (root == null) {
    //         return 0;
    //     }
    //
    //     // 该子树已经计算过了，直接返回哈希表中保存的value
    //     if (memo.containsKey(root)) {
    //         return memo.get(root);
    //     }
    //
    //     // 如果没计算过，开始计算，分两种情况
    //     // 1. 包含root的该子树地区的最高金额（如果抢了root，则左右孩子不能抢了，只能从孙子开始抢）
    //     int resWithRoot = root.val; // 偷root
    //     // 计算了偷root的四个孙子（左右孩子的孩子）为头结点的子树的情况
    //     if (root.left != null) {
    //         resWithRoot += (robField(root.left.left) + robField(root.left.right));
    //     }
    //     if (root.right != null) {
    //         resWithRoot += (robField(root.right.left) + robField(root.right.right));
    //     }
    //
    //     // 2. 不包含root的该子树地区的最高金额（由于不抢root，所以左右子树已经靠root分开了）
    //     int resWithoutRoot = robField(root.left) + robField(root.right);
    //
    //     memo.put(root, Math.max(resWithoutRoot, resWithRoot));  // 记录结果
    //     return memo.get(root);
    // }



    /**
     * 树形dp，在树上进行递归公式的推导，需要熟悉树的遍历方式。
     * 一个子树能偷的最大金额取决于根节点偷或不偷两种情况
     * 而动态规划其实就是使用状态转移容器来记录状态的变化，这里可以使用一个长度为2的数组，记录当前节点偷与不偷所得到的最大金钱。
     *
     * 一个树能偷的最大金额取决于根节点偷或不偷两种情况，而根节点偷或不偷实际得到的最大金钱又取决于两个子树偷的最大金钱，因此应该从下往上、从叶往根计算每个节点为根的子树偷的最大金额
     * 满足这个条件的遍历是二叉树的后序遍历
     *
     * @param root
     * @return
     */
    // 时间复杂度：O(n)，其中n为节点个数。分析：每个节点都被遍历一次
    // 空间复杂度：O(h)，其中h为树高。分析：dp数组长度为2，空间复杂度为O(1)，递归栈的空间复杂度为O(h)
    // 空间复杂度：
    public int rob(TreeNode root) {
        int[] res = robTree(root);
        return Math.max(res[0], res[1]);
    }

    /**
     * 计算当前节点偷和不偷两种情况下当前树偷的最大金额并返回
     * dp数组其实就是记录当前节点偷与不偷所得到的最大金钱情况的长度为2的数组，下标为0代表当前树不偷cur的最大金额，下标为1代表当前树要偷cur的最大金额
     * @param cur
     * @return 下标为0代表当前树不偷cur的最大金额，下标为1代表当前树要偷cur的最大金额
     */
    public int[] robTree(TreeNode cur) {
        int[] res = new int[2];
        if (cur == null) {  // 终止条件：当前节点为null，返回[0,0]
            return res;
        }

        // 后序遍历
        int[] leftRes = robTree(cur.left);  // 递归左节点，得到左节点偷与不偷的金钱。
        int[] rightRes = robTree(cur.right);  // 递归右节点，得到右节点偷与不偷的金钱。

        // 1. 不偷cur节点，则左右孩子都可偷，至于到底偷不偷一定是选一个最大的作为一边的代表
        res[0] = Math.max(leftRes[0], leftRes[1]) + Math.max(rightRes[0], rightRes[1]);

        // 2. 要偷cur节点，则左右孩子都不能偷
        res[1] = cur.val + leftRes[0] + rightRes[0];

        return res;
    }
}
