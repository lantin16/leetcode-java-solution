package MinStack;


import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * No.155 最小栈
 *
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * 实现 MinStack 类:
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 */

public class MinStack {

    Deque<Integer> stk; // 普通栈
    PriorityQueue<Integer> minHeap; // 优先队列实现小根堆（默认就是小根堆）

    public MinStack() {
        stk = new LinkedList<>();
        minHeap = new PriorityQueue<>();   // PriorityQueue当中，默认最“小”的元素就是优先级最高的元素（越左边越靠近队首，优先级越高，自然顺序a<b<c，则a优先级最高）

//        // 如果想实现大根堆
//        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
//        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((x, y) -> y-x);    // 想谁排在前面，就返回一个负数

    }

    public void push(int val) {
        stk.push(val);
        minHeap.add(val);
    }

    public void pop() {
        if (!stk.isEmpty()) {
            int top = stk.pop();
            minHeap.remove(top);
        }
    }

    public int top() {
        int top = -1;
        if (!stk.isEmpty()) {
            top = stk.peek();  // 查看栈顶元素但不删除
        }
        return top;
    }

    public int getMin() {
        int min = -1;
        if (!minHeap.isEmpty()) {
            min = minHeap.peek();
        }
        return min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */