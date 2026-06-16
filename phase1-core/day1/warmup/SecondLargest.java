package day1.warmup;

/**
 * 实操 1.1 - 找出 int 数组中第二大的数
 *
 * 要求：
 * - 数组至少有两个不同元素，否则抛 IllegalArgumentException
 * - 不允许先排序整个数组（练习 O(n) 扫描）
 *
 * 示例：findSecondLargest(new int[]{3, 1, 4, 4, 5}) → 4
 */
public class SecondLargest {

    public static int findSecondLargest(int[] nums) {
        if (nums == null || nums.length < 2) throw new IllegalArgumentException("数组至少有两个不同元素");
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > max) {
                secondMax = max;
                max = num;
            } else if (num > secondMax && num != max) {
                secondMax = num;
            }
        }
        return secondMax;
        // TODO: 一次遍历找出第二大（注意重复最大值的情况）
        // throw new UnsupportedOperationException("TODO: 实现 findSecondLargest 方法");
    }

    public static void main(String[] args) {
        check(4, findSecondLargest(new int[]{3, 1, 4, 4, 5}));
        check(4, findSecondLargest(new int[]{5, 5, 4}));
        check(-2, findSecondLargest(new int[]{-1, -2, -3}));

        try {
            findSecondLargest(new int[]{1});
            throw new AssertionError("应抛出 IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }

        System.out.println("SecondLargest: 全部通过 ✓");
    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
