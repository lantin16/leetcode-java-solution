package findDuplicate;

import java.util.HashSet;
import java.util.Set;

/**
 * No.287 寻找重复数
 *
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 *
 * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
 */

public class Solution {

    /**
     * 用set判断是否重复
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)
    // 空间复杂度：O(n)
    // public int findDuplicate(int[] nums) {
    //     Set<Integer> set = new HashSet<>();
    //     int res = 0;
    //     for(int num : nums) {
    //         if(set.contains(num)) {
    //             res = num;
    //             break;
    //         }
    //         set.add(num);
    //     }
    //     return res;
    // }


    /**
     * 快慢指针，Floyd判圈算法
     * 将数组建成链表，对于nums[i]，当前节点的值为1，其next节点为nums[i]，值相同的节点认为是同一节点，即 i -> nums[i]
     * 如nums=[1,3,4,2,2]存在如下关系 ：0->1, 1->3, 2->4, 3->2, 4->2
     * 建成链表为 0 -> 1 -> 3 -> 2 -> 4 -> 2 -> 4 -> 2 ... 在2和4之间出现环
     *
     * 结论：数组中如果有重复的数，那么就会产生多对一的映射（多个下标映射到一个数），这样，形成的链表就一定会有环路了
     * 综上：
     * 1.数组中有一个重复的整数 <==> 链表中存在环
     * 2.找到数组中的重复整数 <==> 找到链表的环入口
     *
     * 这样问题就转化为No.142求环形链表的环的入口。n+1个数的下标范围为[0, n]，而nums的元素范围为[1, n]，因此用元素作为下标不会越界
     * 链表是通过next指针找到下一个节点，这里则根据 i -> nums[i]的映射关系通过nums[now]找到下一个数
     *
     * 下标从0开始，而数的范围从1开始，因此不会有数指向头节点0，即不可能一开始就在环内
     *
     * @param nums
     * @return
     */
    // 时间复杂度：O(n)。分析：Floyd判圈算法的时间复杂度为线性的时间复杂度。
    // 时间复杂度：O(1)
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        int n = nums.length;

        // 快指针一次走两步，慢指针一次走一步，直至相遇
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while(nums[slow] != nums[fast]);

        // 第一次相遇后，将慢指针移回起点，然后快慢指针每次同时走一步，第二次相遇就在环的入口处
        // 关于为什么这样走第二次相遇点为环的入口，证明参考https://leetcode.cn/problems/find-the-duplicate-number/solutions/261119/xun-zhao-zhong-fu-shu-by-leetcode-solution
        slow = 0;
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
