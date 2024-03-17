package flatten;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * No.114 二叉树展开为链表
 * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
 * - 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
 * - 展开后的单链表应该与二叉树 先序遍历 顺序相同。
 */

public class Solution {

    // private List<TreeNode> list;
    //
    // // 先先序遍历确定结点顺序，再将它们改造成单链表
    // public void flatten(TreeNode root) {
    //     list = new ArrayList<>();
    //     preOrder(root);
    //
    //     // 改造成单链表
    //     for (int i = 0; i < list.size() - 1; i++) {
    //         list.get(i).left = null;
    //         list.get(i).right = list.get(i+1);
    //     }
    // }
    //
    // // // 先序遍历（递归写法）
    // // public void preOrder(TreeNode root) {
    // //     if (root == null) {
    // //         return;
    // //     }
    // //
    // //     list.add(root);
    // //     preOrder(root.left);
    // //     preOrder(root.right);
    // // }
    //
    // // 先序遍历（迭代写法）
    // public void preOrder(TreeNode root) {
    //     Deque<TreeNode> stk = new LinkedList<>();
    //
    //     while (root != null || !stk.isEmpty()) {
    //         while (root != null) {
    //             list.add(root); // 先访问根结点
    //             stk.push(root); // 将根节点入栈
    //             root = root.left;   // 向左子树深入
    //         }
    //
    //         // 直到没有左子树
    //         root = stk.pop();
    //         root = root.right;
    //     }
    // }




    // // 类似于94题中序遍历的Morris算法
    // // 将左子树最右结点的右孩子指向右子树（该节点不一定是右子树的前驱结点，但是该结点的右孩子一定为空，所以用它暂存右子树的引用），左子树就可以替换到根节点的右子树
    // // 时间复杂度：O(n)
    // // 空间复杂度：O(1)
    // public void flatten(TreeNode root) {
    //     while (root != null) {
    //         // 有左子树时
    //         if (root.left != null) {
    //             TreeNode r = root.left;
    //             // 找到左子树的最右边结点
    //             while (r.right != null) {
    //                 r = r.right;
    //             }
    //             // 最右节点的右孩子指向root的右子树
    //             r.right = root.right;
    //             // 将root的左子树替换到右子树的位置
    //             root.right = root.left;
    //             // 左子树置空
    //             root.left = null;
    //         }
    //         root = root.right;  // 继续检查右子树的根节点，重复上述操作，直到所有结点都没有左孩子
    //     }
    // }



    // 能不能在先序遍历的同时就将上一个节点的右指针更新为当前节点呢？
    // ——不能。因为如果将某个结点的右指针指向了前一个结点，那么后续要遍历该节点右子树时会发现右子树已经丢失了
    // 解决方法：逆过来进行，先序遍历的顺序为：中左右，那么其逆序为右左中，然后每遍历一个节点就将当前节点的右指针更新为上一个节点。
    // 由于是右左中的顺序更新的，因此遍历到根节点时左指针可以直接置为null而不用担心左孩子丢失，因为左孩子在根节点之前就已经遍历过了。
    // 时间复杂度：O(n)
    // 空间复杂度：O(H)，其中H为树的高度
    private TreeNode pre = null;    // 全局变量用来记录右左中顺序下当前结点的前一个结点
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        // 右
        flatten(root.right);
        // 左
        flatten(root.left);
        // 根
        root.right = pre;
        root.left = null;
        pre = root;
    }


}
