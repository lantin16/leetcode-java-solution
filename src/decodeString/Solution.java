package decodeString;

import java.util.Deque;
import java.util.LinkedList;

/**
 * No.394 字符串解码
 *
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 */

public class Solution {


    /**
     * StringBuilder对于处理经常需要修改字符串的操作非常合适：
     * 1. StringBuilder中字符串是以字符数组的形式存储的，因此sb.charAt(idx)返回的是此序列中指定索引处的char值而不是String
     * 2. StringBuilder可以直接追加另一个StringBuilder，合并非常方便
     * 3. StringBuilder既可以在尾部追加（append），也可以在指定位置插入（insert(idx, s)），非常放方便
     */

    // // 栈 + StringBuilder
    // // 时间复杂度：O(l)，其中l为s解码后的长度。分析：除了遍历一次原字符串 sss，我们还需要将解码后的字符串中的每个字符都入栈，并最终拼接进答案中。
    // // 空间复杂度：O(l)，其中l为s解码后的长度。分析：栈中元素最多有l个（包括了解码后的重复元素），StringBuilder长度最多需要l。
    // public String decodeString(String s) {
    //     StringBuilder sb = new StringBuilder(); // 用来存储最终的字符串
    //     Deque<Character> stk = new LinkedList<>();
    //
    //     for (char c : s.toCharArray()) {
    //         if (c != ']') { // 除了右括号全部入栈
    //             stk.push(c);
    //         } else {    // 如果是右括号则出栈将最里面的一个重复字符串重复后再入栈
    //             StringBuilder dupSb = new StringBuilder();  // 用于存放重复指定次数后的字符串
    //             StringBuilder tmpSb = new StringBuilder();  // 用于存放需要重复的字符串
    //             while (stk.peek() >= 'a' && stk.peek() <= 'z') {
    //                 tmpSb.insert(0, stk.pop());
    //             }
    //
    //             // 下一个一定是左括号
    //             stk.pop();  // 将左括号弹出
    //             // 接着的应该是数字
    //             int times = 0;  // 重复次数
    //             int base = 1;   // 倍数
    //             while (!stk.isEmpty() && stk.peek() >= '0' && stk.peek() <= '9') {
    //                 times += (stk.pop() - '0') * base;
    //                 base *= 10;
    //             }
    //
    //             // 将tmpSb中的字符串重复追加到dupSb times次
    //             for (int i = 0; i < times; i++) {
    //                 dupSb.append(tmpSb);
    //             }
    //
    //             // 将重复后的字符串入栈
    //             for (int k = 0; k < dupSb.length(); k++) {
    //                 stk.push(dupSb.charAt(k));  // StringBuilder中字符串是以字符数组的形式存储的，因此sb.charAt(idx)返回的是此序列中指定索引处的char值而不是String。
    //             }
    //         }
    //     }
    //
    //     // 依次出栈，将元素头插到sb中，这样最后得到的就是解码好的正序字符串
    //     while (!stk.isEmpty()) {
    //         sb.insert(0, stk.pop());
    //     }
    //     return sb.toString();
    // }



    // 递归
    // 总体思路与辅助栈法一致，不同点在于将 [ 和 ] 分别作为递归的开启与终止条件
    // 复杂度同上
    public String decodeString(String s) {
        return decode(s, 0)[0];
    }

    /**
     * 当 s[i] == ']' 时，返回当前括号内记录的 res 字符串与 ] 的索引 i （更新上层递归指针位置）；
     * 当 s[i] == '[' 时，开启新一层递归，记录此 [...] 内字符串 tmp 和递归后的最新索引 i，并执行 res + multi * tmp 拼接字符串。
     * 遍历完毕后返回 res。
     * @param s
     * @param start 本层开始搜索的下标
     * @return 返回的String数组有两个元素，前一个是本层递归解码后的字符串，后一个是本层递归结束时的下标，便于外层直接从结束下标的后一个开始继续检查
     */
    public String[] decode(String s, int start) {
        StringBuilder sb = new StringBuilder();
        int multi = 0;
        while (start < s.length()) {
            char cur = s.charAt(start);
            if (cur >= '0' && cur <= '9') {
                multi = multi * 10 + (cur - '0');
            } else if (cur == '[') {    // 遇到左括号则进入递归
                String[] innerRes = decode(s, start + 1);   // 嵌套内部括号解析出的结果以及结束的下标
                start = Integer.parseInt(innerRes[1]);
                // 将这部分嵌套括号的结果重复multi次添加到res中
                while (multi > 0) {
                    sb.append(innerRes[0]);
                    multi--;
                }
            } else if (cur == ']') {    // 遇到右括号则本层递归中止
                return new String[]{sb.toString(), String.valueOf(start)};
            } else {    // 遇到字母则添加到sb中
                sb.append(cur);
            }
            start++;
        }
        return new String[]{sb.toString()};   // 最外层在这里返回

    }
}
