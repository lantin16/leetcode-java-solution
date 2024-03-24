package combinationSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * No.39 组合总和
 *
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 */

public class Solution {

    List<List<Integer>> res = new ArrayList<>();
    List<Integer> tmp = new ArrayList<>();  // 用来暂存已选择的数
    // 回溯
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);    // 先排序
        dfs(candidates, 0, target);
        return res;
    }

    /**
     * 深度优先遍历，不断尝试将candidates中数加入tmp，一旦找到满足和为target的组合就加入结果集合
     * @param candidates
     * @param i 当前要从candidates中取的数的下标
     * @param target 还需要达到的和，如最开始target为7，上一层选择了2，则本层只需要找到target为5的组合即可
     * @return 提前结束标志，返回true代表和已经>=target了，回溯到上一层可以不必再继续尝试后面的数了，因为后面的数更大，肯定不满足
     */
    public boolean dfs(int[] candidates, int i, int target) {
        if (target == 0) { // tmp中找到一组和为target的组合，加入结果集合
            res.add(new ArrayList<>(tmp));
            return true;
        } else if (target < 0) {   // 目前tmp的和已经超过了target，需要回退
            return true;
        }

        for (int k = i; k < candidates.length; k++) {   // 由于可以重复，因此继续从candidates[i]开始添加
            tmp.add(candidates[k]);
            boolean end = dfs(candidates, k, target - candidates[k]);   // 选择一个数就将其中target中减去，即为还需要的和的大小
            tmp.remove(tmp.size()-1);    // 撤销操作
            if (end) {  // 如果可以提前结束，即紧接着的下一层dfs直接返回true，那么接着在本层尝试后面的数也不会符合条件
                break;
            }
        }

        return false;   // 就算这一层可以提前结束，但这一层的上一层仍需要尝试后面的数

    }
}
