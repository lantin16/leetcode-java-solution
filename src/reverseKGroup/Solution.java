package reverseKGroup;

/**
 * No.25 K 个一组翻转链表
 *
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 */

public class Solution {

    /**
     * 用一个长度为k的数组记录每次要翻转的k个节点，只有当按顺序填入k个新节点时才从后往前翻转指针，如果最后一段不足k个节点会因遍历指针已经为null而提前跳出循环，使得最后一段不会执行翻转。
     * @param head
     * @param k
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(k)
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode[] tmp = new ListNode[k];   // 长度为k的数组，用于记录需要翻转的一组k个节点的引用
        ListNode dummy = new ListNode(-1, head);    // 哨兵节点，统一头节点与后续节点的操作（否则第一段就需要特殊处理，因为最终头节点应该为第一段的最后一个节点）
        ListNode cur = head;    // 遍历指针
        head = dummy;   // head用于记录每一段的上一个翻转完成的节点，这样就可以在翻转完成后连接上新的开始节点，因此初始指向哨兵节点
        int cnt = 0;    // 计数器，判断一段是否达到k个节点，同时也可作为数组的索引

        while(cur != null) {
            tmp[cnt++] = cur;   // 记录下cur所指的节点然后计数+1
            cur = cur.next; // cur继续往后
            if(cnt == k) {  // k个节点已填满数组，该翻转了
                head.next = tmp[k-1];   // head接上这一段新的开始节点（也就是原来的末尾）
                for(int i = k - 1; i > 0; i--) {
                    tmp[i].next = tmp[i-1]; // 依据数组中存的引用从后往前翻转链表
                }
                tmp[0].next = cur;  // 这这一段新的结尾指向下一段的头（暂时的头）
                head = tmp[0];  // 将head指向下一段的上一个节点（这一段的新的结尾）
                cnt = 0;    // 重置计数器
            }
        }
        return dummy.next;
    }
}
