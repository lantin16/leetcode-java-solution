package isPalindrome;

/**
 * No.234 回文链表
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 * 回文 序列是向前和向后读都相同的序列。
 */

public class Solution {
    // 快慢指针
    // 遍历单链表，维护mid指针和pre指针，mid边移动边将其之前的链表反转，最后达到：头节点 <- ... <- pre | mid -> ... -> 尾节点 的效果
    // 遍历两次单链表，时间复杂度 O(n)
    // 空间复杂度 O(1)
    public boolean isPalindrome(ListNode head) {
        if (head.next == null) {    // 如果只有一个节点必是回文
            return true;
        }

        int len = 0;    // 节点个数
        ListNode p = head;  // 遍历指针，最后会走到链表尾部
        ListNode mid = head;    // 中间节点指针，标记链表中间的节点
        ListNode pre = null;    // 指向mid的前一个节点

        // 如何找到链表的中间节点？
        // ——p走两步，mid走一步，始终保持mid指向动态的中间节点。当p遍历到尾节点时mid就指向整个链表的中间节点

        // 遍历单链表，维护mid指针和pre指针，mid边移动边将其之前的链表反转，最后达到：头节点 <- ... <- pre | mid -> ... -> 尾节点 的效果
        while (p != null) {
            len++;
            // 如果当前长度为偶数则向后移动mid，否则mid不动（保证p走两步，mid走一步，mid始终指向中间节点）
            // 同时将mid之前的链表反转
            if (len % 2 == 0) {
                ListNode next = mid.next;
                mid.next = pre;
                pre = mid;
                mid = next;
            }
            p = p.next;
        }

        // 如果长度为奇数，则mid指向为正中心的节点，该节点不用检查，与左半部分的头节点pre对称的应该为mid.next
        if (len % 2 == 1) {
            mid = mid.next;
        }

        // pre和mid分别向两边检查是否回文
        while (mid != null) {
            if (mid.val != pre.val) {
                return false;
            } else {
                mid = mid.next;
                pre = pre.next;
            }
        }
        // 如果要求的话，最后可以恢复链表结构
        return true;
    }


    // // 暴力法，遍历单链表并用一个List按顺序存储元素，然后检查List是否回文（List能按照索引随机访问）
    // // 超出内存限制，此法理论可选，但无法通过
    // public boolean isPalindrome(ListNode head) {
    //     if (head.next == null) {    // 如果只有一个节点必是回文
    //         return true;
    //     }
    //
    //     List<Integer> list = new ArrayList<>();
    //     int len = 0;    // 节点个数
    //
    //     ListNode p = head;
    //     while (p != null) {
    //         list.add(p.val);
    //         len++;
    //     }
    //
    //     // 从中点向两边检查list是否回文
    //     int l, r; // 需要检查回文的左、右半段的起始下标（它们关于中点对称）
    //     if (len % 2 == 0) { // 偶数个节点（至少2个）
    //         r = len / 2;
    //         l = r - 1;
    //     } else {    // 奇数个节点（至少3个）
    //         r = (len + 1) / 2;
    //         l = r - 2;
    //     }
    //
    //     while (l >= 0) {
    //         int lv = list.get(l);
    //         int rv = list.get(r);
    //         if (lv != rv) {
    //             return false;
    //         } else {
    //             l--;
    //             r++;
    //         }
    //     }
    //     return true;
    // }
}
