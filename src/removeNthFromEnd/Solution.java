package removeNthFromEnd;

/**
 * No.19 删除链表的倒数第N个节点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 */

public class Solution {

    // 双指针，两个指针相隔n，当一个指针遍历完链表后，另一个指针刚好指向倒数第n个结点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p = head, q = null;

        while (p.next != null) {
            p = p.next;
            n--;
            if (n == 0) {
                q = head;   // q出发
            } else if (n < 0) {
                q = q.next;
            }
        }

        // 此时p指向尾结点，q指向倒数第(n+1)个结点
        if (q == null) {    // 如果n=sz，即要删的为头结点，q压根没有出发
            head = head.next;
        } else if (p.next == null) {    // 如果要删的为尾结点
            p.next = null;
        } else {
            p.next = p.next.next;
        }

        return head;
    }
}
