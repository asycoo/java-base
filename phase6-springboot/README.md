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

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 8 | [BookCreateRequest.java](src/main/java/com/asycoo/library/dto/BookCreateRequest.java) | `@NotBlank` / `@Positive` |
| 9 | [GlobalExceptionHandler.java](src/main/java/com/asycoo/library/exception/GlobalExceptionHandler.java) | `@RestControllerAdvice` 统一异常 |
| 10 | `exception/` 包 | 复用 phase4 异常体系 |

**校验测试：**

```bash
# 缺少 title → HTTP 400, code 4001
curl -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"id":"B200","author":"Craig","price":99}'

# 查不存在的书 → HTTP 404, code 4041
curl http://localhost:8080/api/books/NOT_EXIST
```

**异常流转：**

```
Service 抛 BusinessException
   ↓
GlobalExceptionHandler 捕获
   ↓
转成 ApiResponse { code: 4041, message: "...", data: null }
   ↓
HTTP 404 返回给前端
```

### Week 8：JPA + 综合项目 v2

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 11 | [entity/Book.java](src/main/java/com/asycoo/library/entity/Book.java) | `@Entity` 图书 |
| 12 | [entity/Loan.java](src/main/java/com/asycoo/library/entity/Loan.java) | `@Entity` 借阅记录 |
| 13 | [LoanController.java](src/main/java/com/asycoo/library/controller/LoanController.java) | 借书 / 还书 API |
| 14 | `BookRepository` | 内存 Map → Spring Data JPA |
| 15 | Springdoc | 访问 `/swagger-ui.html` |

**H2 控制台：** `http://localhost:8080/h2-console`（JDBC URL: `jdbc:h2:mem:library`）

**借阅测试：**

```bash
# 借书
curl -X POST http://localhost:8080/api/loans \
  -H 'Content-Type: application/json' \
  -d '{"bookId":"B001","memberId":"M001"}'

# 在借列表
curl http://localhost:8080/api/loans

# 还书（返回 fine 逾期罚款）
curl -X POST http://localhost:8080/api/loans/return \
  -H 'Content-Type: application/json' \
  -d '{"bookId":"B001","memberId":"M001"}'
```

**JPA 对照：**

| Spring Data JPA | 前端类比 |
| --- | --- |
| `@Entity` | 数据库表结构定义 |
| `JpaRepository` | ORM / Prisma client，不用手写 SQL |
| `data.sql` | seed 初始数据 |
| `@Transactional` | 事务：借书改两表，要么全成功要么全回滚 |

### 路径 A：JPA 关联 + 分页 + JWT

| 顺序 | 文件 | 任务 |
| --- | --- | --- |
| 16 | [Loan.java](src/main/java/com/asycoo/library/entity/Loan.java) | `@ManyToOne` 关联 Book / Member |
| 17 | [MemberController.java](src/main/java/com/asycoo/library/controller/MemberController.java) | 会员注册 |
| 18 | [LoanController.java](src/main/java/com/asycoo/library/controller/LoanController.java) | 借阅记录分页 |
| 19 | [AuthController.java](src/main/java/com/asycoo/library/controller/AuthController.java) | JWT 登录 |
| 20 | [SecurityConfig.java](src/main/java/com/asycoo/library/security/SecurityConfig.java) | 保护图书增删 |

**会员注册：**

```bash
curl -X POST http://localhost:8080/api/members \
  -H 'Content-Type: application/json' \
  -d '{"id":"M100","name":"王五","username":"wangwu","password":"123456"}'
```

**管理员登录（seed 用户 admin / 123456）：**

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"123456"}' | jq -r '.data.token')

# 带 token 新增图书（仅 ADMIN）
curl -X POST http://localhost:8080/api/books \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"id":"B200","title":"Spring实战","author":"Craig","price":99}'
```

**借阅分页：**

```bash
curl 'http://localhost:8080/api/loans?page=0&size=10&active=true'
# 响应 data.content 为列表，data.totalElements 为总数
```

### 路径 B：Docker + Redis + CI（进阶）

| 组件 | 文件 | 说明 |
| --- | --- | --- |
| Docker Compose | [docker-compose.yml](docker-compose.yml) | MySQL 8.4 + Redis 7 |
| docker profile | [application-docker.yml](src/main/resources/application-docker.yml) | 连 MySQL/Redis |
| Redis 缓存 | [BookService.java](src/main/java/com/asycoo/library/service/BookService.java) | `@Cacheable` 图书列表 |
| 虚拟线程 | `application.yml` | Java 21 `spring.threads.virtual.enabled` |
| GitHub Actions | [.github/workflows/ci.yml](../.github/workflows/ci.yml) | push 自动跑测试 |

**一键启动基础设施：**

```bash
cd phase6-springboot
docker compose up -d          # 启动 MySQL + Redis
docker compose ps             # 确认 healthy

# 用 docker profile 启动应用
mvn spring-boot:run -Dspring-boot.run.profiles=docker
```

**验证 Redis 缓存（docker profile 下）：**

```bash
# 第一次查库，第二次走 Redis 缓存（日志无 SQL）
curl http://localhost:8080/api/books
curl http://localhost:8080/api/books

# 新增图书会 @CacheEvict 清缓存（需 ADMIN token，见路径 A）
curl -X POST http://localhost:8080/api/books \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"id":"B200","title":"Redis实战","author":"Huang","price":88}'
```

**Profile 对照：**

| Profile | 数据库 | 缓存 | 场景 |
| --- | --- | --- | --- |
| 默认（无 profile） | H2 内存 | Simple 本地 | 本地快速开发 |
| `docker` | MySQL | Redis | 接近生产环境 |
| 测试 | H2 内存 | Simple 本地 | `mvn test` / CI |

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
- [x] 图书借阅 REST API 可本地跑通
- [x] 能用 MockMvc 测一个 API

### IoC / DI 速记

```
没有 Spring：ErpMain 里手动 new
  ProductService s = new ProductService(repo, file);

有 Spring：容器帮你 new 并注入
  @Autowired BookService bookService;  // 容器已准备好
```

**IoC** = 控制权反转（对象创建交给容器）  
**DI** = 依赖注入（容器把依赖塞进来）
