package copyRandomList;

import java.util.HashMap;
import java.util.Map;

/**
 * No.138 随机链表的复制
 */

public class Solution {

    // 迭代 + 节点拆分
    // 首先将该链表中每一个节点拆分为两个相连的节点，例如对于链表 A->B->C可以拆分为A->A'->B->B'->C->C'
    // S‘为原节点S的拷贝节点，这样拷贝节点S’的随即指针就指向S的随即指针指向的节点T的后继节点
    // 遍历原链表3次，时间复杂度 O(n)
    // 空间复杂度 O(1)
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // 第一次遍历将该链表中每一个节点拆分为两个相连的节点
        Node p = head;
        while (p != null) {
            Node newNode = new Node(p.val);
            newNode.next = p.next;
            p.next = newNode;
            p = p.next.next;
        }

        // 第二次遍历填充新链表节点的random指针，并拆分出原链表和新链表
        p = head;
        while (p != null) {
            Node newNode = p.next;
            // 为新节点的random指针赋值
            newNode.random = (p.random == null) ? null : p.random.next; // 注意p.random可能为null，要处理一下，否则直接取p.random.next可能报错
            p = p.next.next;
        }

        // 第三次遍历填充新链表节点的next指针，并拆分出原链表和新链表
        p = head;
        Node newHead = head.next;   // 新链表的头节点（经过上述拆分后就为head的下一个节点）
        while (p != null) {
            Node newNode = p.next;
            Node nextP = newNode.next;  // 记录原链表中p的下一个结点
            // 为新节点的next指针赋值
            newNode.next = (nextP == null) ? null : nextP.next;
            p.next = nextP; // 将p指向结点的next指针恢复为原链表中p的下一个节点（恢复原链表的连接关系）
            p = nextP;
        }
        return newHead;
    }

    // // 第一次遍历就将新链表和原链表的对应结点绑定在一起，然后第二次遍历补充新链表结点的next和random
    // // 遍历原链表2次，时间复杂度 O(n)
    // // 额外用一个大小为n的哈希表，空间复杂度 O(n)
    // public Node copyRandomList(Node head) {
    //     if (head == null) {
    //         return null;
    //     }
    //
    //     Map<Node, Node> map = new HashMap<>();  // key：原链表的结点引用， value：新链表对应的结点引用
    //
    //     Node p = head;  // 遍历指针
    //     // 第一次遍历，创建新结点，并将将新链表和原链表的对应结点绑定在一起
    //     while (p != null) {
    //         map.put(p, new Node(p.val));
    //         p = p.next;
    //     }
    //
    //     // 第二次遍历，补充新链表结点的next和random
    //     p = head;
    //     while (p != null) {
    //         map.get(p).next = map.get(p.next);  // map.get(p)取出的是p对应的新结点
    //         map.get(p).random = map.get(p.random);
    //         p = p.next;
    //     }
    //
    //     return map.get(head);
    // }



    // // 暴力法
    // // 遍历原链表2次，时间复杂度 O(n)
    // // 额外用两个大小为n的哈希表，空间复杂度 O(n)
    // public Node copyRandomList(Node head) {
    //     Node newHead = new Node(-1);    // 哨兵结点
    //     Node p = head;  // 原链表上的遍历指针
    //     Node q = newHead;   // 新链表上的遍历指针
    //
    //     int i = 1;
    //     Map<Node, Integer> nodeMap = new HashMap<>();   // 记录原链表上结点的顺序
    //     Map<Integer, Node> newNodeMap = new HashMap<>();   // 记录新链表上某序号结点对应的引用
    //
    //     // 根据next按顺序构造新链表，并记录结点的顺序
    //     while (p != null) {
    //         Node newNode = new Node(p.val);
    //         q.next = newNode;
    //         nodeMap.put(p, i);
    //         newNodeMap.put(i, newNode);
    //         i++;
    //         p = p.next;
    //         q = q.next;
    //     }
    //
    //     // 再遍历一次原链表，根据nodeMap和newNodeMap补充新链表的random字段
    //     p = head;
    //     q = newHead.next;
    //     while (p != null) {
    //         if (p.random == null) {
    //             q.random = null;
    //         } else {    // 注意：random指向null则无法取得num
    //             int num = nodeMap.get(p.random);
    //             q.random = newNodeMap.get(num);
    //         }
    //         p = p.next;
    //         q = q.next;
    //     }
    //
    //     return newHead.next;
    // }
}
