package minWindow;

import java.util.HashMap;

/**
 * leetcode No.76 最小覆盖子串
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 */

public class Solution {

    // 双指针实现
    public static String minWindow(String s, String t) {
        int m = s.length(), n = t.length();

        if (m < n) {
            return "";
        }

        HashMap<Character, Integer> countMap = new HashMap<>(); // 记录t中字符及出现次数
        for (char ch : t.toCharArray()) {
            int cnt = countMap.getOrDefault(ch, 0); // 获取字符ch对应的出现次数，如果不存在则返回默认值0
            countMap.put(ch, cnt+1);
        }

        int resL = -1, resR = -1; // 当前符合条件的已知最小子串的首尾下标（-1代表未找到）
        int l = 0, r = 0;   // 遍历字符串的左右指针
        int lastL = -1;

        for (l = 0; l <= m-n; l++) {   // 左指针
            // 左指针找到下一个在子串中的元素
            if (countMap.containsKey(s.charAt(l))) {
                boolean found = false;  // 以l开头的子串中是否有符合条件的
                if (lastL == -1) {  // 如果之前还没有找到符合条件的子串
                    for (r = l; r < m; r++) {
                        if (countMap.containsKey(s.charAt(r))) {    // 若右指针指向的字符在t中
                            countMap.put(s.charAt(r),countMap.get(s.charAt(r))-1);  // 将countMap对应value减一
                            if (r - l + 1 >= n && findSubstring(countMap)) {   // 只有当前子串长度不小于t的长度时才可能符合条件
                                resL = l;
                                resR = r;
                                found = true;
                                break;  // 以l位置开头的最小子串已经找到了，r不需要继续右移
                            }
                        }
                    }
                } else {    // 如果之前有找到符合条件的子串，则尝试以现在的l为头
                    char lastLeftChar = s.charAt(lastL);
                    countMap.put(lastLeftChar, countMap.get(lastLeftChar)+1);   // 将之前的开头l字符移出子串
                    if (countMap.get(lastLeftChar) <= 0) {    // 之前的l所指的字符在之前的符合条件的子串中是冗余的，移除后的子串仍符合条件
                        if (resR-resL > r-l) {  // 如果当前子串更短
                            resR = r;
                            resL = l;
                        }
                        found = true;
                    } else {    // 移除之前的l所指的字符会使得子串不再满足条件，因此r需要继续右移找到下一个该字符
                        r++;
                        while (r < m) {
                            if (countMap.containsKey(s.charAt(r))) {
                                countMap.put(s.charAt(r), countMap.get(s.charAt(r))-1);
                                if (s.charAt(r) == lastLeftChar) {
                                    found = true;
                                    if (resR-resL > r-l) {  // 如果当前子串更短
                                        resR = r;
                                        resL = l;
                                    }
                                    break;
                                }
                            }
                            r++;
                        }
                    }
                }

                if (found) {
                    lastL = l;
                } else {    // 如果以l开头的子串没有符合条件的，则在l右边的子串都不可能符合条件，左指针不用继续右移了
                    break;
                }
            }
        }
        if (lastL != -1) {
            return s.substring(resL, resR+1);
        } else {
            return "";
        }
    }

    // 判断当前子串是否满足题目条件，即判断map的元素是否都<=0
    public static boolean findSubstring(HashMap<Character, Integer> map) {
        for (Integer value : map.values()) {
            if (value > 0) {
                return false;   // 该字串尚不符合条件
            }
        }
        return true;    // 找到符合条件的子串
    }


    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindow(s, t));    // "BANC"
    }
}