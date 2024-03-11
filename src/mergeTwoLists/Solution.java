package mergeTwoLists;

/**
 * No.21 合并两个有序链表
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */

public class Solution {

    // 递归
    // 时间复杂度 O(m+n)
    // 空间复杂度 O(m+n)
    // public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    //     if (list1 == null) {
    //         return list2;
    //     } else if (list2 == null) {
    //         return list1;
    //     } else if (list1.val <= list2.val){
    //         list1.next = mergeTwoLists(list1.next, list2);
    //         return list1;
    //     } else {
    //         list2.next = mergeTwoLists(list1, list2.next);
    //         return list2;
    //     }
    // }



    // 迭代
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0, null);   // 哨兵节点，为了方便最后返回合并后的链表，该节点并不属于两个链表，最后会去除
        ListNode rear = head;   // 初始尾节点指针就指向头节点

        // 尾插法
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                rear.next = list1;
                list1 = list1.next;
            } else {
                rear.next = list2;
                list2 = list2.next;
            }
            rear = rear.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        // if (list1 != null) {    // list1还没完，直接拼接到res后面
        //     rear.next = list1;
        // }
        //
        // if (list2 != null) {    // list2还没完，直接拼接到res后面
        //     rear.next = list2;
        // }

        // 直接这样写：
        rear.next = list1 != null ? list1 : list2;

        return head.next;    // 记得将为了方便尾插初始化的头节点去除
    }
}
