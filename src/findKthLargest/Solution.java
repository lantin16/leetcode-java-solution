package findKthLargest;

import java.util.*;

/**
 * No.215  数组中的第K个最大元素
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 *
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
 */

public class Solution {

    /**
     * 优先队列，求第k大的数，就是求最大的k个数里面最小的那个，因此用小根堆，别搞反了
     *
     * 由于找第 K 大元素，其实就是整个数组排序以后后K个元素中最小的那个元素。因此，我们可以维护一个有 K 个元素的小根堆：
     * 1. 如果当前堆不满，直接添加；
     * 2. 堆满的时候，如果新读到的数小于等于堆顶，肯定不是我们要找的元素，只有新遍历到的数大于堆顶的时候，才将堆顶拿出，然后放入新读到的数，进而让堆自己去调整内部结构。
     */
    // // 时间复杂度：O(n log k)，其中n为nums的长度。分析：遍历数据 O(n)，堆内元素调整 O(log k)。
    // // 空间复杂度：O(k)。分析：小根堆大小为k。
    // public int findKthLargest(int[] nums, int k) {
    //     PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // PriorityQueue默认小数优先级高
    //
    //     // 先加 K个元素到小根堆中
    //     for (int i = 0; i < k; i++) {
    //         minHeap.offer(nums[i]);
    //     }
    //
    //     // 堆已满
    //     for (int i = k; i < nums.length; i++) {
    //         if (nums[i] > minHeap.peek()) { // 如果新元素比堆顶元素（堆内最小的元素）大，则将堆顶元素弹出，将新元素放入堆中
    //             minHeap.poll();
    //             minHeap.offer(nums[i]);
    //         }
    //         // 新元素 <= 堆顶元素，那么新元素最多只能是第(k+1)大的数，因此可以排除
    //     }
    //
    //     return minHeap.peek();  // 最后返回小根堆的堆顶元素即可
    // }



    /**
     * 面试可能会要求自己实现一个堆，而不让直接用PriorityQueue，因此还得掌握大根堆的实现方法
     * [建堆]、[调整]、[删除]
     */
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    // 构建大根堆
    public void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    // 调整大根堆，使得以i为根的子树满足大根堆性质，即父节点大于等于左右子节点。
    // 如果发现子节点中有比父节点大的值，就将它与父节点交换，并对交换后的子节点继续调整
    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    /**
     * 基于快速排序的选择方法（快速选择）
     *
     * 快速排序的核心包括“哨兵划分” 和 “递归” ：
     * 1. 哨兵划分： 以数组某个元素（一般选取首元素）为基准数，将所有小于基准数的元素移动至其左边，大于基准数的元素移动至其右边。
     * 2. 递归： 对 左子数组 和 右子数组 递归
     *
     * 每次经过「划分」操作后，我们一定可以确定一个元素的最终位置，即 x 的最终位置为 q，并且保证 a[l⋯q−1] 中的每个元素小于等于 a[q]，且 a[q] 小于等于 a[q+1⋯r] 中的每个元素。
     * 所以只要某次划分的 q 为倒数第 k 个下标的时候，我们就已经找到了答案，直接返回a[q]即可。至于 a[l⋯q−1] 和 a[q+1⋯r] 是否是有序的，我们不关心。
     *
     * 快速排序的性能和「划分」出的子数组的长度密切相关。如果每次选择第一个元素作为基准，那么遇到有序数组时间复杂度就会退化为O(n^2)。
     * 因此为了进一步提升算法的稳健性，我们采用随机选择的方式来选定基准数，它的时间代价的期望是 O(n)。
     */
    // 时间复杂度：O(n)，其中n为数组长度。分析：1. 对于长度为 N 的数组执行哨兵划分操作的时间复杂度为 O(N)。
    // 2. 每轮哨兵划分后，向下递归子数组的平均长度为 N/2。
    // 3.因此平均情况下，哨兵划分操作一共有 n+n/2+n/4+...+n/n=2n-1 （等比数列求和），复杂度为O(n)。
    // 空间复杂度：O(n)。分析：划分函数的平均递归深度为 O(log n)，辅助的 List空间为O(n)。
    // public int findKthLargest(int[] nums, int k) {
    //     List<Integer> list = new ArrayList<>();
    //     for (int num : nums) {
    //         list.add(num);
    //     }
    //     return quickSelect(list, k);
    // }
    //
    // public int quickSelect(List<Integer> nums, int k) {
    //     // 随机选择基准数
    //     Random rand = new Random();
    //     int pivot = nums.get(rand.nextInt(nums.size()));    // nextInt(x)生成一个0~(x-1)的随机数
    //
    //     // 将小于、等于、大于pivot的数分别划分至small, equal, big中
    //     List<Integer> small = new ArrayList<>();
    //     List<Integer> equal = new ArrayList<>();
    //     List<Integer> big = new ArrayList<>();
    //     for (int x : nums) {
    //         if (x < pivot) {
    //             small.add(x);
    //         } else if (x > pivot) {
    //             big.add(x);
    //         } else {
    //             equal.add(x);
    //         }
    //     }
    //
    //     // 判断第k大的元素位于哪个部分
    //     if (big.size() >= k) {  // 在大于pivot的这部分
    //         return quickSelect(big, k); // 对大于pivot的该部分进行快速选择
    //     } else if (equal.size() + big.size() >= k){ // 在等于pivot的这部分
    //         return pivot;   // 第k大的数字就是pivot，直接返回
    //     } else {
    //         return quickSelect(small, k - equal.size() - big.size());   // 在小于pivot的这部分继续找，但要更新k，因为在small里面就不是原来的k了
    //     }
    // }

}
