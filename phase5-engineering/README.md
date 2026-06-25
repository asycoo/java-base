# 阶段五：工程化 + 设计模式 + 测试（Week 5–6）

> 阶段四已完成 ✓ 进入「能写工程」：JUnit、Mockito、设计模式。

## 目标

- 用 JUnit 5 写单元测试，替代手写 `main` + `AssertionError`
- 用 Mockito Mock 依赖，只测业务逻辑
- 掌握策略模式 + 工厂模式
- 跑通 `mvn test`

## 学习顺序

### Day 1–2：JUnit 5 + Mockito

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 1 | [LibraryServiceTest.java](../phase5-test/phase5/library/LibraryServiceTest.java) | 阅读并理解，跑通 `mvn test` |
| 2 | 自己加 1 个测试 | 如 `returnBook_无借阅记录_抛 LOAN_NOT_FOUND` |

**前端对照：**

| Java | JS |
| --- | --- |
| JUnit 5 `@Test` | Vitest / Jest `it()` |
| `assertEquals` / `assertThrows` | `expect().toBe()` / `expect(() => ...).toThrow()` |
| `@Mock BookRepository` | `vi.mock('./BookRepository')` |
| `@BeforeEach` | `beforeEach()` |

**核心思想：**

```
LibraryServiceTest
    │
    ├── @Mock BookRepository   ← 假的仓库，不碰真实文件
    ├── when(load).thenReturn(testData)
    └── 只测 borrowBook / returnBook 业务逻辑
```

### Day 3–4：设计模式 — 支付模块

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 3 | [payment/](phase5/payment/) | 实现策略 + 工厂 + Context |
| 4 | [PaymentDemo.java](phase5/payment/PaymentDemo.java) | 运行演示 |

```
PaymentStrategy（接口）
├── AlipayStrategy
├── WechatStrategy
└── BankStrategy

PaymentFactory.create("alipay") → PaymentStrategy
PaymentContext.pay(100.0)       → 委托给当前策略
```

### 必会设计模式（7 种）

| # | 模式 | 一句话 | 本仓库示例 | 运行 |
| --- | --- | --- | --- | --- |
| 1 | **单例** | 全局只要一个实例 | `patterns/singleton/AppConfig` | ↓ |
| 2 | **工厂** | 集中 `new`，调用方只传名字 | `PaymentFactory` | ↓ |
| 3 | **策略** | 同一行为，多种算法可换 | `PaymentStrategy` / `DiscountStrategy` | ↓ |
| 4 | **模板方法** | 流程固定，步骤子类实现 | `template/DataExporter` | ↓ |
| 5 | **观察者** | 事件发生了，通知所有订阅者 | `observer/OrderSubject` | ↓ |
| 6 | **装饰器** | 不改原类，层层包装增强 | `decorator/PrefixNotifier` | ↓ |
| 7 | **代理** | 中间人加日志/鉴权再调真对象 | `proxy/UserServiceProxy` | ↓ |

```bash
mvn exec:java -Dexec.mainClass=phase5.patterns.PatternsDemo
```

详见下方「设计模式速记」。

### Day 5–6：阶段项目 v1（选做）

小型 ERP：**商品 + 库存 + 订单**，整合 JSON 持久化、SLF4J 日志、统一异常、折扣策略模式。

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 5 | [erp/](phase5/erp/) | 阅读分包结构 |
| 6 | [ErpMain.java](phase5/erp/cli/ErpMain.java) | 控制台演示：入库、下单 |
| 7 | [OrderServiceTest.java](../phase5-test/phase5/erp/OrderServiceTest.java) | 跑通 ERP 单元测试 |

**目录结构：**

```
phase5/erp/
├── model/       Product, Order, OrderItem
├── repository/  JSON 读写
├── service/     Product / Inventory / Order
├── discount/    折扣策略（策略 + 工厂）
├── cli/         ErpMain 控制台
└── data/        products.json, orders.json
```

**运行：**

```bash
mvn test -Dtest=OrderServiceTest
mvn exec:java -Dexec.mainClass=phase5.erp.cli.ErpMain
```

**可选扩展：** 下单时接入 `phase5.payment` 支付模块；加 `@Slf4j` 更多日志；补 ProductService 测试。

## 运行方式

```bash
# 编译（会自动同步 IDE 依赖到 lib/）
mvn compile

# 跑全部单元测试
mvn test

# 只跑图书测试
mvn test -Dtest=LibraryServiceTest

# 支付演示
mvn exec:java -Dexec.mainClass=phase5.payment.PaymentDemo

# 小型 ERP
mvn exec:java -Dexec.mainClass=phase5.erp.cli.ErpMain
```

> **IDE 报红（找不到 slf4j / phase4.exception / jackson）？**
> 1. 在项目根执行 `mvn compile`（同步依赖）
> 2. `Cmd+Shift+P` → **Java: Clean Java Language Server Workspace** → Reload
> 3. 确认打开的是 `java-base` 根目录，不是子文件夹

> **`LibraryServiceTest` 没有 `main` 方法**，是 JUnit 测试类。
> 不要点类名旁的 **Run Java**（会报 `ClassNotFoundException` 或找不到主类）。
> 正确方式：`mvn test -Dtest=LibraryServiceTest`，或点 **`@Test` 上方的 Run Test**。

## 验收标准

- [ ] `mvn test` 全部通过
- [ ] 能解释 `@Mock` 和 `when(...).thenReturn(...)` 的作用
- [ ] 能说出策略模式 vs 工厂模式的区别
- [ ] 能手写一个 `assertThrows` 测异常路径
- [ ] `PaymentDemo` 能切换支付渠道并输出结果

### JUnit 5 速记

```java
@Test                          // 标记测试方法
@BeforeEach                    // 每个测试前执行（准备数据）
@ExtendWith(MockitoExtension.class)  // 启用 @Mock 注入

assertEquals(expected, actual)
assertTrue(condition)
assertThrows(Exception.class, () -> method())
```

### Mockito 速记

```java
@Mock BookRepository repo;           // 创建 mock 对象
when(repo.load(path)).thenReturn(map); // 打桩：调用 load 时返回 map
verify(repo).save(path, map);         // 验证 save 被调用过
```

## 设计模式速记

> 跑一遍：`mvn exec:java -Dexec.mainClass=phase5.patterns.PatternsDemo`

### 1. 单例 Singleton — 全局唯一

```java
AppConfig a = AppConfig.INSTANCE;
AppConfig b = AppConfig.INSTANCE;
a == b  // true，枚举写法推荐
```

**场景：** 配置、连接池。**别滥用**，能注入就别单例。

---

### 2. 工厂 Factory — 谁负责 new

```java
PaymentStrategy s = PaymentFactory.create("alipay");  // 不用 new AlipayStrategy()
```

**场景：** 创建逻辑集中，调用方只认接口。

---

### 3. 策略 Strategy — 算法可互换

```java
DiscountStrategy d = DiscountFactory.create("bulk");
double rate = d.discountRate(250);  // 满 200 打 9 折
```

**场景：** 支付渠道、折扣、排序规则。**解决 if-else 越来越长。**

---

### 4. 模板方法 Template Method — 流程骨架固定

```java
abstract class DataExporter {
    final void export(String raw) {  // 顺序不能改
        validate(raw);
        write(convert(raw));
    }
    abstract String convert(String raw);
}
```

**场景：** 导出 CSV/JSON、审批流程（申请→审核→归档）。

---

### 5. 观察者 Observer — 一对多通知

```java
subject.subscribe(new SmsListener());
subject.subscribe(new StockListener());
subject.createOrder("O100", 399);  // 两个 listener 都会收到
```

**场景：** 下单通知库存/短信。**Spring 事件、`@EventListener` 同源。**

---

### 6. 装饰器 Decorator — 包装增强

```java
Notifier n = new PrefixNotifier(new ConsoleNotifier(), "[ERP] ");
n.send("启动");  // 输出 [ERP] 启动
```

**场景：** `BufferedInputStream` 包装 `FileInputStream`。**不改原类，可叠多层。**

---

### 7. 代理 Proxy — 调用前后插逻辑

```java
UserService svc = new UserServiceProxy(new UserServiceImpl());
svc.login("张三");  // 代理里：校验 → 调真实对象 → 记日志
```

**场景：** 鉴权、事务、日志。**Spring AOP 底层就是动态代理。**

---

### 对照表

| 模式 | 解决什么 | 前端近似 |
| --- | --- | --- |
| 单例 | 只要一个实例 | 模块级 singleton |
| 工厂 | 创建对象 | `createX(type)` |
| 策略 | 多种算法 | 传入不同 handler |
| 模板方法 | 固定步骤 | 钩子函数 + 固定 pipeline |
| 观察者 | 事件广播 | `EventEmitter.on()` |
| 装饰器 | 增强不修改 | HOC 包组件 |
| 代理 | 中间层拦截 | axios 拦截器 |
