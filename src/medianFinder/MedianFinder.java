package medianFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * No.295 数据流的中位数
 *
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 * - 例如 arr = [2,3,4] 的中位数是 3 。
 * - 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 *
 * 实现 MedianFinder 类:
 * - MedianFinder() 初始化 MedianFinder 对象。
 * - void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 * - double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
 */

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */

/*class MedianFinder {
    private List<Integer> list; // 存储所有数，并维护有序便于找出中位数

    public MedianFinder() {
        list = new ArrayList<>();
    }

    *//**
     * 插入通过二分查找确定插入位置，保证list始终有序
     * @param num
     *//*
    // 时间复杂度：O(n)。分析：二分查找插入位置时间复杂度为O(logn)，插入元素时间复杂度为O(n)，因为插入位置之后的元素需要后移
    public void addNum(int num) {
        int pos = findPosition(num);    // 插入位置
        list.add(pos, num); // 向list的指定位置插入元素，其后的元素会自动后移
    }

    *//**
     * 二分查找寻找插入位置
     * @param num
     * @return
     *//*
    private int findPosition(int num) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if(list.get(mid) == num) {
                return mid;
            } else if (list.get(mid) > num) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    *//**
     * 由于list有序，因此寻找中位数直接根据索引即可
     * @return
     *//*
    public double findMedian() {
        int size = list.size();
        int mid = size / 2; // 中间或中间偏右
        if(size % 2 == 0) { // 偶数个，返回中间两数的平均数
            return (list.get(mid - 1) + list.get(mid)) / 2.0;
        }

        return list.get(mid);   // 奇数个，返回最中间
    }
}*/






class MedianFinder {
    // 用两个堆分别存放较小和较大的一半数，且大顶堆存放较小的一半数，小顶堆存放较大的一半，这样两个堆的堆顶元素就是靠中间的两个数
    // Java可以通过PriorityQueue来实现堆
    // 约定：若总数N为奇数个，则大顶堆多存一个（存 N/2 + 1 个），小顶堆存 N/2个，中位数为大顶堆的堆顶元素
    // 约定：若总数N为偶数个，则两个堆元素个数相同，均为 N/2 个，中位数为两个堆顶元素除以2
    private PriorityQueue<Integer> maxHeap; // 大顶堆，用来存放较小的一半数，根就是较小一半数中最大的值
    private PriorityQueue<Integer> minHeap; // 小顶堆，用来存放较大的一半数，根就是较大一半数中最小的值
    private int count;  // 元素总数

    public MedianFinder() {
        // PriorityQueue是优先级高的排在前面，默认是小数优先级高，即默认升序
        maxHeap = new PriorityQueue<>((x, y) -> y -x);  // 实现大顶堆需要传一个比较规则，x想排在前面就得返回一个负数
        minHeap = new PriorityQueue<>();    // PriorityQueue默认实现的就是小顶堆
        count = 0;
    }

    // 时间复杂度：O(logn)。分析：堆的插入和弹出操作时间复杂度均为O(logn)
    public void addNum(int num) {
        if (count % 2 == 0) {   // 已有偶数个元素，num插入后大顶堆会多一个数
            maxHeap.add(num);   // 先插入大顶堆再说
        } else {    // 已有奇数个元素，num插入后小顶堆会多一个数
            minHeap.add(num);   // 先插入小顶堆再说
        }

        // 插入后可能出现堆顶元素大小相反的情况，则需要交换堆顶元素
        if (!minHeap.isEmpty() && !maxHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
            // 交换堆顶元素
            int top1 = maxHeap.poll();
            int top2 = minHeap.poll();
            maxHeap.add(top2);
            minHeap.add(top1);
        }
        count++;
    }


    public double findMedian() {
        // 若共偶数个元素，则中位数为两个堆顶元素的平均数
        // 若共奇数个元素，则中位数为大顶堆的堆顶元素（因为大顶堆多存了一个数）
        return count % 2 == 0 ? (maxHeap.peek() + minHeap.peek()) / 2.0 : maxHeap.peek();
    }
}