package letterCombinations;

import java.util.*;

/**
 * No.17 电话号码的字母组合
 * <p>
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 */

public class Solution {

    // class Num {
    //     int num;
    //     char[] letters; // 该数对应的字母
    //
    //     public Num(int num, char[] letters) {
    //         this.num = num;
    //         this.letters = letters;
    //     }
    // }
    //
    // List<String> res = new ArrayList<>();
    //
    // // 如何存储每个数字对应的字母？现在这样写很不优雅
    // // 是用String还是List暂存选好的字母？
    // // 这种写法虽能通过，但时间和空间的性能并不好
    // public List<String> letterCombinations(String digits) {
    //     if (digits.equals("")) {
    //         return new ArrayList<>();
    //     }
    //
    //     Num[] nums = new Num[10];
    //     nums[2] = new Num(2, new char[]{'a', 'b', 'c'});
    //     nums[3] = new Num(3, new char[]{'d', 'e', 'f'});
    //     nums[4] = new Num(4, new char[]{'g', 'h', 'i'});
    //     nums[5] = new Num(5, new char[]{'j', 'k', 'l'});
    //     nums[6] = new Num(6, new char[]{'m', 'n', 'o'});
    //     nums[7] = new Num(7, new char[]{'p', 'q', 'r', 's'});
    //     nums[8] = new Num(8, new char[]{'t', 'u', 'v'});
    //     nums[9] = new Num(9, new char[]{'w', 'x', 'y', 'z'});
    //
    //
    //     dfs(nums, digits, 0, "");
    //
    //     return res;
    // }
    //
    // /**
    //  *
    //  * @param digits
    //  * @param i 正在确定digits中的下标i的数字对应的字母
    //  * @param temp 暂存结果
    //  * @param nums 数字字母对应数组
    //  */
    // public void dfs(Num[] nums, String digits, int i, String temp) {
    //     if (i == digits.length()) {
    //         res.add(temp);
    //         return;
    //     }
    //
    //     int num = digits.charAt(i) - '0';   // 将digits的第i位的数字转为int类型
    //     for (char c : nums[num].letters) {
    //         temp += c;  // 拼接上这个数字可能对应的字母
    //         dfs(nums, digits, i + 1, temp);
    //         temp = temp.substring(0, temp.length()-1);  // 撤销本位添加的字母
    //     }
    // }


    List<String> res = new ArrayList<>();   // 存放最终结果

    // 回溯
    // 时间复杂度：O(3^m × 4^n)，其中 m 是输入中对应 3 个字母的数字个数（包括数字 2、3、4、5、6、8），n 是输入中对应 4 个字母的数字个数（包括数字 7、9），
    // m+n 是输入数字的总个数。分析：当输入包含 m 个对应 3 个字母的数字和 n 个对应 4 个字母的数字时，不同的字母组合一共有 3^m × 4^n 种。
    // 空间复杂度：O(m+n)。分析：除了返回值以外，空间复杂度主要取决于哈希表以及回溯过程中的递归调用层数，哈希表的大小与输入无关，可以看成常数，递归调用层数最大为 m+n。
    public List<String> letterCombinations(String digits) {
        if (digits.equals("")) {
            return new ArrayList<>();
        }

        // 首先使用 哈希表 存储每个数字对应的所有可能的字母
        // 双重花括号初始化 允许在创建集合类对象时进行初始化并添加元素
        // 外层的一对花括号定义了一个匿名内部类，并在其中添加了实例初始化块，用于执行集合的初始化操作。内层的一对花括号定义了实例初始化块，用于执行集合元素的添加操作。
        Map<Character, String> map = new HashMap<>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        // // 也可以用 字符串数组 存储每个数字对应的所有可能的字母。数组下标代表数字，元素代表该数字对应字母组成的字符串
        // // 找到digits第i位数字对应的字母可以这么写：numString[digits.charAt(i) - '0']
        // String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        // 回溯
        backtrack(map, digits, 0, new StringBuilder());

        return res;
    }

    /**
     * StringBuilder 是用于动态构建字符串的一种高效且灵活的方式，在需要频繁修改字符串内容时，使用 StringBuilder 可以提高代码的性能和可维护性。
     * @param map
     * @param digits
     * @param index 正在确定digits中的下标为index的数字对应的字母
     * @param combination 暂存已有的字母排列
     */
    public void backtrack(Map<Character, String> map, String digits, int index, StringBuilder combination) {
        if (index == digits.length()) {
            res.add(combination.toString());
            return;
        }

        // digits的第i位的数字对应的字母
        for (char c : map.get(digits.charAt(index)).toCharArray()) {
            combination.append(c);
            backtrack(map, digits, index + 1, combination);
            combination.deleteCharAt(combination.length() - 1);
        }
    }



    // // 队列法，性能拉跨
    // public List<String> letterCombinations(String digits) {
    //     if (digits.equals("")) {
    //         return new ArrayList<>();
    //     }
    //
    //     List<String> res = new ArrayList<>();   // 存放最终结果
    //     String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    //
    //     Queue<String> queue = new LinkedList<>();
    //     // 先将digits第一个数字对应所有的字母单独入队， 如第一个数字是2，则将"a","b","c"入队
    //     for (char c : numString[digits.charAt(0) - '0'].toCharArray()) {
    //         queue.offer(String.valueOf(c));
    //     }
    //
    //     int index = 1;  // 正在添加digits下标为index数字对应的字母
    //     while (!queue.isEmpty()) {
    //         int size = queue.size();    // 本轮要弹出的字符串个数，每个字符串长度为index
    //         List<String> strList = new ArrayList<>();
    //         for (int i = 0; i < size; i++) {
    //             strList.add(queue.poll());
    //         }
    //
    //         if (index == digits.length()) { // 如果该字符串已经符合结果，则加入res
    //             res.addAll(strList);
    //         } else {    // 如果字符串长度还不够，则添加完digits[index]对应的字母后继续入队
    //             for (String str : strList) {
    //                 for (char letter : numString[digits.charAt(index)-'0'].toCharArray()) {
    //                     queue.offer(str + letter);
    //                 }
    //             }
    //             index++;
    //         }
    //     }
    //     return res;
    // }

}
