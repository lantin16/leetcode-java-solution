package topKFrequent;

import java.util.HashMap;
import java.util.Map;

/**
 * No.347 前 K 个高频元素
 *
 * 给定一个整数数组 nums 和一个整数 k ，请返回其中出现频率前 k 高的元素。可以按 任意顺序 返回答案。
 */

public class Solution {

    // class HeapNode {
    //     int num;
    //     int cnt;
    //
    //     public HeapNode(){}
    //
    //     public HeapNode(int num, int cnt) {
    //         this.num = num;
    //         this.cnt = cnt;
    //     }
    // }
    //
    // //  手动实现一个大小为k的小根堆，不断插入并调整堆，最后留下的k个元素就是前 K 个高频元素
    // public int[] topKFrequent(int[] nums, int k) {
    //     int[] res = new int[k];
    //     Map<Integer, Integer> count = new HashMap<>();
    //     // 遍历nums获取每个数字的出现次数
    //     for (int x : nums) {
    //         if (count.containsKey(x)) {
    //             count.put(x, count.get(x) + 1);
    //         } else {
    //             count.put(x, 1);
    //         }
    //     }
    //
    //     HeapNode[] minHeap = new HeapNode[k]; // 容量为k的小根堆
    //     int heapSize = 0;
    //
    //     // 依次将所有HeapNode尝试插入大小为k的小根堆，最后留下的就是 前 K 个高频元素
    //     for (int key : count.keySet()) {
    //         int value = count.get(key);
    //         heapSize = insert(minHeap, heapSize, k, new HeapNode(key, value));
    //     }
    //
    //     // 将队中的k个元素全部取出，就是结果
    //     for (int i = 0; i < k; i++) {
    //         res[i] = minHeap[i].num;
    //     }
    //     return res;
    // }
    //
    // // 尝试插入新元素到小根堆，并返回本次插入操作后的heapSize
    // // 1. 若堆未满，直接插入然后调整；
    // // 2. 若堆已满，但新元素的次数不比堆顶元素大，则该元素不可能是前k个高频数其中之一，直接跳过。
    // // 3. 若堆已满，且新元素的次数比堆顶元素大，则将堆顶元素弹出，堆化，然后将新元素插入到末尾，堆化
    // public int insert(HeapNode[] minHeap, int heapSize, int k, HeapNode node) {
    //     if (heapSize < k) { // 堆未满，直接插入然后调整
    //         minHeap[heapSize] = node;
    //         heapSize++;
    //         siftUp(minHeap, heapSize - 1);  // 上浮
    //     } else if (node.cnt > minHeap[0].cnt) { // 堆已满，且新元素的次数比堆顶元素大，则将堆顶元素弹出，堆化，然后将新元素插入到末尾，堆化
    //         swap(minHeap, 0, heapSize - 1); // 将堆顶元素与最后的元素交换，heapSize减一，相当于删除堆顶元素，然后将最后一个元素提到堆顶
    //         minHeapify(minHeap, --heapSize, 0);
    //         minHeap[heapSize++] = node;   // 将新元素插到堆尾，然后向上调整
    //         siftUp(minHeap, heapSize - 1);  // 上浮
    //     }
    //     // 堆已满，但新元素的次数不比堆顶元素大。由于题目保证数组中前 k 个高频元素的集合是唯一的，因此则该元素不是前k个高频数其中之一，直接不管
    //
    //     return heapSize;    // 返回本次插入后的堆大小
    // }
    //
    // // 堆化
    // public void minHeapify(HeapNode[] minHeap, int heapSize, int i) {
    //     int lc = i * 2 + 1, rc = i * 2 + 2; // i的左右孩子下标
    //     int minCntIdx = i;  // lc, rc, i中最小次数的下标
    //
    //     if (lc < heapSize && minHeap[lc].cnt < minHeap[minCntIdx].cnt) {
    //         minCntIdx = lc;
    //     }
    //     if (rc < heapSize && minHeap[rc].cnt < minHeap[minCntIdx].cnt) {
    //         minCntIdx = rc;
    //     }
    //
    //     if (minCntIdx != i) {   // 如果次数最少的不再是i，则i需要与次数最少的孩子交换
    //         swap(minHeap, i, minCntIdx);
    //         minHeapify(minHeap, heapSize, minCntIdx);
    //     }
    // }
    //
    // // 上浮操作，调整堆
    // public void siftUp(HeapNode[] minHeap, int i) {
    //     if (i == 0) {   // 到达根节点就不用上浮了
    //         return;
    //     }
    //
    //     int parent = (i - 1) / 2;   // 索引为i的结点的父节点索引为 (i - 1) / 2
    //     if (minHeap[parent].cnt > minHeap[i].cnt) {
    //         swap(minHeap, parent, i);
    //         siftUp(minHeap, parent);    // 交换到父节点后继续判断是否上浮
    //     } else {
    //         return; // 如果不用交换的话上浮到此为止
    //     }
    //
    // }
    //
    // public void swap(HeapNode[] minHeap, int i, int j) {
    //     HeapNode tmp = minHeap[i];
    //     minHeap[i] = minHeap[j];
    //     minHeap[j] = tmp;
    // }




    /**
     * 写法优化
     * 既然已经统计除了词频的哈希表，那么哈希表中的数（key）是唯一的，通过哈希表就可以获得数对应的出现次数，也就不需要将次数和数绑定来一起加到堆中
     * 小根堆直接存数即可，涉及到要比较出现次数的时候访问哈希表即可
     */

    // // 手动实现一个大小为k的小根堆，不断插入并调整堆，最后留下的k个元素就是前 K 个高频元素
    // // 时间复杂度：O(nlogk)，其中n为nums长度。分析：首先，遍历一遍数组统计元素的频率，这一系列操作的时间复杂度是 O(n)；
    // // 接着，遍历用于存储元素频率的 map，如果元素的频率大于最小堆中顶部的元素，则将顶部的元素删除并将该元素加入堆中，这里维护堆的大小是 k，每次调整堆
    // // 的时间复杂度是O(logk)。因此，总的时间复杂度是O(nlogk)。
    // // 空间复杂度：O(n)。分析：哈希表的空间复杂度为O(n)，堆用数组存储，空间复杂度是O(k)。
    // public int[] topKFrequent(int[] nums, int k) {
    //     int[] res = new int[k];
    //     Map<Integer, Integer> count = new HashMap<>();
    //     // 遍历nums获取每个数字的出现次数
    //     for (int x : nums) {
    //         // 这里也可以优化：由于java的HashMap在get一个不存在的key时是会返回null的，因此不能直接get出来然后+1再put回去
    //         // 这里可以用getOrDefault(x, 0)
    //         count.put(x, count.getOrDefault(x, 0) + 1);
    //     }
    //
    //     int[] minHeap = new int[k]; // 容量为k的小根堆
    //     int heapSize = 0;
    //
    //     // 依次将nums中所有非重复数尝试插入大小为k的小根堆，最后留下的就是 前 K 个高频元素
    //     for (int key : count.keySet()) {
    //         heapSize = insert(minHeap, heapSize, k, key, count);
    //     }
    //
    //     // 将队中的k个元素全部取出，就是结果
    //     for (int i = 0; i < k; i++) {
    //         res[i] = minHeap[i];
    //     }
    //     return res;
    // }
    //
    // // 尝试插入新元素到小根堆，并返回本次插入操作后的heapSize
    // // 1. 若堆未满，直接插入然后调整；
    // // 2. 若堆已满，但新元素的次数不比堆顶元素大，则该元素不可能是前k个高频数其中之一，直接跳过。
    // // 3. 若堆已满，且新元素的次数比堆顶元素大，则将堆顶元素弹出，堆化，然后将新元素插入到末尾，堆化
    // public int insert(int[] minHeap, int heapSize, int k, int x, Map<Integer, Integer> map) {
    //     if (heapSize < k) { // 堆未满，直接插入然后调整
    //         minHeap[heapSize] = x;
    //         heapSize++;
    //         siftUp(minHeap, heapSize - 1, map);  // 上浮
    //     } else if (map.get(x) > map.get(minHeap[0])) { // 堆已满，且新元素的次数比堆顶元素大，则将堆顶元素弹出，堆化，然后将新元素插入到末尾，堆化
    //         swap(minHeap, 0, heapSize - 1); // 将堆顶元素与最后的元素交换，heapSize减一，相当于删除堆顶元素，然后将最后一个元素提到堆顶
    //         minHeapify(minHeap, --heapSize, 0, map);
    //         minHeap[heapSize++] = x;   // 将新元素插到堆尾，然后向上调整
    //         siftUp(minHeap, heapSize - 1, map);  // 上浮
    //     }
    //     // 堆已满，但新元素的次数不比堆顶元素大。由于题目保证数组中前 k 个高频元素的集合是唯一的，因此则该元素不是前k个高频数其中之一，直接不管
    //
    //     return heapSize;    // 返回本次插入后的堆大小
    // }
    //
    // // 堆化
    // public void minHeapify(int[] minHeap, int heapSize, int i, Map<Integer, Integer> map) {
    //     int lc = i * 2 + 1, rc = i * 2 + 2; // i的左右孩子下标
    //     int minCntIdx = i;  // lc, rc, i中最小次数的下标
    //
    //     if (lc < heapSize && map.get(minHeap[lc]) < map.get(minHeap[minCntIdx])) {
    //         minCntIdx = lc;
    //     }
    //     if (rc < heapSize && map.get(minHeap[rc]) < map.get(minHeap[minCntIdx])) {
    //         minCntIdx = rc;
    //     }
    //
    //     if (minCntIdx != i) {   // 如果次数最少的不再是i，则i需要与次数最少的孩子交换
    //         swap(minHeap, i, minCntIdx);
    //         minHeapify(minHeap, heapSize, minCntIdx, map);
    //     }
    // }
    //
    // // 上浮操作，调整堆
    // public void siftUp(int[] minHeap, int i, Map<Integer, Integer> map) {
    //     if (i == 0) {   // 到达根节点就不用上浮了
    //         return;
    //     }
    //
    //     int parent = (i - 1) / 2;   // 索引为i的结点的父节点索引为 (i - 1) / 2
    //     if (map.get(minHeap[parent]) > map.get(minHeap[i])) {
    //         swap(minHeap, parent, i);
    //         siftUp(minHeap, parent, map);    // 交换到父节点后继续判断是否上浮
    //     } else {
    //         return; // 如果不用交换的话上浮到此为止
    //     }
    //
    // }
    //
    // public void swap(int[] minHeap, int i, int j) {
    //     int tmp = minHeap[i];
    //     minHeap[i] = minHeap[j];
    //     minHeap[j] = tmp;
    // }


    /**
     * 快速排序
     * *** 这一类题：求一个数组的 第k大 / 前k大 的数 ***
     * 因为快速排序的一次划分，可以确定一个元素的最终位置，即 x 的最终位置为 q，并且保证 a[l⋯q−1] 中的每个元素小于等于 a[q]，且 a[q] 小于等于 a[q+1⋯r] 中的每个元素。
     * 这时判断k与两个部分子数组的关系就可以知道第k大的数在哪个部分，下次只对那个部分的数字递归进行划分即可，另一部分由于结果不可能在其中，就不用管了，
     * 比起将数组用快排全排序的O(nlogn)复杂度，这样每次只需在其中的一个分支递归即可，因此算法的平均时间复杂度降为 O(n)。
     */
    // 时间复杂度：平均情况下为O(n)，其中n为数组长度
    // 空间复杂度：平均情况下为O(n)，其中n为数组长度。分析：哈希表的大小为 O(n)，用于排序的数组的大小也为 O(n)，快速排序的空间复杂度最好情况为O(log n)，最坏情况为 O(n)。
    public int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];
        Map<Integer, Integer> count = new HashMap<>();
        // 遍历nums获取每个数字的出现次数
        for (int x : nums) {
            count.put(x, count.getOrDefault(x, 0) + 1);
        }

        int size = count.size();    // nums中不同元素的个数
        int[][] values = new int[size][2];    // 二维数组存放多个元组，每个元组由数字及其出现次数组成
        int i = 0;
        // 迭代器同时取出k、v的写法
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            // 将每个数字及其出现次数填入values中
            values[i][0] = entry.getKey();  // 元组的前一个数为出现的不同的数字
            values[i][1] = entry.getValue();  // 元组的后一个数为该数字的出现次数
            i++;
        }

        quickSort(res, 0, values, k, 0, size - 1);

        return res;
    }

    /**
     * 快速排序
     * @param res
     * @param resIdx 下次要填入res的位置
     * @param values
     * @param k
     * @param start
     * @param end
     */
    public void quickSort(int[] res, int resIdx, int[][] values, int k, int start, int end) {
        // 随机选择一个数作为基准
        int picked = start + (int) (Math.random() * (end - start + 1)); // Math.random()返回一个在 0.0（包含）和 1.0（不包含）之间的 double 类型的随机数
        swap(values, picked, start);    // 将随机选择的基准从它的位置换到start位置

        int pivot = values[start][1];
        int index = start;  // index用于记录当前不小于pivot的部分的最后一个位置，它的下一个位置应该是小于pivot的部分的第一个位置

        for (int i = start + 1; i <= end; i++) {
            // 使用双指针把不小于基准值的元素放到左边，小于基准值的元素放到右边
            if (values[i][1] >= pivot) {
                swap(values, i, index + 1);
                index++;
            }
        }

        swap(values, start, index); // 将基准值放到正确的位置，这里index后面都是小于pivot的元素了，前面都是不大于pivot的元素

        // >= pivot的部分：start ~ index， < pivot的部分：index+1 ~ end
        if (index - start + 1 <= k) {
            // 左边>=pivot的部分全都属于最高频的K个元素里面
            for (int i = start; i <= index; i++) {
                res[resIdx++] = values[i][0];
            }
            // 等于的情况说明已经将k个添加到res中，递归可以结束了
            // 小于的情况还需要对右边继续递归
            if (index - start + 1 < k) {
                // 还需要在右边<pivot的部分再找前 k - (index-start+1) 高频的数
                quickSort(res, resIdx, values, k - (index - start + 1), index + 1, end);
            }
        } else {    // 最高频的K个元素全部在左边>=pivot的内部，再对左边进行递归
            quickSort(res, resIdx, values, k, start, index);
        }
    }

    public void swap(int[][] values, int i, int j) {
        int[] tmp = values[i];
        values[i] = values[j];
        values[j] = tmp;
    }

}
