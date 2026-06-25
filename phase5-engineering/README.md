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
