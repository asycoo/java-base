# 阶段六：Spring Boot + REST API（Week 7–8）

> 阶段五已完成 ✓ 进入真正的后端开发：Spring Boot、REST、JPA。

## 目标

- 理解 IoC / DI（`@Autowired` 为什么能注入）
- 写 REST API（替代 `ErpMain` 控制台）
- 统一异常、参数校验
- 把图书系统升级成 **在线借阅 API**（综合项目 v2）

## 和前面阶段的关系

```
day3 LibraryService     →  phase4 JSON + 异常
       ↓
phase5 ErpMain 控制台   →  phase6 @RestController REST API
phase5 @Mock 单元测试   →  phase6 @SpringBootTest + MockMvc
phase5 代理/策略        →  phase6 Spring AOP / 业务策略
```

## 学习顺序

### Day 1：Hello Spring Boot

| 顺序 | 内容 | 任务 |
| --- | --- | --- |
| 1 | [LibraryApplication.java](src/main/java/com/asycoo/library/LibraryApplication.java) | 启动类 |
| 2 | [HelloController.java](src/main/java/com/asycoo/library/controller/HelloController.java) | 第一个 API |
| 3 | [application.yml](src/main/resources/application.yml) | 配置文件 |

```bash
cd phase6-springboot
mvn spring-boot:run
# 浏览器访问 http://localhost:8080/api/hello
```

**前端对照：**

| Spring Boot | 前端 |
| --- | --- |
| `@RestController` | Express / Koa 路由 handler |
| `@GetMapping` | `app.get('/api/hello')` |
| `@Autowired` | 依赖注入（React Context 自动传 deps） |
| `application.yml` | `.env` + 配置文件 |

### Day 2–3：REST CRUD + 分层

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 4 | [BookController.java](src/main/java/com/asycoo/library/controller/BookController.java) | 图书 CRUD API |
| 5 | [BookService.java](src/main/java/com/asycoo/library/service/BookService.java) | 业务层 |
| 6 | [BookRepository.java](src/main/java/com/asycoo/library/repository/BookRepository.java) | 数据层（内存） |
| 7 | [ApiResponse.java](src/main/java/com/asycoo/library/dto/ApiResponse.java) | 统一响应格式 |

**三层架构：**

```
HTTP 请求
   ↓
Controller  (@RestController)  接收请求、返回 JSON
   ↓
Service     (@Service)         业务逻辑
   ↓
Repository  (@Repository)      存取数据
```

**手动测试（先 `mvn spring-boot:run`）：**

```bash
# 列表
curl http://localhost:8080/api/books

# 详情
curl http://localhost:8080/api/books/B001

# 新增
curl -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"id":"B100","title":"Spring实战","author":"Craig","price":99}'

# 删除
curl -X DELETE http://localhost:8080/api/books/B100
```

**响应格式：**

```json
{ "code": 0, "message": "ok", "data": [ ... ] }
```

### Day 4–5：校验 + 全局异常

| 顺序 | 内容 |
| --- | --- |
| 7 | `@Valid` + `@NotBlank` 参数校验 |
| 8 | `@ControllerAdvice` — 复用 phase4 异常体系 |

### Week 8：JPA + 综合项目 v2

- H2 数据库 + `@Entity` Book / Loan
- 借阅 / 归还 API
- `@SpringBootTest` + `MockMvc` 集成测试
- Springdoc API 文档

## 运行方式

```bash
# 在 phase6-springboot 目录下（独立 Spring Boot 工程）
cd phase6-springboot
mvn spring-boot:run

# 或从仓库根目录
mvn -f phase6-springboot/pom.xml spring-boot:run
```

## 验收标准

- [x] 能独立启动 Spring Boot，访问 `/api/hello`
- [ ] 能解释 `@RestController` vs 普通 `@Controller`
- [ ] 能解释 `@Autowired` / IoC 是什么（看 BookController 构造器注入）
- [ ] 图书借阅 REST API 可本地跑通
- [ ] 能用 MockMvc 测一个 API

### IoC / DI 速记

```
没有 Spring：ErpMain 里手动 new
  ProductService s = new ProductService(repo, file);

有 Spring：容器帮你 new 并注入
  @Autowired BookService bookService;  // 容器已准备好
```

**IoC** = 控制权反转（对象创建交给容器）  
**DI** = 依赖注入（容器把依赖塞进来）
