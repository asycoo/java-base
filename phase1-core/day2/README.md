# Day 2：OOP 深度

> 从前端的「函数 + 对象字面量」过渡到 Java 的「类设计 + 多态」。今天代码量会明显变大。

## 今日目标

- [ ] 实操 2.1：不可变 `Money` 类（`BigDecimal` + `equals`/`hashCode`）
- [ ] 实操 2.2：员工体系 + 多态发薪
- [ ] 实操 2.3：图书管理系统 v1（第一个多类项目）
- [ ] 验收：能画 UML 类图；能解释封装 / 继承 / 多态

## 学习顺序

### 上午（约 3–4h）

| 顺序 | 目录 | 任务 |
|------|------|------|
| 1 | [money/Money.java](money/Money.java) | 不可变值对象，金额运算 |
| 2 | [employee/](employee/) | 抽象类 + 继承 + 多态 |

**前端对照：**

| Java OOP | 前端类似 |
|----------|----------|
| `class` + `private` 字段 | TS `class` 或闭包隐藏状态 |
| `extends` 继承 | `class Dog extends Animal`（ES6 class） |
| `interface` / 抽象类 | TS `interface` / `abstract class` |
| 多态 | 父类型变量指向不同子类实例，像 React 里统一 `render()` 不同组件 |

### 下午（约 3–4h）

| 顺序 | 目录 | 任务 |
|------|------|------|
| 3 | [library/](library/) | Book / Member / LoanRecord / LibraryService |
| 4 | 运行 `LibraryMain.java` | 控制台菜单驱动 |

**设计约束（必须遵守）：**

- 业务逻辑写在 `LibraryService`，`main` 只做菜单和 I/O
- 所有字段 `private`，通过方法访问
- 借不存在的书、还不存在的书 → 抛 `LibraryException`

## 建议类图（图书系统）

```
┌─────────────┐     ┌──────────────────┐     ┌────────────┐
│    Book     │     │  LibraryService  │     │   Member   │
├─────────────┤     ├──────────────────┤     ├────────────┤
│ id          │◄────│ books: Map       │     │ id         │
│ title       │     │ members: Map     │────►│ name       │
│ author      │     │ loans: List      │     └────────────┘
│ available   │     ├──────────────────┤
└─────────────┘     │ + borrowBook()   │     ┌────────────┐
                    │ + returnBook()   │     │ LoanRecord │
                    │ + calcFine()     │     ├────────────┤
                    └──────────────────┘     │ bookId     │
                                             │ memberId   │
                                             │ dueDate    │
                                             └────────────┘
```

## 验收自测

1. **Money**：`new Money("10.00", "CNY").add(new Money("5.50", "CNY"))` 结果是 `15.50 CNY`
2. **多态**：`List<Employee>` 里混放两种员工，`PayrollService` 一次算总工资
3. **图书系统**：借书 → 书变为不可借 → 还书 → 恢复可借；逾期有罚款

## 完成后

进入 Day 3：集合深入 + 图书系统 v2 文件持久化。
