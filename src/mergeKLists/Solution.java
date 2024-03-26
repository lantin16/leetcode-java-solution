package mergeKLists;

import java.util.PriorityQueue;

/**
 * No.23 合并 K 个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 */

public class Solution {

    // 分治，两两合并
    // 时间复杂度：O(kn x logk)，其中k为要合并的链表数，n为每个链表的最大长度
    // 空间复杂度： O(logk) 的栈空间
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        if (k == 0) {
            return null;
        }

        return merge(lists, 0,k-1);
    }

    /**
     * 合并lists中下标begin~end的所有升序链表
     * @param lists
     * @param begin
     * @param end
     * @return
     */
    public ListNode merge(ListNode[] lists, int begin, int end) {
        if (begin == end) { // 只有一个链表
            return lists[begin];
        }

        // 继续拆分
        int mid = begin + (end-begin) / 2;
        ListNode l1 = merge(lists, begin, mid);
        ListNode l2 = merge(lists, mid + 1, end);

        // 合并两个有序链表
        ListNode dummyHead = new ListNode(-1, null);
        ListNode pre = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                pre.next = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
        pre.next = (l1 != null) ? l1 : l2;

        return dummyHead.next;
    }


    // // 类似于合并两个有序链表，每次从k个有序链表的尚未合并的最前面的一个元素中挑出最小的加入结果链表中
    // // 怎么知道每个链表尚未合并的第一个元素在哪？——通过Status结构的指针获取
    // // 怎么快速找到这些第一个元素中最小val的那个？——优先队列
    // // 时间复杂度：考虑优先队列中的元素不超过 k 个，那么插入和删除的时间代价为 O(logk)。这里最多有 kn 个点，
    // // 对于每个点都被插入删除各一次，故总的时间代价即渐进时间复杂度为 O(kn×logk)。
    // //空间复杂度：这里用了优先队列，优先队列中的元素不超过 k 个，故渐进空间复杂度为 O(k)
    // class Status implements Comparable<Status> {
    //     int val;
    //     ListNode ptr;   // 通过ptr就能定位到此val对应的链表节点
    //
    //     Status(int val, ListNode ptr) {
    //         this.val = val;
    //         this.ptr = ptr;
    //     }
    //
    //     @Override
    //     public int compareTo(Status status2) {
    //         return this.val - status2.val;
    //     }
    // }
    //
    // // 优先队列，用于找到k个val中最小的那个
    // PriorityQueue<Status> queue = new PriorityQueue<Status>();
    //
    // public ListNode mergeKLists(ListNode[] lists) {
    //     ListNode dummyHead = new ListNode(-1, null);
    //     ListNode tail = dummyHead;
    //
    //     // 对每个链表，将它们的第一个节点加入优先队列
    //     for (ListNode node : lists) {
    //         if (node != null) {
    //             queue.offer(new Status(node.val, node));
    //         }
    //     }
    //
    //     // 不断从优先队列中弹出队首元素，即为val最小的链表节点对应的Status
    //     while (!queue.isEmpty()) {
    //         Status minStatus = queue.poll();
    //         ListNode p = minStatus.ptr;
    //         tail.next = p;
    //         tail = tail.next;
    //
    //         // 将p的下一个节点加入队列中
    //         if (p.next != null) {
    //             queue.offer(new Status(p.next.val, p.next));
    //         }
    //     }
    //
    //     // 队列为空，结束合并
    //     return dummyHead.next;
    // }
}
