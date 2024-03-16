package sortedArrayToBST;

import java.util.LinkedList;
import java.util.Queue;

/**
 * No.108 将有序数组转换为二叉搜索树
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵平衡二叉搜索树。
 *
 * 二叉搜索树的中序遍历是升序序列。
 * 我们可以选择中间数字作为二叉搜索树的根节点，这样分给左右子树的数字个数相同或只相差 1，可以使得树保持平衡。
 * 确定平衡二叉搜索树的根节点之后，其余的数字分别位于平衡二叉搜索树的左子树和右子树中，左子树和右子树分别也是平衡二叉搜索树，因此可以通过递归的方式创建平衡二叉搜索树。
 */

public class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return createBST(nums, 0, nums.length - 1);
    }

    /**
     * 将nums的下标left~right的有序数生成一棵平衡二叉搜索树
     * 作为mid的左右子树
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public TreeNode createBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 选择nums的最中间数字作为根节点，如果是奇数个则根节点为正中间的，如果是偶数个则根节点为中间偏左那个
        int mid = (right + left) / 2;

        // 左右子树也是平衡二叉搜索树
        TreeNode lt = createBST(nums, left, mid - 1);
        TreeNode rt = createBST(nums, mid + 1, right);

        return new TreeNode(nums[mid], lt, rt);
    }
}
