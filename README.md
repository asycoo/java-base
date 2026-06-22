# java-base

Java 学习练习仓库（阶段一～四）。

## IDE 配置（VS Code / Cursor）

1. **Open Folder** 打开本目录 `java-base`（不要只开子目录）
2. 安装 [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
3. 首次打开等待 Maven 导入（右下角 ☕ Building workspace）
4. 若仍提示 `non-project file`：`Cmd+Shift+P` → **Java: Clean Java Language Server Workspace** → Reload

项目已配置 **Maven**（`pom.xml`），IDE 会自动识别各 `phase*` 目录下的 Java 文件。

## 编译 & 运行

```bash
# 编译整个项目
mvn compile

# 运行示例（main 类）
mvn -q exec:java -Dexec.mainClass=phase2.stream.WordFrequencyStream
```

或使用 classpath：

```bash
mvn -q compile
java -cp target/classes phase2.stream.WordFrequencyStream
```

## 目录

| 目录 | 内容 |
|------|------|
| [doc/](doc/) | 学习路线 |
| [phase1-core/](phase1-core/) | 阶段一 Day 1–3 |
| [phase2-collections/](phase2-collections/) | 阶段二 泛型 / Stream / ArrayList |
| [phase3-concurrency/](phase3-concurrency/) | 阶段三 多线程 / JVM |
| [phase4-io/](phase4-io/) | 阶段四 IO/NIO / 异常 / JSON（进行中） |
