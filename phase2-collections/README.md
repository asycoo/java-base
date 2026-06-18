# 阶段二：集合深入 + 泛型 + 现代 Java（Week 2）

> 阶段一已完成 ✓ 从这里进入「现代 Java」写法。

## 目标

- 理解泛型与类型擦除
- 手写 `SimpleArrayList`（理解集合底层）
- 掌握 Stream、Lambda、Optional
- 用 Stream 完成订单分析

## 学习顺序

### Day 1–2：泛型 + 手写 ArrayList


| 顺序  | 文件                                                                             | 任务                              |
| --- | ------------------------------------------------------------------------------ | ------------------------------- |
| 1   | [phase2/arraylist/SimpleArrayList.java](phase2/arraylist/SimpleArrayList.java) | 实现 `add/get/remove/size/expand` |
| 2   | 运行 [SimpleArrayListTest.java](phase2/arraylist/SimpleArrayListTest.java)       | 自测通过                            |


**前端对照：**

```java
SimpleArrayList<String> list = new SimpleArrayList<>();
// ≈ JS: const list = []; 但 Java 要声明元素类型
```

### Day 3–4：Stream + Lambda


| 顺序  | 文件                                                                               | 任务                           |
| --- | -------------------------------------------------------------------------------- | ---------------------------- |
| 3   | [phase2/stream/WordFrequencyStream.java](phase2/stream/WordFrequencyStream.java) | 用 Stream 重构 Day3 词频统计        |
| 4   | [phase2/stream/OrderAnalyzer.java](phase2/stream/OrderAnalyzer.java)             | 订单分析（纯 Stream，禁止 for 循环处理业务） |


**前端对照：**

```java
list.stream().filter(x -> x >= 60).map(...)
// ≈ list.filter(x => x >= 60).map(...)
```

### Day 5：源码阅读（笔记即可，不写代码）

阅读 JDK 17 `HashMap` 源码，重点：

- `put` / `get` 流程
- `resize` 扩容
- 链表 → 红黑树（阈值 8）
- 为什么线程不安全

在 [doc/Java完全掌握学习路线.md](../doc/Java完全掌握学习路线.md) 的「阶段二」笔记区记录。

## 运行方式

```bash
mvn compile
java -cp target/classes phase2.stream.WordFrequencyStream
java -cp target/classes phase2.arraylist.SimpleArrayListTest
```

> 项目为 Maven 工程，目录须与 package 一致：`phase2-collections/phase2/stream/` → `package phase2.stream;`

## 验收标准

- `SimpleArrayListTest` 全部通过
- 能解释：`List<String>` 运行时是什么类型？
- `OrderAnalyzer` 三个分析结果正确
- 能手写 5 个 Stream 操作：`filter` `map` `sorted` `collect` `groupingBy`

```
import java.util.*;
import java.util.stream.*;

public class StreamCompleteDemo {
    public static void main(String[] args) {
        List<String> words = Arrays.asList(
            "apple", "banana", "apple", "orange", 
            "banana", "apple", "grape", "kiwi"
        );
        
        // 任务：统计所有长度 >= 5 的单词出现次数，按次数降序输出
        Map<String, Long> result = words.stream()
            .filter(word -> word.length() >= 5)                    // 1. filter: 筛选长度>=5
            .map(String::toUpperCase)                              // 2. map: 转为大写
            .sorted()                                              // 3. sorted: 排序（便于阅读）
            .collect(Collectors.groupingBy(                       // 4. collect + groupingBy
                Function.identity(),                              // 按单词自身分组
                Collectors.counting()                             // 统计出现次数
            ));
        
        System.out.println("统计结果: " + result);
        // 输出: 统计结果: {APPLE=3, ORANGE=1, BANANA=2, GRAPE=1}
        
        // 按次数降序排列输出
        result.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> System.out.println(entry.getKey() + "=" + entry.getValue()));
        // 输出:
        // APPLE=3
        // BANANA=2
        // ORANGE=1
        // GRAPE=1
    }
}
```

