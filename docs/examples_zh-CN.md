# 实战示例 🦞

以下展示如何在 Claude Code 中使用 MCP Hub 加速 Java 开发。

## 示例1：一句话生成完整 CRUD 模块

**在 Claude Code 中输入：**

```
用 spring_generate 生成一个 BlogPost Entity，包名 com.myblog，字段：title(String, 必填, max 200)、content(Text)、author(String)、publishedAt(LocalDateTime)
```

**Claude Code 调用 MCP Server，生成：**

- `BlogPost.java` — JPA Entity
- `BlogPostRepository.java` — Spring Data JPA Repository
- `BlogPostService.java` — 业务逻辑 Service
- `BlogPostController.java` — REST API Controller
- `CreateBlogPostRequest.java` — 请求 DTO
- `CorsConfig.java` — 跨域配置

---

## 示例2：分步生成

**Step 1：先生成 Entity**
```
生成一个 Order Entity，包名 com.myapp.entity，字段：orderId(String)、totalAmount(BigDecimal)、status(String)、createTime(LocalDateTime)
```

**Step 2：生成 Repository**
```
基于刚生成的 Order Entity，生成对应的 Repository
```

**Step 3：生成 Service + Controller**
```
为 Order 生成完整的 Service 和 REST Controller
```

---

## 示例3：实际项目片段

假设你在开发一个库存管理系统，在 Claude Code 中输入：

```
为商品管理模块生成完整的代码：
- Product Entity：productName(String), sku(String唯一), quantity(Integer), price(BigDecimal)
- ProductRepository：带按 SKU 查找的方法
- ProductService：含库存增减的业务方法
- ProductController：REST API
- DTO：CreateProductRequest, UpdateStockRequest
```

---

## 示例4：直接调用 health_check

在 Claude Code 中检查 MCP 服务器是否正常：

```
/health_check
```

---

## 与纯手工对比

| 场景 | 纯手工 | 用 MCP Hub |
|------|--------|-----------|
| 生成一个 Entity | ~3 分钟 | 3 秒 |
| 生成完整 CRUD 模块 | ~30 分钟 | 30 秒 |
| 查文档+写代码 | 必须 | 自动完成 |

## 下一步

- 🔧 想添加新工具？查看 [CONTRIBUTING.md](../CONTRIBUTING.md)
- 📖 更多工具用法：[工具参数详解](./tools-reference_zh-CN.md)
