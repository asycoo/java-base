package day3.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实操 3.1 - 词频统计 Top 10
 *
 * 要求：
 * - 忽略大小写，按单词切分（简单按空格 split 即可）
 * - 返回出现次数最多的前 10 个词（Map 计数 + 排序）
 *
 * 示例："Java is great and Java is fun" → java:2, is:2, ...
 */
public class WordFrequency {

    public static List<Map.Entry<String, Integer>> top10(String text) {
        // TODO: 1. 转小写、split  2. Map 计数  3. 排序取 Top 10
        text = text.toLowerCase();
        String[] words = text.split("\\s+");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCount.entrySet());
        sortedWords.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return new ArrayList<>(sortedWords.subList(0, Math.min(10, sortedWords.size())));
    }

    public static void main(String[] args) {
        String text = """
                Java is great and Java is fun. Java java JAVA.
                Python is good but Java is better for enterprise.
                Java streams are powerful and Java collections are essential.
                """;

        List<Map.Entry<String, Integer>> result = top10(text);
        if (result.isEmpty()) {
            throw new AssertionError("结果不应为空");
        }
        if (result.size() > 10) {
            throw new AssertionError("最多 10 个");
        }
        if (!result.get(0).getKey().equals("java")) {
            throw new AssertionError("最高频应为 java，实际 " + result.get(0).getKey());
        }

        System.out.println("Top 10 词频：");
        for (Map.Entry<String, Integer> entry : result) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("WordFrequency: 全部通过 ✓");
    }
}
