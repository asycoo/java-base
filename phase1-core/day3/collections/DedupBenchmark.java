package day3.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 实操 3.1 - Set vs List 去重性能对比
 *
 * 要求：
 * - 生成 10 万个 1~1000 的随机整数（大量重复）
 * - 分别用 List.contains 和 HashSet 去重，打印耗时 ms
 * - 体会 O(n²) vs O(n)
 */
public class DedupBenchmark {

    private static final int SIZE = 100_000;
    private static final int MAX_VALUE = 1000;

    public static List<Integer> dedupWithList(List<Integer> source) {
        // TODO: 遍历 source，若 result 不包含则 add（慢）
        List<Integer> result = new ArrayList<>();
        for (Integer num : source) {
            if (!result.contains(num)) {
                result.add(num);
            }
        }
        return result;
        // throw new UnsupportedOperationException("TODO: 实现 dedupWithList");
    }

    public static List<Integer> dedupWithSet(List<Integer> source) {
        // TODO: 放入 HashSet 再转 List（快）
        Set<Integer> result = new HashSet<>(source);
        return new ArrayList<>(result);
        // throw new UnsupportedOperationException("TODO: 实现 dedupWithSet");
    }

    public static void main(String[] args) {
        Random random = new Random(42);
        List<Integer> data = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            data.add(random.nextInt(MAX_VALUE) + 1);
        }

        long startSet = System.currentTimeMillis();
        List<Integer> setResult = dedupWithSet(data);
        long setMs = System.currentTimeMillis() - startSet;

        long startList = System.currentTimeMillis();
        List<Integer> listResult = dedupWithList(data);
        long listMs = System.currentTimeMillis() - startList;

        if (setResult.size() != listResult.size()) {
            throw new AssertionError("两种方式去重数量应一致");
        }

        System.out.printf("HashSet 去重: %d ms, 结果 %d 个%n", setMs, setResult.size());
        System.out.printf("List.contains 去重: %d ms, 结果 %d 个%n", listMs, listResult.size());
        System.out.println("DedupBenchmark: 完成 ✓");
    }
}
