package Trie;

import maxPathSum.TreeNode;

/**
 * No.208 实现 Trie (前缀树)
 * https://leetcode.cn/problems/implement-trie-prefix-tree/description/?envType=study-plan-v2&envId=top-100-liked
 */


/**
 * Trie，又称前缀树或字典树，是一棵有根树，其每个节点包含以下字段：
 * - 指向子节点的指针数组 children。对于本题而言，数组长度为 26，即小写英文字母的数量。此时 children[0]对应小写字母 a，children[1]对应小写字母 b，…，children[25]对应小写字母 z。
 * - 布尔字段 isEnd，表示该节点是否为字符串的结尾。
 */
public class Trie {

    // 前缀树结点
    class TrieNode {
        boolean isEnd; // 该结点是否是一个字符串的结束
        TrieNode[] next;    // 该前缀后已插入的字符

        public TrieNode() {
            isEnd = false;
            next = new TrieNode[26];    // 题目规定存入前缀树的只可能是26个小写字母
        }
    }

    private TrieNode root;  // 根节点，根节点不存字符

    // 初始化前缀树对象
    public Trie() {
        root = new TrieNode();   // 根节点
    }

    /**
     * 向前缀树中插入字符串 word
     *
     * 我们从字典树的根开始，插入字符串。对于当前字符对应的子节点，有两种情况：
     * - 子节点存在。沿着指针移动到子节点，继续处理下一个字符。
     * - 子节点不存在。创建一个新的子节点，记录在 children 数组的对应位置上，然后沿着指针移动到子节点，继续搜索下一个字符。
     * 重复以上步骤，直到处理字符串的最后一个字符，然后将当前节点标记为字符串的结尾。
     *
     * @param word
     */
    public void insert(String word) {
        TrieNode p = root;  // 搜索指针
        // 对word的每一个字符，从根节点往下匹配
        for (char c : word.toCharArray()) {
            if (p.next[c - 'a'] == null) {  // 如果没有该字符，则插入新的结点
                p.next[c - 'a'] = new TrieNode();
            }
            p = p.next[c - 'a'];
        }
        p.isEnd = true;    // 最后一个结点处要设置isEnd为true
    }


    /**
     * 查找字符串
     * 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false
     *
     * 类似于搜前缀，只不过在最后多一步检查word末尾字符对应结点的 isEnd 是否为真
     *
     * @param word
     * @return
     */
    public boolean search(String word) {
        TrieNode p = root;
        for (char c : word.toCharArray()) {
            if (p.next[c - 'a'] == null) {
                return false;
            }
            p = p.next[c - 'a'];
        }
        return p.isEnd;
    }


    /**
     * 查找前缀
     * 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false
     *
     * 我们从字典树的根开始，查找前缀。对于当前字符对应的子节点，有两种情况：
     * - 子节点存在。沿着指针移动到子节点，继续搜索下一个字符。
     * - 子节点不存在。说明字典树中不包含该前缀，返回空指针。
     * 重复以上步骤，直到返回空指针或搜索完前缀的最后一个字符。
     *
     * 若搜索到了前缀的末尾，就说明字典树中存在该前缀。
     *
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (char c : prefix.toCharArray()) {
            if (p.next[c - 'a'] == null) {
                return false;
            }
            p = p.next[c - 'a'];
        }
        return true;
    }
}
