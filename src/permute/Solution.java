package permute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * No.46 全排列
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 */

public class Solution {

    // List<List<Integer>> res = new ArrayList<>();
    // public List<List<Integer>> permute(int[] nums) {
    //     // 以nums每个数为起点开始一次dfs
    //     for (int i = 0; i < nums.length; i++) {
    //         dfs(new ArrayList<>(), new int[nums.length], nums, i); // 以nums[i]为起点
    //     }
    //     return res;
    // }
    //
    // public void dfs(List<Integer> permutation, int[] visited, int[] nums, int i) {
    //     if (visited[i] == 1) {  // 访问过自己了
    //         return;
    //     }
    //
    //     // 访问当前数字
    //     visited[i] = 1;
    //     permutation.add(nums[i]);
    //
    //     // 尝试从头开始寻找下一个搜索数字
    //     for (int k = 0; k < nums.length; k++) {
    //         dfs(permutation, visited, nums, k);
    //     }
    //
    //     // 找到一个全排列
    //     if (permutation.size() == nums.length) {
    //         // 注意，这里不能直接向res添加permutation，因为这只是将引用加进去，后面permutation会在回溯的时候将元素都删除，最后res中全是空list
    //         // 虽然利用构造方法拷贝也是浅拷贝，只是将原始 ArrayList 中的元素引用也添加到新list中，而不会创建元素的副本，但是在这里够用了。
    //         res.add(new ArrayList<>(permutation));
    //     }
    //
    //     // 返回前将当前结点退出序列并置为未访问
    //     visited[i] = 0;
    //     permutation.remove(permutation.size()-1);
    // }



    // 另一种写法，可以省掉标记数组visited
    // 时间复杂度：O(n×n!)，其中n为nums元素个数。分析：排列组合有 n！种方案，O(n!)，每个方案复制到res中又要花费O(n)遍历，相乘得时间复杂度为 O(n×n!)
    // 空间复杂度：O(n)。分析：递归深度最大为n
    List<Integer> permutation = new ArrayList<>();
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        // 首先将nums全部填入List
        for (int num : nums) {
            permutation.add(num);
        }

        // 每次确定全排序的一个数（设其在排序中的下标为i），那么将该数交换到permutation的i位置，这样从前往后，前面的都是已经确定的数，要找未确定的数直接从后面找就行
        dfs(0);
        return res;
    }

    // 正在确定permutation的下标i位置的数，i以前的都是确定下来的，i及以后的都是未确定的
    public void dfs(int i) {
        if (i == permutation.size()-1) {    // 一个全排序已经确定
            res.add(new ArrayList<>(permutation));
            return;
        }

        // 从permutation的未确定部分（i及之后）找一个数交换到i的位置，代表这个位置确定它
        for (int j = i; j < permutation.size(); j++) {
            Collections.swap(permutation, i, j);    // 将j位置的元素交换到i位置，代表i位置的元素确定成它
            dfs(i + 1);   // 准备确定i+1位置的数
            Collections.swap(permutation, i, j); // 尝试完这个分支后交换回去（撤销操作），再尝试下一个数放到i位置
        }
    }



    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        System.out.println("l原来：" + l.toString());

        List<Integer> copy = new ArrayList<>(l);    // 其实利用构造方法也是浅拷贝，只是将原始 ArrayList 中的元素引用也添加到新list中，而不会创建元素的副本。
        System.out.println("copy原来：" + copy.toString());


        l.remove(0);
        System.out.println("l现在：" + l.toString());  // 2，3
        System.out.println("copy现在：" + copy.toString());// 1，2，3，看起来l的增删元素没有影响到copy，但是实际上1，2，3 Integer的地址还是原来l中的1，2，3
    }
}
