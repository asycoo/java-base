# Day 3：集合 + 异常 + 文件 IO + 图书系统 v2

> 阶段一最后一天。上午练集合，下午把 Day2 图书系统升级成**可持久化**的真实小项目。

## 今日目标

- [ ] 实操 3.1：词频统计、Student 排序、Set vs List 去重性能
- [ ] 实操 3.2：图书系统 v2 — CSV 加载/保存、作者索引、按价格排序
- [ ] 验收：重启程序后数据仍在；能说出 HashMap 底层结构

## 学习顺序

### 上午（约 3h）— 集合练习 `collections/`

| 顺序 | 文件 | 任务 |
|------|------|------|
| 1 | [WordFrequency.java](collections/WordFrequency.java) | 词频 Top 10，`Map<String, Integer>` |
| 2 | [StudentSorter.java](collections/StudentSorter.java) | 成绩降序 + 姓名升序，`Comparator` |
| 3 | [DedupBenchmark.java](collections/DedupBenchmark.java) | 10 万随机数，Set vs List 去重耗时 |

**前端对照：**

| Java | JS |
|------|-----|
| `Map` | `Map` / 普通对象 `{}` |
| `HashSet` | `new Set([...])` |
| `Comparator.comparing` | `array.sort((a,b) => ...)` |
| `List.sort(comparator)` | `[...].sort()` |

### 下午（约 3–4h）— 图书系统 v2 `library/`

| 顺序 | 文件 | 任务 |
|------|------|------|
| 4 | [BookRepository.java](library/BookRepository.java) | CSV 读写（核心 TODO） |
| 5 | [LibraryService.java](library/LibraryService.java) | 作者索引、搜索、排序 |
| 6 | 运行 [LibraryMain.java](library/LibraryMain.java) | 启动加载、退出保存 |

**v2 新增功能：**

- 启动从 `books.txt` 加载，退出时保存
- `Map<String, List<Book>>` 按作者索引
- 按作者搜索、按价格从低到高排序
- 文件不存在 → 空库启动（不崩溃）

## CSV 格式

```
id,title,author,price,available
B001,Java核心技术,Horstmann,128.0,true
B002,Effective Java,Bloch,89.0,true
```

## 运行方式

```bash
cd phase1-core
javac -d bin day3/collections/*.java && java -cp bin day3.collections.WordFrequency
javac -d bin day3/library/*.java && java -cp bin day3.library.LibraryMain
```

> 已使用 `package day3.library`，与 Day2 的 `day2.library` 区分，避免 IDE 跳转混乱。

## 验收自测

1. 添加一本书 → 退出 → 重启 → 书还在
2. `findByAuthor("Bloch")` 返回对应图书
3. `listBooksSortedByPrice()` 价格从低到高
4. 口述：HashMap = 数组 + 链表/红黑树（JDK 8+，链表长度 > 8 树化）

## 完成后

阶段一结束，进入 **Week 2：泛型 + Stream + 手写 ArrayList**（见学习路线阶段二）。
