package invertTree;

/**
 * No.226. 翻转二叉树
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 */

public class Solution {

    // 递归
    // 时间复杂度：O(n)，其中n为二叉树节点的数目
    // 空间复杂度：由递归栈的深度决定，它等于当前节点在二叉树中的高度。在平均情况下，二叉树的高度与节点个数为对数关系，即 O(log⁡N)。
    // 而在最坏情况下，树形成链状，空间复杂度为 O(N)。
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 翻转左右子树
        TreeNode invertLeft = invertTree(root.left);
        TreeNode invertRight = invertTree(root.right);

        // 交换root的左右子树
        root.left = invertRight;
        root.right = invertLeft;

        return root;
    }
}
