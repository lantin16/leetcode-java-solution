package maxDepth;

import sortList.ListNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * No.104 二叉树的最大深度
 *
 * 给定一个二叉树 root ，返回其最大深度。
 * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
 */

public class Solution {
    // 深度优先搜索，递归
    // 二叉树的最大深度 = max{左子树的最大深度, 右子树的最大深度} + 1
    // 时间复杂度：O（n），其中n为节点个数
    // 空间复杂度：O(height)，其中 height 表示二叉树的高度。
//    public int maxDepth(TreeNode root) {
//        if (root == null) {
//            return  0;
//        }
//
//        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
//    }


    // 广度优先搜索
    // 遍历完一层的节点则层数+1，一边在将队首取出的节点的左孩子和右孩子加入队尾，如何队列中分别出哪是这一层、哪是下一层的节点呢？——依靠size变量
    // 时间复杂度：O（n），其中n为节点个数
    // 空间复杂度：取决于队列存储的元素数量，其在最坏情况下会达到 O(n)
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return  0;
        }

        Queue<TreeNode> queue = new LinkedList<>(); // 用于存放每一层的节点
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();    // 记录这一层有多少个节点，用于确保这一层的节点全部拓展完后层数+1
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            depth++;

        }

        return depth;
    }
}
