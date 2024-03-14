package LRUCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * No.146 LRU缓存（熟练掌握）
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构
 *
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

/**
 * LRU 缓存机制可以通过哈希表辅以双向链表实现
 * -双向链表按照被使用的顺序存储了这些键值对，靠近头部的键值对是最近使用的，而靠近尾部的键值对是最久未使用的。
 * -哈希表通过缓存数据的键映射到其在双向链表中的位置，用于快速定位key对应的双向链表节点
 *
 * 这样，我们首先使用哈希表进行定位，找出缓存项在双向链表中的位置，随后将其移动到双向链表的头部，即可在 O(1) 的时间内完成 get 或者 put 操作。
 *
 * ！！！！！！
 * 在双向链表的实现中，使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限，这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。
 *
 */

public class LRUCache {

    // 双向链表节点
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        DLinkedNode() {}

        DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }


    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>(); // key -> 存储该k-v的双向链表节点的引用
    private int size;   // 已使用的缓存数
    private int cap;   // 缓存容量
    private DLinkedNode dummyHead, dummyTail;  // 伪头节点、伪尾节点

    // 初始化LRU缓存
    public LRUCache(int capacity) {
        cap = capacity;
        size = 0;
        // 使用伪头部和伪尾部节点使得在添加节点和删除节点的时候就不需要检查相邻的节点是否存在
        dummyHead = new DLinkedNode();
        dummyTail = new DLinkedNode();

        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    // 获取key对应的value
    public int get(int key) {
        DLinkedNode node = cache.get(key);  // hashmap的get方法若key不存在则返回null

        if (node == null) {
            return -1;  // 缓存中不存在该key，返回-1
        }

        // 如果key存在，则该key最近使用，将对应节点移到双向链表头部
        moveToHead(node);
        return node.value;
    }

    // 将某节点移到双向链表头部
    private void moveToHead(DLinkedNode node) {
        // 1. 将该节点从原位置断开/删除
        removeNode(node);

        // 2. 将该节点加入到双向链表头部
        addToHead(node);
    }

    // 从双向链表中删除某节点
    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 将某节点插入到双向链表头部（dummyHead的后一个）
    private void addToHead(DLinkedNode node) {
        node.prev = dummyHead;
        node.next = dummyHead.next;
        dummyHead.next.prev = node;
        dummyHead.next = node;
    }

    // 新增或修改key的value
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) { // key在cache中不存在，需要加入到cache中
            // 判断此前cache是否已满
            if (size == cap) {  // cache已满
                // 将双向链表尾部节点去除（最久未使用），腾出空间
                DLinkedNode tail = removeTail();
                // 删除哈希表中的对应项
                cache.remove(tail.key);
                size --;
            }

            // cache有空间加入新的kv
            // 创建一个新节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 将新节点添加到哈希表中
            cache.put(key, newNode);
            // 将新节点插入到双向链表头部
            addToHead(newNode);
            size++;
        } else  {   // key在cache中已经存在，则修改其value
            node.value = value;
            // 将该节点移动到双向链表头部，代表刚刚使用过
            moveToHead(node);
        }
    }

    // 删除双向链表的尾部结点（dummyTail的前一个结点）
    private DLinkedNode removeTail() {
        // 1. 真正的尾部节点
        DLinkedNode tail = dummyTail.prev;
        // 2. 将该节点从原位置断开/删除
        removeNode(tail);
        // 3. 将该节点返回，因为还需要它的key以便删除cache哈希表中的对应项
        return tail;
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.get(1);    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lRUCache.get(1);    // 返回 -1 (未找到)
        lRUCache.get(3);    // 返回 3
        lRUCache.get(4);    // 返回 4
    }
}
