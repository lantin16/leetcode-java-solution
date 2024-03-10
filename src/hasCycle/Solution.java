package hasCycle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * No.141 环形链表
 * 给你一个链表的头节点 head ，判断链表中是否有环。
 *
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
 *
 * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
 */

public class Solution {

    /**
     * 龟兔赛跑算法(Tortoise and Hare Algorithm)，是一个可以在有限状态机、迭代函数或者链表上判断是否存在环，求出该环的起点与长度的算法。
     * 相关结论见：https://zhuanlan.zhihu.com/p/496079620
     *
     * 兔子会不会「跳过」乌龟，从来不会和乌龟相遇呢？
     * 这是不可能的。如果有环的话，那么兔子和乌龟都会进入环中。这时用「相对速度」思考，乌龟不动，兔子相对乌龟每次只走一步，这样就可以看出兔子一定会和乌龟相遇了。
     */

    // 快慢指针
    // 快指针每次走两步，慢指针每次走一步，如果存在环，则快指针会先进入环且一直在环中运动，最终快指针会超过慢指针若干圈而相遇
    // 若存在环，每一轮快慢指针的距离将减小1，最多需要环的长度那么多轮一定会相遇，因此时间复杂度 O(n)
    // 空间复杂度 O(1)
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode f = head.next, s = head;   // 块、慢指针
        while (f != null && f.next != null) {
            if (f == s) {   // 快慢指针相遇代表有环，若无环则快指针永远在慢指针前面
                return true;
            }
            s = s.next;
            f = f.next.next;
        }

        // 跳出循环代表快指针已经到达链表尾部且无环
        return false;
    }


    // // 用HashSet记录遍历过的节点的引用
    // // 时间复杂度 O(n)
    // // 空间复杂度 O(n)
    // public boolean hasCycle(ListNode head) {
    //     Set<ListNode> seen = new HashSet<ListNode>();
    //     while (head != null) {
    //         // add()将元素添加到集合中。同时如果该元素之前就已经在集合中，返回false并保持集合不变。如果该元素之前不在则返回true并加入该元素
    //         if (!seen.add(head)) {
    //             return true;
    //         }
    //         head = head.next;
    //     }
    //     return false;
    // }
}
