package sortList;

import java.util.List;

/**
 * No.148 排序链表
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 *
 * 最适合链表的排序算法是 归并排序
 */

public class Solution {

    // // 自底向上归并排序
    // // 时间复杂度 O(nlogn)
    // // 空间复杂度 O(1)，没有递归
    // // 虽然但是，这个方法的代码难写得一批！！！
    // public static ListNode sortList(ListNode head) {
    //     if (head == null) {
    //         return null;
    //     }
    //
    //     // 1. 求出链表长度
    //     int len = 0;
    //     ListNode p = head;
    //     while (p != null) {
    //         len++;
    //         p = p.next;
    //     }
    //
    //     ListNode dummyHead = new ListNode(-1, head);    // 哨兵节点
    //     for (int subLen = 1; subLen < len; subLen *= 2) {   // subLen：每次要排序的子链表的长度
    //         // 每次将链表拆成（不是真的拆）若干个长度为subLen的子链表（最后一个子链表长度可以小于subLen），将它们每两个一组进行合并
    //         ListNode cur = dummyHead.next;    // 当前遍历节点
    //         ListNode pre = dummyHead;   // 当前节点的前一个节点
    //         while (cur != null) {
    //             // 按顺序找出一对长度为subLen的子链表
    //             ListNode head1 = cur;
    //             for (int i = 1; i < subLen && cur.next != null; i++) {
    //                 cur = cur.next;
    //             }
    //             ListNode head2 = cur.next;  // 可能为null
    //             cur.next = null;    // 记录好第一段的后续head2后可以先切断，等这两个子链表合并完后再接上
    //
    //             cur = head2;
    //             for (int i = 1; i < subLen && cur != null && cur.next != null; i++) {   // cur != null是判断head2为null的情况，cur.next != null是判断最后一个子链表长度不足subLen提前结束的情况
    //                 cur = cur.next;
    //             }
    //             ListNode next = null;   // 记录head2子链表之后的链表部分（如果有的话）
    //             if (cur != null) {
    //                 next = cur.next;
    //                 cur.next = null;    // 记录完后续的链表头节点后就可以先切断，等这两个子链表合并完后再接上
    //             }
    //
    //             // 对有序的两个长度为subLen的子链表进行合并，得到长度为subLen*2的更大的有序子链表（一开始subLen=1每个长度为1的子链表已经有序）
    //             while (head1 != null && head2 != null) {
    //                 if (head1.val <= head2.val) {
    //                     pre.next = head1;
    //                     head1 = head1.next;
    //                 } else {
    //                     pre.next = head2;
    //                     head2 = head2.next;
    //                 }
    //                 pre = pre.next;
    //             }
    //             pre.next = (head1 != null) ? head1 : head2; // 如果某个子链表提前被取完，则将另一个子链表的剩余部分直接接上
    //             while (pre.next != null) {  // 将pre移到末尾
    //                 pre = pre.next;
    //             }
    //             pre.next = next;    // 将后面的链表部分拼接上去
    //             cur = next;
    //         }
    //     }
    //
    //     return dummyHead.next;
    // }



    // 自顶向下归并排序，递归实现（代码好写！）
    // 时间复杂度 O(nlogn)
    // 主要取决于递归调用的栈空间，而链表可以通过修改引用来更改节点顺序，无需像数组一样开辟额外空间；故空间复杂度 O(logn)
    public static ListNode sortList(ListNode head) {
        // 1. 拆分：找到链表的中点，以中点为分界，将链表拆分成两个子链表。（寻找链表的中点可以使用快慢指针的做法）

        // 递归终止条件：子链表没有或只有一个节点
        if (head == null || head.next == null) {
            return head;
        }

        // 快慢指针找中点
        ListNode fast = head, slow = head;  // 快慢指针，当fast走到尾节点时，若总共奇数个节点，则slow指向最中间的节点；若总共偶数个节点，则slow指向中间偏左的那个节点
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;  // 快指针一次走两步
            slow = slow.next;   // 快指针一次走一步
        }

        // 从中点拆开（此时slow就指向中点）
        ListNode lh = head; // 左边子链表的头节点
        ListNode rh = slow.next;    // 右边子链表的头节点
        slow.next = null;   // 从slow后面断开

        // 2. 对两个子链表分别排序。
        lh = sortList(lh);
        rh = sortList(rh);

        // 3. 将两个排序后的子链表合并，得到完整的排序后的链表。
        head = new ListNode(-1, null);  // 哨兵节点
        ListNode p = head;
        while (lh != null && rh != null) {
            if (lh.val <= rh.val) {
                p.next = lh;
                lh = lh.next;
            } else {
                p.next = rh;
                rh = rh.next;
            }
            p = p.next;
        }
        p.next = (lh != null) ? lh : rh;

        return head.next;
    }

    public static void main(String[] args) {
        ListNode n4 = new ListNode(3, null);
        ListNode n3 = new ListNode(1, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(4, n2);

        ListNode res = sortList(n1);
    }

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
