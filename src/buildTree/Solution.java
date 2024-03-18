package buildTree;

import java.util.HashMap;
import java.util.Map;

/**
 * No.105 从前序与中序遍历序列构造二叉树
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 */

public class Solution {

    // 递归构造
    // 时间复杂度：O(n)
    // 空间复杂度：O(n)。分析：使用 O(n) 的空间存储哈希映射，以及 O(h)（其中 h 是树的高度）的空间表示递归时栈空间。这里 h<n，所以总空间复杂度为 O(n)。
    private Map<Integer, Integer> inMap = new HashMap<>();  // 通过哈希表可以快速定位根节点在中序遍历序列中的位置
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 1. 建立中序遍历的哈希表 key:元素val, value:其在inorder中的索引
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        // 2. 从根节点开始构造符合条件的二叉树
        return buildTree(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }


    /**
     * 根据中序和前序遍历构造二叉树
     * @param preorder
     * @param inorder
     * @param preBegin 要构造的二叉树的结点在先序遍历中的开始下标
     * @param preEnd 要构造的二叉树的结点在先序遍历中的结束下标
     * @param inBegin 要构造的二叉树的结点在中序遍历中的开始下标
     * @param inEnd 要构造的二叉树的结点在中序遍历中的结束下标
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder, int preBegin, int preEnd, int inBegin, int inEnd) {
        int num = preEnd - preBegin + 1;    // 该二叉树的结点数
        if (num == 0) { // 没有节点
            return null;
        }
        if (num == 1) { // 只有一个结点
            return new TreeNode(preorder[preBegin]);
        }

        // 1. 先找先序遍历中该区域的第一个元素，即为根节点
        TreeNode root = new TreeNode(preorder[preBegin]);

        // 2. 再在中序遍历中寻找根节点val的位置，该位置左边的都属于左子树，右边的属于右子树
        int inMid = inMap.get(preorder[preBegin]);
        int leftNum = inMid - inBegin;  // 左子树的节点个数
        int rightNum = inEnd - inMid;  // 右子树的节点个数

        // 3. 对左子树和右子树以相同的方式构建
        root.left = buildTree(preorder, inorder, preBegin + 1, preBegin + leftNum, inBegin, inMid - 1);
        root.right = buildTree(preorder, inorder, preBegin + leftNum + 1, preBegin + leftNum + rightNum, inMid + 1, inEnd);

        return root;
    }
}
