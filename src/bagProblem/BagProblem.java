package bagProblem;

import java.util.Arrays;

public class BagProblem {
    public static void main(String[] args) {
        int[] weight = {1,3,4};
        int[] value = {15,20,30};
        int bagSize = 4;
        weightBagProblem(weight,value,bagSize);
    }


    /**
     * 01背包问题
     * 动态规划 + 二维dp数组
     * @param weight 物品的重量
     * @param value 物品的价值
     * @param bagSize 背包的容量
     */
    // public static void weightBagProblem(int[] weight, int[] value, int bagSize) {
    //     int goods = weight.length;  // 物品的数量
    //     // 1. dp[i][j]表示从下标[0,..,i]的物品中选择放到容量为j的背包里的最大价值
    //     int[][] dp = new int[goods][bagSize + 1];
    //
    //     // 2. 递推公式：dp[i][j] = max{dp[i-1][j], dp[i-1][j-weight[i]] + value[i]]}
    //     // dp[i][j]有两种来源：下标的i的物品不放进去，或者容量j空间足够把i物品放进去，剩下就从0到i-1的物品中选择放入容量为j-weight[i]的背包
    //
    //     // 3. 初始化
    //     // - j=0时，什么物品都放不进去，dp[i][0]都初始化为0
    //     // - 由于求dp[i][j]要用到dp[i-1][j]，因此i=0的也需要初始化，而dp[0][j]取决于0号物品是否能放入。因此将j < weight[0]的初始化为0，j>= weight[0]的初始化为value[0]
    //     // - 从递推公式看出，dp[i][j] 是由左上方数值推导出来了，那么剩余下标初始为什么数值都可以，因为都会被覆盖，这里就用默认的0。
    //     // 当然，由于数组元素默认初始化为了0，因此有些工作不用做
    //     for (int j = weight[0]; j <= bagSize; j++) {
    //         dp[0][j] = value[0];
    //     }
    //
    //     // 4. 遍历顺序，两种顺序都可以，但先遍历物品再遍历背包容量更好理解
    //     for (int i = 1; i < goods; i++) {
    //         for (int j = 1; j <= bagSize ; j++) {
    //             if (j < weight[i]) {    // 只装i物品都装不下，只可能为dp[i-1][j]
    //                 dp[i][j] = dp[i-1][j];
    //             } else {    // 如果能装下i，再比较装i和不装i哪个价值更高
    //                 dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight[i]] + value[i]);
    //             }
    //         }
    //     }
    //
    //     // 5. 打印dp数组
    //     for (int i = 0; i < goods; i++) {
    //         for (int j = 0; j <= bagSize; j++) {
    //             System.out.print(dp[i][j] + " ");
    //         }
    //         System.out.println("\n");
    //     }
    //
    //     // 最后dp[goods-1][bagSize]即为所求
    // }


    /**
     * 01背包问题
     * 动态规划 + 一维滚动数组，优化空间复杂度
     * 代码更简洁，空间复杂度降低了一个量级
     * 需要保证遍历j时，j之后的都是本轮i计算的，j及之前的都是上一轮i的
     * @param weight 物品的重量
     * @param value 物品的价值
     * @param bagSize 背包的容量
     */
    public static void weightBagProblem(int[] weight, int[] value, int bagSize) {
        int goods = weight.length;  // 物品的数量
        // 1. dp[j]表示容量为j的背包，所背的物品价值可以最大为dp[j]
        int[] dp = new int[bagSize + 1];

        // 2. 递推公式：dp[j] = max{dp[j], dp[j-weight[i]] + value[i]]}
        // dp[i][j]有两种来源：下标的i的物品不放进去，或者容量j空间足够把i物品放进去，剩下就从0到i-1的物品中选择放入容量为j-weight[i]的背包

        // 3. 初始化
        // 全部初始化为0即可，下面遍历就从i=0开始遍历

        // 4. 遍历顺序，只能先遍历物品再遍历背包容量（如果先遍历背包则对每个j从上往下计算各个dp[j]时都只会放入一个物品）
        for (int i = 0; i < goods; i++) {   // 注意dp数组全初始化为0这里的i就得从0开始
            // j得倒序遍历，根据二维dp数组的递推公式可知，计算dp[i][j]实际要用到上一行左上角的数据，而如果j正序遍历，那么滚动数组用到的左边的dp[j-weight[i]]其实是这一行新计算覆盖的，这会导致某些物品被重复放入，不符合01背包问题
            // 因此，j只能倒序遍历，从右往左覆盖，保证当计算dp[j]时用到的左边的dp[j-weight[i]]仍然是上一行未覆盖的结果。而计算dp[j]时的自己在赋值前其实也是上一行的dp[j]，这一点正序倒序遍历都满足
            for (int j = bagSize; j >= weight[i] ; j--) {  // 注意：这里j向左遍历到weight[i]就可以了，容量再小i就放不进去了，此时保持上一行的dp[j]即可
                dp[j] = Math.max(dp[j], dp[j-weight[i]] + value[i]);
            }
        }

        // 5. 打印dp数组
        for (int j = 0; j <= bagSize; j++) {
            System.out.print(dp[j] + " ");
        }

        // 最后dp[bagSize]即为所求
    }



    /**
     * 完全背包问题，每种物品的数量无限，可重复选择
     * 动态规划 + 一维滚动数组
     * 需要保证遍历j时，j之前的都是本轮i计算的，j及之后的都是上一轮i的
     * 完全背包问题相较于01背包问题：
     * 1. 遍历背包容量时得顺序遍历，因为一种物品可重复添加多次
     * 2. 先遍历物品还是先遍历背包都可以，因为dp[j] 是根据 下标j及之前所对应的dp[j]计算出来的。 只要保证下标j之前的dp[j]都是经过计算的就可以了。
     * @param weight 物品的重量
     * @param value 物品的价值
     * @param bagSize 背包的容量
     */
    public static void CompletePack(int[] weight, int[] value, int bagSize) {
        int goods = weight.length;  // 物品的数量
        // 1. dp[j]表示容量为j的背包，所背的物品价值可以最大为dp[j]
        int[] dp = new int[bagSize + 1];

        // 2. 递推公式：
        // 二维的形式：dp[i][j] = max{dp[i-1][j], dp[i][j-weight[i]] + value[i]]}，注意这里第二项是dp[i][j-weight[i]] + value[i]]而不是01背包的dp[i-1][j-weight[i]] + value[i]]
        // dp[i][j]有两种来源：下标的i的物品不放进去，或者容量j空间足够把i物品放进去，剩下就从0到i的物品中选择放入容量为j-weight[i]的背包（即使本次放入i，前面j-weight[i]的空间也可能有i）
        // 一维的形式：dp[j] = max{dp[j], dp[j-weight[i]] + value[i]]}

        // 3. 初始化
        // 全部初始化为0即可，下面遍历就从i=0开始遍历

        // 4. 遍历顺序，先遍历物品再遍历背包容量或先遍历背包容量再遍历物品都可以
        // 如先遍历物品再遍历背包容量
        for (int i = 0; i < goods; i++) {   // 注意dp数组全初始化为0这里的i就得从0开始
            // j只能顺序遍历，因为一种物品可重复添加多次
            for (int j = weight[i]; j <= bagSize; j++) {  // 注意：这里j从weight[i]开始遍历，j<weight[i]时物品i就放不进去了，此时保持上一行的dp[j]即可
                dp[j] = Math.max(dp[j], dp[j-weight[i]] + value[i]);
            }
        }


//        // 也可以先遍历背包容量再遍历物品
//        for (int j = 1; j <= bagSize; j++) {    // 这里j从1开始即可，因为j=0即背包容量为0时是放不进任何物品的，因此第0列的都是0
//            for (int i = 0; i < goods; i++) {
//                if (j >= weight[i]) {
//                    dp[j] = Math.max(dp[j], dp[j-weight[i]] + value[i]);
//                }
//            }
//        }


        // 5. 打印dp数组
        for (int j = 0; j <= bagSize; j++) {
            System.out.print(dp[j] + " ");
        }

        // 最后dp[bagSize]即为所求
    }

}
