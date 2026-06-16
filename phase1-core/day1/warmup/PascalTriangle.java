package day1.warmup;

/**
 * 实操 1.1 - 打印杨辉三角
 *
 * 要求：
 * - printTriangle(10) 打印 10 行
 * - 每行数字用空格分隔，尽量对齐（不要求完美居中）
 *
 * 示例前 4 行：
 *   1
 *   1 1
 *   1 2 1
 *   1 3 3 1
 */
public class PascalTriangle {

    /**
     * 返回第 row 行（0-indexed）的值，如 row=2 → [1, 2, 1]
     */
    public static int[] getRow(int row) {
        int[] result = new int[row + 1];
        result[0] = 1;
        for (int i = 1; i <= row; i++) {
            result[i] = result[i - 1] * (row - i + 1) / i;
        }
        return result;
        // TODO: 计算杨辉三角的第 row 行
        // throw new UnsupportedOperationException("TODO: 实现 getRow 方法");
    }

    public static void printTriangle(int rows) {
        for (int i = 0; i < rows; i++) {
            int[] row = getRow(i);
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();
        }
        // TODO: 循环调用 getRow 并打印
        // throw new UnsupportedOperationException("TODO: 实现 printTriangle 方法");
    }

    public static void main(String[] args) {
        int[] row2 = getRow(2);
        check(new int[]{1, 2, 1}, row2);

        int[] row4 = getRow(4);
        check(new int[]{1, 4, 6, 4, 1}, row4);

        System.out.println("--- 杨辉三角 10 行 ---");
        printTriangle(10);
        System.out.println("PascalTriangle: 全部通过 ✓");
    }

    private static void check(int[] expected, int[] actual) {
        if (expected.length != actual.length) {
            throw new AssertionError("长度不符");
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new AssertionError("期望 " + java.util.Arrays.toString(expected)
                        + "，实际 " + java.util.Arrays.toString(actual));
            }
        }
    }
}
