package findMedianSortedArrays;

/**
 * No.4 寻找两个正序数组的中位数
 *
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 */

public class Solution {


    /**
     * 归并
     *
     * 难点：
     * 1. 如何统一奇偶长度的情况：都找合并数组下标 len/2 的数（即第 len/2 + 1 个），如果是偶数则会找到中间偏右那个，如果是奇数则直接找到中位数，
     * 在遍历逐个加入元素时维护一个pre变量来记录当前数的前一个数，这样在找到第(len/2 + 1)个数跳出循环后若总长为偶数则pre记录的就是中间偏左的数，利用
     * pre和cur可以求出中位数，如果总长为奇数则中位数直接为cur。
     *
     * 2. 如何处理数组越界问题：并没有像合并两个有序链表那样只有当两个子数组都为空时才在循环内添加元素，然后跳出循环继续在未完的数组中添加，这样在循环外
     * 还需要判断哪个数组结束了，再加上奇偶的情况，代码很繁琐。因此这里当一个数组提前遍历完了但是还没有找到mid的话，仍然在循环中添加，只是要修改下if条件。
     *
     */
    // // 时间复杂度：O(m+n)，不符合题目要求
    // // 时间复杂度：O(1)
    // public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    //     int m = nums1.length, n = nums2.length;
    //     int len = m + n;    // 总共的元素个数
    //     int mid = len / 2;  // 若len为奇数则mid位置（从0开始）就为中位数，若为偶数则mid为中间偏右那个
    //     int pre = -1, cur = -1; // 记录当前循环加入的元素以及前一次循环加入的元素
    //     int p = 0, q = 0;   // nums1, nums2的遍历指针
    //     int count = 0;  // 已经加入过的元素数量，最后需要达到 mid + 1
    //
    //     while (count <=  mid) {
    //         pre = cur;  // 即将加入新元素，将pre更新为cur
    //
    //         // 在本题里p、q不可能同时遍历完
    //         if ((p < m) && (q >= n || nums1[p] <= nums2[q])) {   // 当p没遍历完 且 q遍历完或者q没遍历完但是p指向的更小
    //             cur = nums1[p++];
    //         } else {    // 其他情况添加nums2[q]
    //             cur = nums2[q++];
    //         }
    //         count++;
    //     }
    //
    //     if (len % 2 == 0) { // 总长度为偶数，最后跳出循环的cur就是中间偏右的，pre就是中间偏左的
    //         return (pre + cur) / 2.0;
    //     } else {    // 总长度为偶数，最后跳出循环的cur就是中位数
    //         return cur;
    //     }
    // }




    // 求中位数就相当于求第k小的数，k为中间的一或两个数
    // 时间复杂度：O(log(m+n))，其中m,n为两个数组的长度。分析：初始k=(m+n)/2，每次排除k/2，k/4，k/8...个元素，所以时间复杂度是O(log k)，也就是O(log(m+n))
    // 空间复杂度：O(1)。分析：虽然我们用到了递归，但是可以看到这个递归属于尾递归，编译器不需要不停地堆栈，所以空间复杂度为 O(1)。
    // 尾递归：在递归过程中最后一步执行递归函数的步骤，并且在函数结束前不再需要执行任何其他计算或操作，只有一个栈帧被不断的更新，避免了不必要的调用栈帧的创建，并使得递归算法的空间复杂度降低到 O(1)。
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int mid = (m + n) / 2;
        if ((m + n) % 2 == 0) {
            return (getKth(nums1, 0, m-1, nums2, 0, n-1, mid) + getKth(nums1, 0, m-1, nums2, 0, n-1, mid+1)) / 2.0;
        } else {
            return getKth(nums1, 0, m-1, nums2, 0, n-1, mid+1);
        }
    }


    /**
     * 更普遍的一个问题：求两个有序数组的第k小的数
     * nums1，nums2都为升序序列，当前的两个子数组为：nums1[l1]~nums1[r1]和nums2[l2]~nums1[r2]
     * @param nums1
     * @param l1
     * @param r1
     * @param nums2
     * @param l2
     * @param r2
     * @param k
     * @return
     */
    // 求num1和num2中第k小的数
    public int getKth(int[] nums1, int l1, int r1, int[] nums2, int l2, int r2, int k) {
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;
        if (len1 > len2) {
            return getKth(nums2, l2, r2, nums1, l1, r1, k); // 保证执行下面的逻辑时一定是nums1的部分更短，要是有数组先排除完则一定是nums1
        }

        // 递归终止条件：有数组已经排除完或者k=1
        // nums1已经排除完
        if (len1 == 0) {
            return nums2[l2 + k - 1];    // 剩下的直接在nums2中找第k小的即可
        }
        if (k == 1) {   // 即找剩下两个数组最小的数，取两个头部较小的即可
            return  Math.min(nums1[l1], nums2[l2]);
        }

        int halfK = k / 2;  // 检查每个数组的第 k/2 个数
        int p1, p2; // 本轮需要比较nums1[p1]和nums2[p2]的大小
        if (len1 < halfK) {
            p1 = r1;    // nums后面的部分长度已经不足 k/2，则p1指向末尾
        } else {
            p1 = l1 + halfK - 1;    // 否则指向从l1开始的第 k/2个
        }

        // 因为nums1的部分较短，因此只有nums1的部分长度可能会不足 k/2
        p2 = l2 + halfK - 1;

        if (nums1[p1] < nums2[p2]) {    // nums1[p1]不可能是第k小的数（最大是第(k-1)小的数）
            return getKth(nums1, p1 + 1, r1, nums2, l2, r2, k-(p1-l1+1)); // 将nums1[l]到nums[p1]共p1-l1+1个数排除，找剩下两个数组第(k-(p1-l1+1))小的数
        } else {    // nums1[p1] >= nums2[p2] 相等删哪边都可以
            return getKth(nums1, l1, r1, nums2, p2 + 1, r2, k-(p2-l2+1));
        }
    }
}
