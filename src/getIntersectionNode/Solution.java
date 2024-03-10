package getIntersectionNode;

/**
 * No.160 相交链表
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 */

public class Solution {

    // 先计算两个链表的长度，然后长的比短的提前走delta步，之后如果相交它们就会同时到达相交点
    // 链表A和B均遍历了两次，时间复杂度O(m+n)
    // 仅用了常数个变量来存储长度或遍历指针等，空间复杂度O(1)
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 分析：短的一定比长的更早到达相交节点。但是单链表无法直接知道长度，只能先通过遍历获取链表长度
        int lenA = 0, lenB = 0;
        ListNode p = headA, q = headB;  // 遍历指针

        // 获取链表长度
        while (p != null) {
            lenA++;
            p = p.next;
        }

        while (q != null) {
            lenB++;
            q = q.next;
        }

        ListNode longL, shortL;  // 分别指向较长和较短的单链表
        if (lenA > lenB) {
            longL = headA;
            shortL = headB;
        } else {
            longL = headB;
            shortL = headA;
        }

        int delta = Math.abs(lenA - lenB);  // 两个链表的长度差

        // 长的比短的提前走delta步两个链表就会同时到达相交节点（如果有的话）
        while (shortL != null) {
            if (delta > 0) {    // 前面delta步只有较长的链表走
                longL = longL.next;
                delta--;
            } else {    // 后面两个链表同步走
                if (shortL == longL) {  // 两个指针指向同一节点，找到结果
                    return shortL;
                } else {
                    longL = longL.next;
                    shortL = shortL.next;
                }
            }
        }
        return null;
     }

}
