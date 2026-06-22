# Java 完全掌握学习路线（含实操）

> **适用对象**：有编程经验或学过 Java 但不成体系，希望系统掌握到「能独立做后端项目」  
> ** realistic 周期**：**8–10 周**（每天 3–4 小时）可达到扎实掌握；精通需持续项目实践  
> **学习原则**：每个阶段必须有代码产出；理论 → 小练习 → 阶段项目 → 自测

---

## 先说清楚：「完全掌握」是什么

Java 体系很大，完全掌握 ≠ 背完 API，而是下面 **5 层能力都过关**：

| 层级 | 能力 | 验收标准 |
|------|------|----------|
| **L1 语言** | 语法、OOP、集合、泛型、异常、Lambda/Stream | 不看文档写出正确代码 |
| **L2 平台** | 多线程、JVM 内存/GC、反射、注解 | 能分析 NPE/OOM、写线程安全代码 |
| **L3 工程** | Maven、JUnit、日志、设计模式 | 能搭建标准项目、写测试、重构烂代码 |
| **L4 框架** | Spring Boot、Spring MVC、JPA/MyBatis | 独立实现 REST API + 数据库 CRUD |
| **L5 实战** | 系统设计、性能、排查、部署 | 完成 1 个可演示的完整项目 |

本路线按 **6 个阶段**推进，前 3 天是**加速启动**（不是零基础保姆教程），后面按周深入。

---

## 总览时间线

```
Week 1          Week 2          Week 3          Week 4
├─ Day1-3 核心  ├─ 集合/泛型     ├─ 多线程        ├─ IO/NIO
│  语言+OOP     ├─ Stream/Lambda ├─ JVM 原理      ├─ 异常体系
│               │                │                │
Week 5-6                    Week 7-8                    持续
├─ Maven + 测试              ├─ Spring Boot              ├─ 源码阅读
├─ 设计模式                  ├─ 综合项目                  ├─ 性能调优
└─ 阶段项目 v1               └─ 阶段项目 v2              └─ 面试/进阶
```

---

## 环境准备（开始前 1 小时）

| 工具 | 版本 | 用途 |
|------|------|------|
| JDK | 17 或 21（LTS） | 编译运行 |
| IDE | IntelliJ IDEA **或** VS Code + Extension Pack for Java | 二选一即可 |
| Maven | 3.9+ | 构建（Week 5 起） |
| Docker Desktop | 最新 | 跑 MySQL/Redis（Week 7 起，可选） |

```bash
java -version && javac -version && mvn -version
```

**仓库目录规划：**

```
java-base/
├── doc/
│   └── Java完全掌握学习路线.md
├── phase1-core/          # Day 1-3
├── phase2-collections/   # Week 2
├── phase3-concurrency/   # Week 3
├── phase4-io/            # Week 4
├── phase5-engineering/   # Week 5-6
└── phase6-spring/        # Week 7-8 综合项目
```

---

# 阶段一：语言核心（Day 1–3，加速）

> 有基础则快速过语法，重点放在 OOP 深度和编码规范。

## Day 1：语法 + 方法 + 数组（6h）

### 上午：快速回顾 + 规范

**必须掌握：**

- 基本类型 / 包装类 / 自动装箱、`==` vs `equals`
- `String` 不可变、`StringBuilder` 使用场景
- 流程控制、增强 for、数组与二维数组
- 方法：重载、可变参数、值传递（Java 只有值传递）

**实操 1.1 — 算法热身（不看答案独立完成）**

1. 反转字符串（不用 `StringBuilder.reverse`）
2. 判断回文字符串
3. 找出 int 数组中第二大的数
4. 打印杨辉三角（10 行）

**实操 1.2 — 简易计算器**

- 支持 `+ - * /`，连续运算，除零异常处理
- 拆成 `parse`、`calculate`、`formatResult` 方法

### 下午：调试 + 代码质量

- IDE 断点、条件断点、Evaluate Expression
- 读懂常见编译错误 vs 运行时错误

**实操 1.3 — 日志版计算器**

- 每次运算打印入参和结果（为后续日志框架做铺垫）
- 用 `Scanner` 做 REPL 循环，输入 `exit` 退出

### Day 1 验收

- [ ] 30 分钟内独立完成回文判断 + 数组第二大
- [ ] 能解释 `Integer a=127; Integer b=127; a==b` 为 true 的原因

---

## Day 2：OOP 深度（6–8h）

### 上午：类设计

**必须掌握：**

- 封装、构造链、`this`/`super`
- 继承 vs 组合（优先组合）
- 多态、动态绑定
- 抽象类 vs 接口（Java 8+ `default`/`static` 方法）
- 重写 `equals`/`hashCode`/`toString` 的规则
- 不可变对象设计（`final` 字段、无 setter）

**实操 2.1 — 设计 `Money` 类**

- 字段：`amount`（用 `BigDecimal`）、`currency`
- 实现：`add`、`subtract`、比较、不可变
- 正确重写 `equals`/`hashCode`

**实操 2.2 — 员工体系**

```
Employee（抽象）→ SalariedEmployee / HourlyEmployee
PayrollService 计算总工资（多态）
```

### 下午：UML + 图书系统 v1

**实操 2.3 — 图书管理系统 v1**

```
Book / Member / LoanRecord / LibraryService
```

- `LibraryService`：借书、还书、逾期罚款
- 用 `ArrayList` + `HashMap` 管理
- 控制台菜单驱动

**设计约束：**

- 领域逻辑不放 `main`
- 不允许 public 字段
- 删除/借不存在的书 → 自定义 `LibraryException`

### Day 2 验收

- [ ] 能画 UML 类图并解释 Book / LibraryService 关系
- [ ] 能说明何时用接口、何时用抽象类
- [ ] 图书系统 v1 可运行，有基本异常提示

---

## Day 3：集合入门 + 异常 + 文件（6–8h）

### 上午：集合框架概览

**必须掌握：**

| 接口 | 常用实现 | 特点 |
|------|----------|------|
| `List` | `ArrayList` / `LinkedList` | 有序可重复 |
| `Set` | `HashSet` / `TreeSet` | 不重复 |
| `Map` | `HashMap` / `TreeMap` | 键值对 |
| `Queue` | `ArrayDeque` / `PriorityQueue` | 队列 |

- 迭代器 `Iterator`、fail-fast 机制
- `Comparable` vs `Comparator`

**实操 3.1 — 集合练习**

1. 统计文本词频 Top 10 → `Map<String, Integer>`
2. 对 `List<Student>` 按成绩降序、再按姓名升序
3. 用 `Set` 对 10 万随机数去重，对比 `List` 去重性能（感受 O(1) vs O(n)）

### 下午：图书系统 v2（持久化）

**升级 v1 → v2：**

- `HashMap<String, Book>` 做主索引
- CSV 文件加载/保存（`Files.readAllLines` / `Files.write`）
- 按作者索引（`Map<String, List<Book>>`）— 体会一对多
- 统一异常处理层

### Day 3 验收

- [ ] 图书系统 v2 重启后数据不丢
- [ ] 能说出 `HashMap` 底层结构（数组 + 链表/红黑树，JDK 8+）
- [ ] 阶段一小项目代码提交到 `phase1-core/`

---

# 阶段二：集合深入 + 泛型 + 现代 Java（Week 2）

## 目标

- 泛型与类型擦除
- Stream API、Optional、Lambda
- 时间 API（`java.time`）
- 函数式接口

## 理论要点

```java
// 泛型
public class Box<T> { private T value; }

// Lambda + Stream
list.stream()
    .filter(x -> x.getScore() >= 60)
    .sorted(Comparator.comparing(Student::getScore).reversed())
    .map(Student::getName)
    .toList();

// Optional 避免 NPE
Optional.ofNullable(user).map(User::getName).orElse("匿名");

// 新时间 API
LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
```

## 实操

**2.1 — 手写简易 `ArrayList`（核心方法：`add/get/remove/size/expand`）**

**2.2 — 用 Stream 重构阶段一词频统计**

**2.3 — 订单分析系统**

- 输入一批 `Order(userId, amount, createdAt)`
- 输出：每用户总消费、最高单笔、近 7 天 daily sum
- 全部用 Stream 实现，禁止 for 循环处理业务

**2.4 — 阅读 `HashMap` 源码（JDK 17）**

- 重点：`put`/`get`、resize、树化阈值（链表长度 > 8）
- 写笔记：为什么线程不安全

## Week 2 验收

- [ ] 能解释泛型擦除和 `List<String>` 运行时是什么
- [ ] 能手写 5 个常用 Stream 操作
- [ ] 简易 ArrayList 通过单元测试

---

# 阶段三：多线程 + JVM（Week 3）

## 目标

- 线程创建、同步、线程池
- 并发容器、原子类
- JVM 内存结构、GC、类加载
- 能排查 OOM、死锁

## 理论要点

| 主题 | 核心 |
|------|------|
| 线程 | `Thread`/`Runnable`、`synchronized`、`Lock`、`volatile` |
| 线程池 | `ExecutorService`、`ThreadPoolExecutor` 七大参数 |
| 并发包 | `ConcurrentHashMap`、`CountDownLatch`、`Semaphore` |
| JVM | 堆/栈/方法区、年轻代/老年代、G1 GC |
| 类加载 | 加载→验证→准备→解析→初始化、双亲委派 |

## 实操

**3.1 — 1000 线程不安全计数 → 改线程安全（至少 3 种方案）**

- `synchronized`
- `AtomicInteger`
- `LongAdder`

**3.2 — 生产者-消费者（`BlockingQueue`）**

**3.3 — 固定大小线程池下载模拟**

- 10 个 URL，池大小 3，统计耗时

**3.4 — 死锁复现 + `jstack` 排查**

**3.5 — JVM 实验**

```bash
java -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError OomDemo
```

- 用 VisualVM 或 IDEA Profiler 看堆占用

## Week 3 验收

- [ ] 能画出 JVM 运行时数据区
- [ ] 能解释 `volatile` 不能保证原子性的原因
- [ ] 能配置一个合理的线程池并说明拒绝策略

---

# 阶段四：IO/NIO + 异常体系（Week 4）

## 目标

- 字节流/字符流、缓冲流
- NIO：`Channel`/`Buffer`/`Selector` 概念
- 序列化（了解 JSON 为主，Java 原生序列化知道坑即可）
- 受检/非受检异常设计

## 实操

**4.1 — 文件拷贝工具**

- 分别用 BIO 和 NIO 实现，Benchmark 对比 100MB 文件

**4.2 — 简易 JSON 序列化（理解原理，不必造轮子）**

- 用 Jackson 或 Gson 做 `Book ↔ JSON`
- 理解 `@JsonProperty`、`@JsonIgnore`

**4.3 — 设计异常 hierarchy**

```
AppException（运行时）
├── BusinessException（业务可预期）
├── ValidationException
└── InfrastructureException（IO/网络）
```

重构图书系统，统一错误码 + 消息

**4.4 — 网络入门：Socket 聊天室（选做）**

- 服务端 + 多客户端广播
- 为 Spring Web 打底

## Week 4 验收

- [ ] 能说出 BIO vs NIO 适用场景
- [ ] 项目中不再滥用 `catch(Exception e) {}`
- [ ] 图书系统 v3：JSON 持久化 + 异常体系

---

# 阶段五：工程化 + 设计模式 + 测试（Week 5–6）

## 目标

- Maven 多模块项目结构
- JUnit 5 + Mockito
- SLF4J + Logback
- 常用设计模式（不是背 23 种，掌握 7–8 种）

## Maven 标准结构

```
my-app/
├── pom.xml
└── src/
    ├── main/java/
    ├── main/resources/
    └── test/java/
```

```bash
mvn archetype:generate -DgroupId=com.example -DartifactId=library -DarchetypeArtifactId=maven-archetype-quickstart
mvn test && mvn package
```

## 必会设计模式

| 模式 | 场景 |
|------|------|
| 单例 | 配置管理（枚举单例） |
| 工厂 / 抽象工厂 | 创建支付渠道 |
| 策略 | 折扣/计费规则 |
| 模板方法 | 数据处理流程 |
| 观察者 | 事件通知 |
| 装饰器 | 增强 IO/功能 |
| 代理 | AOP 基础、RPC |

## 实操

**5.1 — 把图书系统迁移为 Maven 项目**

- 分包：`model` / `service` / `repository` / `cli`
- 引入 JUnit 5，核心 service 测试覆盖率 > 70%

**5.2 — 支付模块（设计模式综合）**

```
PaymentStrategy → Alipay / Wechat / Bank
PaymentContext 运行时选择策略
PaymentFactory  创建实例
```

**5.3 — Mock 测试**

- Mock `BookRepository`，测 `LibraryService` 借书逻辑
- 测异常路径：书已借出、用户不存在

**5.4 — 代码重构练习**

- 给你一段「烂代码」（长方法、魔法数、重复逻辑）
- 重构：Extract Method、Replace Conditional with Polymorphism

## 阶段项目 v1：命令行 + 文件存储的「小型 ERP」

- 模块：商品、库存、订单
- Maven + 测试 + 日志 + 异常体系 + 至少 2 种设计模式

## Week 5–6 验收

- [ ] 独立创建 Maven 项目并跑通 `mvn test`
- [ ] 能写出 Mock 单元测试
- [ ] 阶段项目 v1 可演示

---

# 阶段六：Spring Boot + 综合项目（Week 7–8）

## 目标

- Spring IoC / DI、AOP 概念
- Spring Boot 自动配置
- REST API、参数校验、统一响应
- Spring Data JPA 或 MyBatis
- 全局异常处理、拦截器

## 技术栈

| 组件 | 用途 |
|------|------|
| Spring Boot 3.x | 应用框架 |
| Spring Web | REST |
| Spring Data JPA | ORM |
| H2 / MySQL | 数据库 |
| Lombok | 减少样板代码 |
| Springdoc / Knife4j | API 文档 |

## 学习路径

1. 纯 Spring Boot Hello + 配置文件 `application.yml`
2. `@RestController` CRUD
3. `@Service` + `@Repository` 分层
4. `@Valid` + `@ControllerAdvice` 统一异常
5. JPA 实体关系：`@OneToMany` / `@ManyToOne`
6. 分页查询、条件查询
7. 集成测试：`@SpringBootTest` + `MockMvc`

## 综合项目 v2：在线图书借阅 API

**功能：**

- 用户注册/登录（JWT 或 Session，二选一）
- 图书 CRUD（管理员）
- 借阅 / 归还 / 逾期查询
- 借阅记录分页
- 统一错误码 `{ code, message, data }`

**非功能：**

- 分层架构清晰
- 核心 Service 有单元测试
- API 文档可访问
- README 含启动说明

**加分项：**

- Redis 缓存热门图书
- Docker Compose 一键启动
- GitHub Actions CI 跑测试

## Week 7–8 验收

- [ ] 独立从零搭建 Spring Boot 项目
- [ ] REST API 符合 RESTful 规范
- [ ] 综合项目 v2 可本地跑通并演示
- [ ] 能解释 `@Autowired` 原理（IoC 容器）

---

# 进阶路线（8 周后，持续）

| 方向 | 内容 | 建议 |
|------|------|------|
| 源码 | `ArrayList`/`HashMap`/`ConcurrentHashMap`、Spring 核心 | 每周读 1 个类 |
| 性能 | JMH 基准测试、SQL 优化、连接池调优 | 结合项目 |
| 分布式 | Redis、消息队列、微服务、Spring Cloud | 有单体基础后再学 |
| 并发进阶 | `CompletableFuture`、虚拟线程（Java 21） | 复习 Week 3 |
| 计算机基础 | 网络、操作系统、数据库索引/事务 | 面试必备 |

---

# 推荐书单 & 资源

| 类型 | 资源 |
|------|------|
| 入门到扎实 | 《Head First Java》 |
| 进阶必读 | 《Effective Java》第三版 |
| 并发 | 《Java 并发编程实战》 |
| JVM | 《深入理解 Java 虚拟机》 |
| Spring | Spring 官方文档 + Spring Boot Reference |
| 练习 | [LeetCode 标签：Java](https://leetcode.cn/) 数组/哈希/链表/二叉树 |
| 官方 | [Oracle Java Docs](https://docs.oracle.com/en/java/) |

---

# 自测：你是否「掌握」了 Java？

## L1 语言（必须全过）

- [ ] 手写 `equals`/`hashCode`，说明契约
- [ ] 解释 HashMap 扩容、hash 冲突处理
- [ ] 用 Stream 完成分组、聚合、排序
- [ ] 解释 `ArrayList` 扩容机制

## L2 平台（必须全过）

- [ ] 写出线程安全的单例（枚举）
- [ ] 配置线程池并解释各参数
- [ ] 画出 JVM 堆结构，说明 GC 触发条件
- [ ] 用 jstack/jmap 排查一次问题

## L3 工程（必须全过）

- [ ] Maven 生命周期：`compile/test/package`
- [ ] 写 Mock 测试隔离数据库
- [ ] 重构一段 200 行烂代码

## L4 框架（必须全过）

- [ ] 独立实现 CRUD REST API
- [ ] 解释 Spring Bean 生命周期
- [ ] 全局异常 + 参数校验

## L5 实战（至少 1 项）

- [ ] 完成综合项目 v2 并写 README
- [ ] 能向他人讲解项目架构（15 分钟）
- [ ] 能应对常见 Java 后端面试题（集合/并发/JVM/Spring）

---

# 前 3 天快速启动表（阶段一浓缩版）

| 天 | 上午 | 下午 | 产出 |
|----|------|------|------|
| **Day 1** | 语法回顾 + 算法热身 1.1 | 计算器 1.2–1.3 | `phase1-core/day1/` |
| **Day 2** | OOP 深度 + Money/Employee 2.1–2.2 | 图书系统 v1（2.3） | `phase1-core/day2/` |
| **Day 3** | 集合 + 词频/排序 3.1 | 图书系统 v2 持久化 | `phase1-core/day3/` |

> Day 1–3 是加速启动，不是终点。真正「完全掌握」至少走完 **6 个阶段**。

---

## 学习笔记（自用）

### 阶段一（Day 1–3）

- **Day 1 已完成**（2026-06-16）：warmup + calculator
- **Day 2 已完成**：OOP — `phase1-core/day2/`
- **Day 3 已完成**：集合 + 持久化 — `phase1-core/day3/`
- **阶段二已完成**：泛型 + Stream — `phase2-collections/`
- **阶段三已完成**（Week 3）：多线程 + JVM — `phase3-concurrency/`
- **阶段四已开始**（Week 4）：IO/NIO + 异常 — `phase4-io/`

### 阶段二（Week 2）

- 

### 阶段三（Week 3）

- 

### 阶段四（Week 4）

- 

### 阶段五（Week 5–6）

- 

### 阶段六（Week 7–8）

- 

---

*最后更新：2026-06-16*
