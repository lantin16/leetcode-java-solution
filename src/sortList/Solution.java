package sortList;

/**
 * No.148 排序链表
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 */

public class Solution {

    // TODO 归并排序 见题解


//    // 有部分测试用例会超出时间限制
//    // 类似于冒泡排序，每一轮将一个较大的数向后交换到正确位置，知道没有可交换为止
//    // 时间复杂度 O(n^2)
//    // 空间复杂度 O(1)
//    public ListNode sortList(ListNode head) {
//        if (head == null) {
//            return null;
//        }
//
//        ListNode newHead = new ListNode(-1, head);  // 哨兵节点
//        boolean swap = false;   // 本轮是否有交换的标志，用于结束循环
//        do {
//            swap = false;   // 每轮开始前重置交换标志
//            ListNode p = newHead.next;  // 遍历指针
//            ListNode pre = newHead; // 指向p的前一个结点
//            while (p.next != null) {
//                if (p.val > p.next.val) {   // 将val大的结点向后移
//                    // 交换p和p.next结点的位置
//                    ListNode tmp = p.next;
//                    p.next = p.next.next;
//                    tmp.next = p;
//                    pre.next = tmp;
//
//                    swap = true;    // 更新交换标志
//                    pre = tmp;
//                } else {
//                    pre = p;
//                    p = p.next; // 后移遍历指针
//                }
//            }
//
//        } while (swap);
//
//        return newHead.next;
//    }
}
