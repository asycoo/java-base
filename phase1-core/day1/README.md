# Day 1：语法 + 方法 + 数组

## 今日目标

- [ ] 完成实操 1.1 算法热身（4 题）
- [ ] 完成实操 1.2 简易计算器
- [ ] 完成实操 1.3 日志版 REPL 计算器
- [ ] Day 1 验收：30 分钟独立完成回文 + 第二大；能解释 Integer 缓存

## 学习顺序

### 上午（约 3h）

1. 快速回顾：基本类型、包装类、`==` vs `equals`、`String`/`StringBuilder`
2. 依次完成 `warmup/` 下 4 个练习（**先看 TODO，自己实现，再看提示**）

| 文件 | 任务 |
|------|------|
| [ReverseString.java](warmup/ReverseString.java) | 反转字符串（禁止 `StringBuilder.reverse`） |
| [PalindromeChecker.java](warmup/PalindromeChecker.java) | 判断回文字符串 |
| [SecondLargest.java](warmup/SecondLargest.java) | 找数组中第二大的数 |
| [PascalTriangle.java](warmup/PascalTriangle.java) | 打印杨辉三角 10 行 |

### 下午（约 3h）

3. 完成 [Calculator.java](calculator/Calculator.java)：`parse` / `calculate` / `formatResult`
4. 完成 [CalculatorRepl.java](calculator/CalculatorRepl.java)：REPL 循环 + 运算日志
5. 练习 IDE 调试：断点、单步、Evaluate Expression（VS Code / IntelliJ 均可）

## 验收自测

**回文 + 第二大：** 合上书/关 IDE 提示，30 分钟内重写一遍。

**Integer 缓存：** 能解释下面为何 `a == b` 为 `true`，而 `c == d` 为 `false`？

```java
Integer a = 127, b = 127;
Integer c = 128, d = 128;
System.out.println(a == b); // true
System.out.println(c == d); // false
```

> 提示：`-128` 到 `127` 之间的 `Integer` 会被缓存复用。

## 完成后

在 [doc/Java完全掌握学习路线.md](../../doc/Java完全掌握学习路线.md) 的「学习笔记 → 阶段一」记下今天搞懂和还不懂的点，然后进入 Day 2。
