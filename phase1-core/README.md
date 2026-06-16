# 阶段一：语言核心（Day 1–3）

| 天 | 目录 | 状态 |
|----|------|------|
| Day 1 | [day1/](day1/) | 已完成 |
| Day 2 | [day2/](day2/) | 已完成 |
| Day 3 | [day3/](day3/) | 进行中 |

## 如何运行

**VS Code（推荐你已在使用时）：**

1. 安装扩展：[Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
2. `File` → `Open Folder` → 选择 `java-base`
3. 打开 `.java` 文件 → 点击 `main` 上方的 `Run` / `Debug`

**IntelliJ IDEA：**

`Open` → 选择 `java-base` → 打开 Java 文件 → 右键 `Run main()`

**命令行编译运行（有 package 后从 `phase1-core` 目录）：**

```bash
cd phase1-core
javac -d bin day1/warmup/*.java && java -cp bin day1.warmup.ReverseString
javac -d bin day1/calculator/*.java && java -cp bin day1.calculator.Calculator
javac -d bin day2/library/*.java && java -cp bin day2.library.LibraryMain
javac -d bin day3/library/*.java && java -cp bin day3.library.LibraryMain
```
