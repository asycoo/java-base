package phase2.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实操 2.2 - 用 Stream 重构词频 Top 10
 *
 * 要求：
 * - 逻辑与 Day3 WordFrequency 相同
 * - 必须用 Stream API（允许在 collect 之前使用 stream 链）
 * - 禁止传统 for 循环处理词频业务
 */
public class WordFrequencyStream {

    public static List<Map.Entry<String, Integer>> top10(String text) {
        // TODO: split → 转小写 → groupingBy 计数 → 排序 → limit 10
        return Arrays.stream(text.split("\\s+"))
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(10)
            .collect(Collectors.toList());
        // throw new UnsupportedOperationException("TODO: 实现 top10");
    }

    public static void main(String[] args) {
        String text = """
                Java is great and Java is fun. Java java JAVA.
                Python is good but Java is better for enterprise.
                """;

        List<Map.Entry<String, Integer>> result = top10(text);
        if (result.isEmpty() || !result.get(0).getKey().equals("java")) {
            throw new AssertionError("最高频应为 java");
        }

        System.out.println("Top 10 词频：");
        result.forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
        System.out.println("WordFrequencyStream: 全部通过 ✓");
    }
}
