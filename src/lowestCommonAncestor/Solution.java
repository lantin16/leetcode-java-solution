package lowestCommonAncestor;

/**
 * No.236 二叉树的最近公共祖先
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 */

public class Solution {

    /**
     * 设root为p，q的最近公共祖先，只有以下三种情况：
     * 1. p，q位于root的异侧（分别位于root左右子树中）
     * 2. root = p， q位于root即p的左子树或右子树
     * 3. root = q， p位于root即q的左子树或右子树
     *
     * 说明：
     * 第1种情况，左右两边向下递归分别遇到p，q就会向上不断抛p，q，直到root处检测到左右返回结果都不为null，接着就向上抛root即可
     * 第2、3种情况，遇到p或q中的一个就直接向上抛p或q，由于另一个在其子树中，因此第一次遇到的p或q就是最近公共祖先，其他分支不可能再找到另一个q或p，因此也就不会影响p或q向上抛到root最后返回
     */

    // 深度优先搜素，如果p、q都找到了，则返回最近公共祖先；如果p、q只找到一个，则返回p、q；如果p、q都没找到，则返回null
    // 时间复杂度：O(n)，其中n为二叉树结点数
    // 空间复杂度：O(n)，最差情况下，递归深度达到n，二叉树退化为链状
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果找到了p或q，则直接返回p或q，因为最近公共祖先不可能在比p或q更深的位置
        if (root == null || root == p || root == q) {
            return root;
        }

        // 如果当前结点不是p或q则继续向左右子树搜索
        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        // 如果左右子树中也没有找到p或q，则返回null，代表从当前结点往下的这一分支不可能存在p或q的最近公共祖先
        if (leftRes == null && rightRes == null) {
            return null;
        }

        // 如果左子树没有找到结果而右子树找到了，则直接返回右子树的搜索结果（这个结果可能是p，可能是q，也可能是在下面已经找到的最近公共祖先）
        if (leftRes == null) {
            return rightRes;
        }

        // 如果右子树没有找到结果而左子树找到了，则直接返回左子树的搜索结果
        if (rightRes == null) {
            return leftRes;
        }

        // 如果左子树和右子树都找到了成果，则一定是p，q分处左右两边的情况，则当前结点就是最近公共祖先，返回即可
        return root;
    }




    // 偏暴力的做法：利用dfs找出root到p或q的路径并存储下来，然后比较路径中最后一个重叠结点即可。
}
