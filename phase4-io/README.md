# 阶段四：IO/NIO + 异常体系（Week 4）

> 阶段三已完成 ✓ 阶段四已完成 ✓ 详见 [阶段五](../phase5-engineering/README.md)。

## 目标

- 掌握字节流 / 字符流 / 缓冲流，理解 try-with-resources
- 会用 NIO `Channel` 做文件拷贝，能对比 BIO vs NIO
- 用 Jackson 做 `Book ↔ JSON`
- 设计统一异常 hierarchy，告别 `catch (Exception e) {}`

## 学习顺序

### Day 1–2：BIO + NIO 文件拷贝

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 1 | [FileCopyBio.java](phase4/io/FileCopyBio.java) | `InputStream` + 缓冲流拷贝 |
| 2 | [FileCopyNio.java](phase4/io/FileCopyNio.java) | `FileChannel.transferTo` 拷贝 |
| 3 | [FileCopyBenchmark.java](phase4/io/FileCopyBenchmark.java) | 生成 100MB 测试文件，对比耗时 |

**前端对照：**

| Java | JS/Node |
| --- | --- |
| `InputStream` / `OutputStream` | `fs.createReadStream` / `createWriteStream` |
| `Reader` / `Writer`（字符流） | 处理文本时用 `utf8` encoding |
| `Files.readAllLines` | `fs.readFile` + `split('\n')` |
| `FileChannel` | 更接近零拷贝，Node 的 `fs.copyFile` 底层也类似 |

**BIO vs NIO 速记：**

```
BIO（Blocking IO）     流式、阻塞，一次读/写一块，API 简单
NIO（New IO）          Channel + Buffer，可非阻塞，适合高并发网络 IO
文件拷贝               两者差距不大，NIO 的 transferTo 代码更短
网络服务器             NIO + Selector 才是主战场（Spring Netty 底层）
```

### Day 3：JSON 序列化

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 4 | [BookJsonDemo.java](phase4/library/BookJsonDemo.java) | Jackson 读写 JSON，理解 `@JsonProperty` |

### Day 4–5：异常体系 + 图书 v3

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 5 | [exception/](phase4/exception/) | 异常 hierarchy |
| 6 | [day3/library/](../../phase1-core/day3/library/) | **v3 已完成**：JSON 持久化 + 统一异常 |

**v3 改动摘要：**

| 模块 | 变化 |
| --- | --- |
| `BookRepository` | CSV → JSON（Jackson） |
| `LibraryService` | `LibraryException` → `BusinessException` + errorCode |
| `LibraryMain` | 分层 catch：`Validation` / `Business` / `Infrastructure` |
| 数据文件 | `books.txt` → `books.json` |

**验证：**

```bash
mvn compile
mvn exec:java -Dexec.mainClass=day3.library.LibraryV3Test
mvn exec:java -Dexec.mainClass=day3.library.LibraryMain
```

**异常 hierarchy：**

```
AppException（运行时基类，含 errorCode + message）
├── BusinessException      业务可预期（书已借出、库存不足）
├── ValidationException    参数校验失败
└── InfrastructureException IO / 网络 / 外部依赖失败
```

## 运行方式

```bash
mvn compile

# BIO 拷贝（需先运行 Benchmark 生成测试文件，或指定路径）
java -cp target/classes phase4.io.FileCopyBio /tmp/large.bin /tmp/large-copy-bio.bin

# NIO 拷贝
java -cp target/classes phase4.io.FileCopyNio /tmp/large.bin /tmp/large-copy-nio.bin

# 100MB 对比测试
java -cp target/classes phase4.io.FileCopyBenchmark

# JSON 演示
java -cp target/classes phase4.library.BookJsonDemo
```

## 验收标准

- [x] `FileCopyBenchmark` 能跑通，能说出 BIO vs NIO 适用场景
- [x] 能手写 try-with-resources 拷贝文件
- [x] `BookJsonDemo` 能序列化 / 反序列化 Book
- [x] 能画出异常 hierarchy，项目中不再空 catch
- [x] 图书系统 v3：JSON 持久化 + 统一错误码

### try-with-resources 自测

```java
// ✓ 自动 close，即使中途抛异常也会关流
try (InputStream in = new FileInputStream(src);
     OutputStream out = new FileOutputStream(dest)) {
    // ...
}
// 等价于 finally { in.close(); out.close(); } 但更安全
```

### 受检 vs 非受检

| 类型 | 代表 | 谁处理 |
| --- | --- | --- |
| **受检** Checked | `IOException` | 调用方必须 catch 或 throws |
| **非受检** Unchecked | `RuntimeException` | 可不声明，由上层或全局 handler 兜 |

业务异常继承 `RuntimeException`（非受检），避免每层都 `throws`。
