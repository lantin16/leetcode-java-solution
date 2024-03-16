package isValidBST;

import mergeKLists.ListNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * No.98 验证二叉搜索树
 *
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 *
 * 有效 二叉搜索树定义如下：
 * - 节点的左子树只包含小于当前节点的数。
 * - 节点的右子树只包含大于当前节点的数。
 * - 所有左子树和右子树自身必须也是二叉搜索树。
 */

public class Solution {

    // // 二叉搜索数的中序遍历为升序序列，因此可以依据其中序序列是否为升序来判断是否为有效二叉搜索树
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(n)
    // public boolean isValidBST(TreeNode root) {
    //     List<Integer> list = new ArrayList<>();
    //     inOrder(root, list);
    //
    //     // 检查list中的val是否是升序的
    //     int last = list.get(0);
    //     for (int i = 1; i < list.size(); i++) {
    //         if (list.get(i) <= last) {
    //             return false;
    //         }
    //         last = list.get(i);
    //     }
    //
    //     return true;
    // }
    //
    // /**
    //  * 对root为根的树进行中序遍历，并将遍历节点的val存入list
    //  * @param root
    //  * @param list
    //  */
    // public void inOrder(TreeNode root, List<Integer> list) {
    //     if (root == null) {
    //         return;
    //     }
    //
    //     inOrder(root.left, list);
    //     list.add(root.val);
    //     inOrder(root.right, list);
    // }


    // 递归判断，每次还要传入该子树的所有数应该大于/小于的数
    // 时间复杂度：O(n)，其中n为结点个数
    // 空间复杂度：O(height)，其中height为树的高度。最坏情况下二叉树为一条链，递归最深达到 n 层，故最坏情况下空间复杂度为 O(n)。
    public boolean isValidBST(TreeNode root) {
        // 注意这里得用Long而非Integer因为测试用例中val最小能取到Integer.MIN_VALUE，而root.val应该是不受父节点val限制的，因此它可以取到Integer.MIN_VALUE
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * 检查该树是否为有效的二叉搜索树
     * @param root
     * @param lower 该树的所有结点的val必须大于lower（下界）
     * @param upper 该树的所有结点的val必须小于upper（上界）
     * @return
     */
    public boolean check(TreeNode root, long lower, long upper) {
        if (root == null) {
            return true;
        }

        // 先判断大前提
        if (root.val <= lower || root.val >= upper) {
            return false;
        }

        // 再检查左右子树是否也为有效的二叉搜索树
        return check(root.left, lower, root.val) && check(root.right, root.val, upper);
    }
}
