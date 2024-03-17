package kthSmallest;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * No.230 二叉搜索树中第K小的元素
 * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
 *
 */

public class Solution {

    // // 利用二叉搜索树的中序遍历是升序序列
    // // 迭代，找到中序遍历的第k个元素即可停止，不需要遍历整棵树
    // // 时间复杂度：O(H+k)，其中 H 是树的高度。在开始遍历之前，我们需要 O(H) 到达叶结点。当树是平衡树时，时间复杂度取得最小值 O(logN+k)；
    // // 当树是线性树（树中每个结点都只有一个子结点或没有子结点）时，时间复杂度取得最大值 O(N+k)。
    // // 空间复杂度：O(H)，栈中最多需要存储 H 个元素。当树是平衡树时，空间复杂度取得最小值 O(logN)；当树是线性树时，空间复杂度取得最大值O(N)。
    // public int kthSmallest(TreeNode root, int k) {
    //     Deque<TreeNode> stk = new LinkedList<>();
    //
    //     while (root != null || !stk.isEmpty()) {
    //         // 一直往左子树深入
    //         while (root != null) {
    //             stk.push(root);
    //             root = root.left;
    //         }
    //
    //         root = stk.pop();
    //         k--;
    //         if (k == 0) {
    //             break;
    //         }
    //         root = root.right;
    //     }
    //
    //     return root.val;
    // }


    // 如果你需要频繁地查找第 k 小的值，你将如何优化算法？
    // 记录子树的结点数
    // 时间复杂度：O(n)，其中n是树中结点的总数。分析：预处理生成map的时间复杂度为O(n)。搜索的时间复杂度为 O(H)，其中 H 是树的高度；
    // 当树是平衡树时，搜索的时间复杂度取得最小值 O(logn)；当树是线性树时，搜索的时间复杂度取得最大值 O(n)。
    // 空间复杂度：O(n)。分析：使用了哈希表来存储每个结点为为根结点的子树的结点数，空间复杂度O(n)。递归搜索的空间复杂度为O(H)，为递归深度，也即树的高度
    public Map<TreeNode, Integer> map = new HashMap<>();   // key: root, value: 以root为根的树的结点数
    public int kthSmallest(TreeNode root, int k) {
        // 计算以树中每个结点为根的子树的结点数，并存入哈希表中（这一步操作只有第一次的时候需要做）
        countNode(root);

        // 从root开始判断左右子树的结点数与k的关系
        return search(root, k);
    }

    /**
     * 计算以root为根的树的结点数，并将其存入map后返回
     * @param root
     * @return
     */
    public int countNode(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 分别计算左右子树的结点数
        int lc = countNode(root.left);
        int rc = countNode(root.right);

        int cnt = lc + rc + 1;  // 该树的结点数 = 左子树的结点数 + 右子树的结点数 + 1（根节点自身）
        map.put(root, cnt);
        return cnt;
    }

    /**
     * 搜索以root为根的树中第k小的值，并返回
     * @param root
     * @param k
     * @return
     */
    public int search(TreeNode root, int k) {
        int lc; // root左子树的结点数

        if (root.left == null) {
            lc = 0;
        } else {
            lc = map.get(root.left);
        }

        // 从root开始判断左子树的结点数与k的关系，来确定第k小的元素的位置
        if (k-1 < lc) { // 1. 如果 k-1 < root左子树的结点数，则第k小的值就在root的左子树，继续向左子树搜索
            return search(root.left, k);
        } else if (k-1 == lc) { // 2. 如果root左子树的结点数 = k-1，则第k小的值就是root的值
            return root.val;
        } else {    // 3. 如果root左子树的结点数 > k-1，则第k小的值在root的右子树，向右子树搜索
            return search(root.right, k-lc-1);   //注意向右子树搜索就要找右子树的第(k-lc-1)小的数
        }

    }
}
