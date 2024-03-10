package reverseList;


/**
 * No.206 反转链表
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 */

public class Solution {

    // 从前往后遍历，边遍历边修改相邻两个节点的指针指向，同时将head向后移，最后head就指向了反转链表的头节点（即最开始的尾节点）
    // 只遍历一次链表，时间复杂度 O(n)
    // 空间复杂度 O(1)
    public ListNode reverseList(ListNode head) {
        // 零个或一个节点的情况
        if (head == null || head.next == null) {
            return head;
        }

        // 大于等于2个节点的情况
        ListNode pre = null; // 指向head的前一个节点
        ListNode next = head.next;  // 指向head的后一个节点
        while (next != null) {
            head.next = pre;
            pre = head;
            head = next;
            next = next.next;
        }

        // 最后一个节点的next还需要设置为pre
        head.next = pre;

        return head;
    }
}
