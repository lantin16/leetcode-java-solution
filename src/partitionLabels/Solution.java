package partitionLabels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * No.763 划分字母区间
 *
 * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
 * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
 * 返回一个表示每个字符串片段的长度的列表。
 *
 */

public class Solution {

    /**
     * 用哈希表记录每个字母最后一次出现的位置
     * - 首尾相同字母的一段一定在同一段内
     * - 有交叉的则需要合并成同一段，如 abxxxaxb，由于axxxxa和bxxxxxb这两段有交叉，因此它们最终会合并成一大段
     * @param s
     * @return
     */
    // 时间复杂度：O(n)。分析：一遍找出每个字母最后出现的位置，一遍确定分段结果，遍历两次
    // 空间复杂度：O(n)。分析：哈希表大小最大为26，常数量级，用chs记录字符串s转字符数组，便于后续访问字母更快。
    // public List<Integer> partitionLabels(String s) {
    //     List<Integer> res = new ArrayList<>();
    //     Map<Character, Integer> map = new HashMap<>(26); // 记录各字母最后一次出现的位置
    //     int n = s.length();
    //     char[] chs = s.toCharArray();
    //     for(int i = 0; i < n; i++) {
    //         map.put(chs[i], i);
    //     }
    //
    //     int begin = 0;
    //     while(begin < n) {
    //         int end = map.get(chs[begin]);
    //         for(int j = begin + 1; j < end; j++) {  // 检查begin到end之间的字母，如果它们的右边界更远（比end更右），则要更新end，直至检查到end为一段结束
    //             end = Math.max(map.get(chs[j]), end);
    //         }
    //         res.add(end - begin + 1);
    //         begin = end + 1;
    //     }
    //     return res;
    // }


    /**
     * 改进：用数组代替哈希表存储字母最后出现的位置
     * 用数组比用哈希表时间性能更好，先转字符数组再访问比直接用s.charAt(i)访问时间性能更好
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] last = new int[26];
        int n = s.length();
        char[] chs = s.toCharArray();
        for(int i = 0; i < n; i++) {
            last[chs[i] - 'a'] = i;
        }

        int begin = 0;
        while(begin < n) {
            int end = last[chs[begin] - 'a'];
            for(int j = begin + 1; j < end; j++) {  // 检查begin到end之间的字母，如果它们的右边界更远（比end更右），则要更新end，直至检查到end为一段结束
                end = Math.max(last[chs[j] - 'a'], end);
            }
            res.add(end - begin + 1);
            begin = end + 1;
        }
        return res;
    }

}
