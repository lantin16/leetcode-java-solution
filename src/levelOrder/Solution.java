package levelOrder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * No.102 二叉树的层序遍历
 *
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 */

public class Solution {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            // 将一层的节点值全部加入结果列表中
            List<Integer> levelList = new ArrayList<>();
            while (size > 0) {
                TreeNode node = queue.poll();
                levelList.add(node.val);

                // 对访问的每个节点，将其孩子入队
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            res.add(levelList);
        }
        return res;
    }


    public static void main(String[] args) {
        TreeNode n9 = new TreeNode(9);
        TreeNode n15 = new TreeNode(15);
        TreeNode n7 = new TreeNode(7);
        TreeNode n20 = new TreeNode(20, n15, n7);
        TreeNode root = new TreeNode(3, n9, n20);

        levelOrder(root);
    }
}
