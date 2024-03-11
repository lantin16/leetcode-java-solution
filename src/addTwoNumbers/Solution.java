package addTwoNumbers;

/**
 * No.2 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */

public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1, null);   // 哨兵节点，为了方便最后返回合并后的链表，该节点并不属于两个链表，最后会去除
        ListNode rear = head;   // 初始尾节点指针就指向头节点

        int carry = 0;  // 用于记录进位
        while (l1 != null || l2 != null || carry != 0) {
            int n = 0;  // 结果中本位的数
            if (l1 != null) {
                n += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                n += l2.val;
                l2 = l2.next;
            }
            n += carry;

            if (n >= 10) {  // 需要进位
                carry = 1;
                n -= 10;
            } else {    // 无需进位
                carry = 0;  // 重置进位值
            }

            // 向结果链表中插入本位应该的值
            rear.next = new ListNode(n);
            rear = rear.next;
        }

        return head.next;
    }

}
