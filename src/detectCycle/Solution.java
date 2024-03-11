package detectCycle;

import java.util.HashSet;
import java.util.Set;

/**
 * No.142 环形链表 II
 * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 */

public class Solution {

    // 龟兔赛跑算法，假设龟兔同时从head出发，乌龟一次走一步，兔子一次走两步，
    // 龟兔在环内第一次相遇于节点C，则再令兔子头节点head出发，龟从C点出发，二者以相同速度每次走一步，则它们第二次相遇的点即为环的入口
    // 快慢指针
    public static ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode fast = head, slow = head;  // 要有上述结论，快慢指针必须一开始同时从head出发才行，若fast=head.next则相当于提前走了一步，不是同时出发
        boolean firstMeet =  false;
        while (fast != null && fast.next != null) {
            // 同在head出发不能算作相遇，因此得先起步确保fast到slow前面
            fast = fast.next.next;  // 快指针一次走两步
            slow = slow.next;   // 慢指针一次走一步

            if (fast == slow) { // 第一次相遇
                firstMeet = true;
                break;
            }
        }

        if (!firstMeet) {   // 如果是因为不满足while条件才跳出循环
            return null;    // fast已经走到链表尾部，无环
        }

        // 是由于第一次相遇才跳出循环，有环
        // 将快指针从head出发，慢指针从相遇点出发，以相同速度运动
        fast = head;
        while (fast != slow) {
            // 快慢指针每次均走一步
            fast = fast.next;
            slow = slow.next;
        }

        // 第二次相遇
        return fast;
    }


    // // 哈希
    // public ListNode detectCycle(ListNode head) {
    //     Set<ListNode> seen = new HashSet<>();
    //     while (head != null) {
    //         if (!seen.add(head)) {
    //             return head;
    //         }
    //         head = head.next;
    //     }
    //     return null;
    // }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        ListNode head = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2;

        ListNode res = detectCycle(head);
        System.out.println("res = " + res.val);
    }
}
