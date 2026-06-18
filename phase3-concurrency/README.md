# 阶段三：多线程 + JVM（Week 3）

> 阶段二已完成 ✓ 进入后端核心：并发与 JVM。

## 目标

- 理解线程不安全，会用 3 种方式做线程安全计数
- 掌握 `BlockingQueue` 生产者-消费者
- 会使用线程池
- 了解 JVM 内存结构，能复现 OOM

## 学习顺序

### Day 1–2：线程基础 + 线程安全


| 顺序  | 文件                                                                      | 任务                    |
| --- | ----------------------------------------------------------------------- | --------------------- |
| 1   | [CounterUnsafe.java](phase3/concurrency/CounterUnsafe.java)             | 运行，观察 count 远小于 20000 |
| 2   | [CounterSynchronized.java](phase3/concurrency/CounterSynchronized.java) | `synchronized` 修复     |
| 3   | [CounterAtomic.java](phase3/concurrency/CounterAtomic.java)             | `AtomicInteger`       |
| 4   | [CounterLongAdder.java](phase3/concurrency/CounterLongAdder.java)       | `LongAdder`（高并发推荐）    |


**前端对照：**


| Java                           | JS                    |
| ------------------------------ | --------------------- |
| `new Thread(() -> {}).start()` | Web Worker（概念类似：独立执行） |
| `synchronized`                 | 互斥锁，同一时刻只有一个线程进       |
| `AtomicInteger`                | 没有直接对应，可理解成「原子操作的变量」  |


### Day 3：生产者-消费者 + 线程池


| 顺序  | 文件                                                                    | 任务                 |
| --- | --------------------------------------------------------------------- | ------------------ |
| 5   | [ProducerConsumer.java](phase3/concurrency/ProducerConsumer.java)     | `BlockingQueue` 实现 |
| 6   | [ThreadPoolDownload.java](phase3/concurrency/ThreadPoolDownload.java) | 3 线程下载 10 个任务      |
| 7   | [DeadlockDemo.java](phase3/concurrency/DeadlockDemo.java)             | 复现死锁，用 `jstack` 排查 |


### Day 4–5：JVM


| 顺序  | 文件                                      | 任务                     |
| --- | --------------------------------------- | ---------------------- |
| 8   | [OomDemo.java](phase3/jvm/OomDemo.java) | 限制堆 20MB，触发 OOM        |
| 9   | 笔记                                      | 画 JVM 内存图，写学习路线「阶段三」笔记 |


**JVM 速记：**

```
┌─────────────────────────────────┐
│  堆 Heap（对象、数组）            │  ← OOM 发生在这里
│  ├─ 年轻代 Young（Eden + Survivor）│
│  └─ 老年代 Old                    │
├─────────────────────────────────┤
│  栈 Stack（每个线程一份）           │  ← 局部变量、方法调用
│  程序计数器、本地方法栈             │
├─────────────────────────────────┤
│  方法区 / 元空间 Metaspace（类信息）│
└─────────────────────────────────┘

                    ┌─────────────────────────────────────────┐
                    │              JVM 内存布局                │
                    └─────────────────────────────────────────┘

  ┌── 线程共享 ──────────────────────────────────────────────────────────┐
  │                                                                      │
  │   ┌─── 堆 Heap（-Xms / -Xmx 控制大小，OOM 最常见）──────────────────┐  │
  │   │                                                                  │  │
  │   │   ┌── 年轻代 Young Gen（-XX:NewRatio 等）───────────────┐       │  │
  │   │   │                                                      │       │  │
  │   │   │   ┌─────────────┐  ┌──────┐  ┌──────┐               │       │  │
  │   │   │   │   Eden      │  │  S0  │  │  S1  │               │       │  │
  │   │   │   │  （伊甸园）  │  │幸存0 │  │幸存1 │               │       │  │
  │   │   │   │  new 对象   │  │      │  │      │               │       │  │
  │   │   │   │  先放这里   │  │      │  │      │               │       │  │
  │   │   │   └──────┬──────┘  └──┬───┘  └──┬───┘               │       │  │
  │   │   │          │ Minor GC     │◄──────►│                   │       │  │
  │   │   │          └──────────────┴────────┘                   │       │  │
  │   │   └──────────────────────────┬───────────────────────────┘       │  │
  │   │                              │ 存活足够久的对象                   │  │
  │   │   ┌──────────────────────────▼───────────────────────────┐       │  │
  │   │   │              老年代 Old Generation                    │       │  │
  │   │   │         长期存活对象、大对象（部分策略）                 │       │  │
  │   │   └──────────────────────────┬───────────────────────────┘       │  │
  │   │                              │ Full GC / Major GC               │  │
  │   └──────────────────────────────┴──────────────────────────────────┘  │
  │                                                                      │
  │   ┌─── 元空间 Metaspace（-XX:MetaspaceSize，Java 8+ 在本地内存）─────┐  │
  │   │  类的结构、方法信息、运行时常量池、静态变量（引用在堆，值看类型）    │  │
  │   └──────────────────────────────────────────────────────────────────┘  │
  │                                                                      │
  │   ┌─── 直接内存 Direct Memory（-XX:MaxDirectMemorySize）─────────────┐  │
  │   │  NIO ByteBuffer 等堆外内存，不属于堆，但受 JVM 管理                 │  │
  │   └──────────────────────────────────────────────────────────────────┘  │
  └──────────────────────────────────────────────────────────────────────────┘

  ┌── 每个线程私有（线程结束就回收）─────────────────────────────────────────┐
  │                                                                          │
  │   程序计数器 PC Register    当前线程执行到字节码第几条                     │
  │                                                                          │
  │   虚拟机栈 VM Stack         每个方法 = 一「栈帧 Stack Frame」              │
  │   ┌─────────────────────────────────────────┐                           │
  │   │ 栈帧：局部变量表、操作数栈、动态链接、返回地址  │  ← main、download 等   │
  │   ├─────────────────────────────────────────┤                           │
  │   │              另一个栈帧                  │                           │
  │   └─────────────────────────────────────────┘                           │
  │   StackOverflowError：递归太深 / 栈太深                                   │
  │                                                                          │
  │   本地方法栈 Native Method Stack   调用 native 方法（如 Object.hashCode）  │
  └──────────────────────────────────────────────────────────────────────────┘
```

## 运行方式

```bash
mvn compile
java -cp target/classes phase3.concurrency.CounterUnsafe
java -cp target/classes phase3.jvm.OomDemo
```

**OOM 实验（限制堆 20MB）：**

```bash
java -Xms20m -Xmx20m -cp target/classes phase3.jvm.OomDemo
```

**死锁排查：**

```bash
# 终端1：运行 DeadlockDemo（会卡住）
java -cp target/classes phase3.concurrency.DeadlockDemo

# 终端2：找到进程 pid，执行
jps
jstack <pid>
```

## 验收标准

- 能解释：`volatile` 可见但不保证原子性
- 三种计数方案结果都接近 20000
- 能说出线程池 7 大参数，并解释任务提交流程
- 能画出 JVM 运行时数据区

### volatile 自测

`volatile` 保证**可见性**（一个线程改了，别的线程立刻能看到）和**有序性**（禁止部分指令重排），但**不保证原子性**。

典型反例：`count++` 实际是「读 → 改 → 写」三步，多线程下仍会丢更新，所以计数器要用 `synchronized` / `AtomicInteger` / `LongAdder`。

### 线程池 7 大参数

`ThreadPoolExecutor` 构造函数的 7 个参数（`keepAliveTime` 和 `unit` 算一组）：

```java
new ThreadPoolExecutor(
    corePoolSize,      // 1. 核心线程数
    maximumPoolSize,   // 2. 最大线程数
    keepAliveTime,     // 3. 存活时间
    unit,              // 4. 时间单位（配 keepAliveTime）
    workQueue,         // 5. 任务队列
    threadFactory,     // 6. 线程工厂
    handler            // 7. 拒绝策略
);
```

**任务提交流程（必背）：**

```
1. 核心线程未满  →  新建核心线程执行
2. 核心线程满了  →  任务进队列
3. 队列满了      →  扩容到 max 线程（仅当 max > core）
4. 线程也到 max  →  触发拒绝策略
```

| 参数 | 含义 | 要点 |
| --- | --- | --- |
| **corePoolSize** | 常驻核心线程数 | 即使空闲也保留（除非 `allowCoreThreadTimeOut`） |
| **maximumPoolSize** | 线程数上限 | 核心 + 临时扩容的非核心线程 |
| **keepAliveTime + unit** | 非核心线程空闲多久回收 | `core = max` 的固定池基本用不上 |
| **workQueue** | 任务排队队列 | 有界 / 无界 / 不存储，决定背压行为 |
| **threadFactory** | 创建线程 | 自定义线程名，方便日志排查 |
| **handler** | 拒绝策略 | 队列满且线程到 max 时怎么办 |

**常见队列：**

| 队列 | 特点 | 典型场景 |
| --- | --- | --- |
| `LinkedBlockingQueue`（无界） | 可一直排，任务堆积可能 OOM | `newFixedThreadPool` 默认 |
| `ArrayBlockingQueue`（有界） | 容量固定 | 生产环境常用 |
| `SynchronousQueue` | 不存储，直接交给线程 | `newCachedThreadPool` |

**四种拒绝策略：**

| 策略 | 行为 |
| --- | --- |
| `AbortPolicy`（默认） | 抛 `RejectedExecutionException` |
| `CallerRunsPolicy` | 调用者线程自己执行任务（削峰） |
| `DiscardPolicy` | 静默丢弃 |
| `DiscardOldestPolicy` | 丢弃队列里最老的任务，再提交新的 |

**和 ThreadPoolDownload 的对应：**

```java
Executors.newFixedThreadPool(3)
// 等价于 core = max = 3 + 无界 LinkedBlockingQueue + AbortPolicy
// 10 个任务 → 3 线程执行，7 个排队 → 不会触发拒绝策略
```

**生产建议：** 尽量手写 `ThreadPoolExecutor`，显式指定有界队列和拒绝策略，避免 `Executors` 工厂方法的隐藏风险。

