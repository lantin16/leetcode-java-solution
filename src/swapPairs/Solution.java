package swapPairs;

import java.util.List;

/**
 * No.24 两两交换链表中的节点
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 */

public class Solution {


    // 递归
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    public ListNode swapPairs(ListNode head) {
        // 递归中止条件：0个或1个结点
        if (head == null || head.next == null) {
            return head;
        }

        ListNode remain = swapPairs(head.next.next);    // 除去前两个结点外剩余部分链表的交换
        ListNode tmp = head.next;
        head.next = remain;
        tmp.next = head;
        head = tmp;

        return head;
    }


    // 迭代
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
//    public ListNode swapPairs(ListNode head) {
//        // 0个或1个结点的情况
//        if (head == null || head.next == null) {
//            return head;
//        }
//
//        // 不少于2个结点的情况
//        ListNode odd = head, even = head.next;  // 第奇数、偶数个结点
//        head = new ListNode(-1, null);  // 哨兵结点
//        ListNode pre = head; // odd的前一个结点
//        while (true) {
//            pre.next = even;
//            odd.next = even.next;
//            even.next = odd;
//
//            // 将odd和even向后移
//            if (odd.next == null || odd.next.next == null) { // 后面刚好没有要交换的对（总共偶数个结点）或最后留有一个结点，均不用交换
//                break;
//            } else {   // 后面还有两个及以上的结点
//                pre = odd;
//                odd = pre.next;
//                even = odd.next;
//            }
//        }
//
//        return head.next;
//    }
}
