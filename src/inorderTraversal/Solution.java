package inorderTraversal;

import java.util.*;

/**
 * No.94 二叉树的中序遍历
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 */

public class Solution {

    // 递归
    // 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
    // 空间复杂度：O(n)。空间复杂度取决于递归的栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n)的级别。
    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> list = new ArrayList<>();
    //
    //     if (root == null) {
    //         return list;
    //     }
    //
    //     // 遍历左子树
    //     list = (root.left != null) ? inorderTraversal(root.left) : list;
    //
    //     // 遍历根节点
    //     list.add(root.val);
    //
    //     // 遍历右子树
    //     if (root.right != null) {
    //         list.addAll(inorderTraversal(root.right));  // 将右子树的遍历结果拼接到list中
    //     }
    //
    //     return list;
    // }


    // 递归的另一种写法，将结果list跟着递归调用往下传
    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> res = new ArrayList<>();
    //     inorder(root, res);
    //     return res;
    // }
    //
    // public void inorder(TreeNode root, List<Integer> res) {
    //     if (root == null) {
    //         return;
    //     }
    //
    //     // 遍历左子树
    //     inorder(root.left, res);
    //     // 遍历根节点
    //     res.add(root.val);
    //     // 遍历右子树
    //     inorder(root.right, res);
    // }


    // 迭代
    // 与递归等价，区别在于递归的时候隐式地维护了一个栈，而我们在迭代的时候需要显式地将这个栈模拟出来。
    // 时间复杂度：O(n)
    // 空间复杂度：O(n)
    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> res = new ArrayList<>();
    //     // Java堆栈Stack类已经过时，Java官方推荐使用Deque替代Stack使用。Deque堆栈操作方法：push()、pop()、peek()。
    //     Deque<TreeNode> stk = new LinkedList<>();
    //
    //     while (root != null || !stk.isEmpty()) {
    //         while (root != null) {
    //             stk.push(root); // 根节点非空则将其入栈
    //             root = root.left;   // 向左子树深入
    //         }
    //
    //         // 直到没有左子树
    //         root = stk.pop();
    //         res.add(root.val);  // 访问根节点
    //
    //         root = root.right;  // 向右子树深入
    //     }
    //
    //     return res;
    // }


    // Morris 中序遍历，能将非递归的中序遍历空间复杂度降为 O(1)
    // x有左子树时，前驱节点predecessor是当前节点x左子树上最右的节点
    // 中序遍历迭代法的难点就在于，需要先访问当前节点的左子树，才能访问当前节点。
    // 但是只有通往左子树的单向路程，而没有回程路，因此无法进行下去，除非用额外的数据结构记录下回程的路。
    // 在原始的树结构中，前驱节点的右子树一定为空，因此我们就可以暂时用其保存回程的路
    // 另外，我们也可以根据前驱节点的右子树来判断x的访问状态：
    // - predecessor的右子树为空，说明此时第一次访问x，x的左子树还没有访问，因此我们应该将predecessor的右子树指向x，并访问x的左子树
    // - predecessor的右子节树指向x，那么说明这是第二次访问x了，也就是说x的左子树已经访问完了，此时将x.val加入结果集中，接下来访问x的右子树，同时恢复predecessor的右子树为null
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        while (root != null) {
            // 如果节点x没有左子树，则直接加x的值加入res，再深入到x的右子树
            if (root.left == null) {
                res.add(root.val);
                root = root.right;
                continue;
            }

            // 如果x有左子树，则找到x的左子树上最右的节点（它为x的前驱节点，且原树中它的右子树一定为空）
            TreeNode predecessor = root.left;
            while (predecessor.right != null && predecessor.right != root) {
                predecessor = predecessor.right;
            }

            // 如果x的前驱节点的右孩子为空，说明这是第一次来到x/root这，将predecessor的右子树指向x
            if (predecessor.right == null) {
                predecessor.right = root;
                root = root.left;   // 向左子树遍历
            } else {    // 如果x的前驱节点的右孩子不为空，说明这是第二次来到x/root这，x的左子树已经遍历完毕，此时可以遍历x了
                res.add(root.val);
                predecessor.right = null;   // 恢复predecessor的右子树为空
                root = root.right;  // 该遍历x的右子树了
            }

        }

        return res;
    }
}
