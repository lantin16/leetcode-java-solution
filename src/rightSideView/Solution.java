package rightSideView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * No.199 二叉树的右视图
 * 给定一个二叉树的根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 */

public class Solution {

    // bfs
    // 层序遍历，每层的最后一个结点即为右视图看到的该层结点
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode node = new TreeNode();
            while (size > 0) {
                node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            list.add(node.val);
        }

        return list;
    }


    // private List<Integer> ans;
    //
    // public List<Integer> rightSideView(TreeNode root) {
    //     ans = new ArrayList<>();
    //     dfs(root, 0);
    //     return ans;
    // }
    //
    // /**
    //  * 深度优先遍历，depth为node的深度，对于每个depth可能有多个结点，因此对于每个depth，先设为左边的数，如果同层右边还有数，则设右边的数时会将之前设的左数覆盖。
    //  * 最后ans中以某个depth为索引的值存的就是该depth最右边的数。
    //  * @param node
    //  * @param depth
    //  */
    // private void dfs(TreeNode node, int depth) {
    //     if (node == null) return;
    //     if (ans.size() <= depth) {
    //         ans.add(node.val);
    //     } else {
    //         ans.set(depth, node.val);
    //     }
    //     dfs(node.left, depth + 1);  // 先设左
    //     dfs(node.right, depth + 1); // 再设右
    // }
}
