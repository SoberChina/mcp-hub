# 工具参数详解

## spring_generate

生成 Spring Boot 各类组件代码，是最核心的工具。

### 参数

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| `type` | String | ✅ | — | 生成类型：entity/repository/service/controller/dto/config |
| `name` | String | ✅ | — | 组件名称（不含类型后缀） |
| `package` | String | ❌ | com.example | Java 包名 |
| `fields` | List[Map] | ❌ | — | 字段列表（仅 entity/dto 使用） |

### fields 参数结构（entity/dto 专用）

| 字段 | 类型 | 说明 |
|------|------|------|
| `name` | String | 字段名 |
| `type` | String | Java 类型，默认 String |
| `column` | String | 数据库列名（entity 专用） |
| `required` | String | "true" 表示必填（dto 专用） |
| `max` | String | 字符串最大长度（dto 专用） |

### type 详细说明

#### entity — JPA Entity
```json
{
  "name": "spring_generate",
  "params": {
    "type": "entity",
    "name": "Product",
    "package": "com.myapp.entity",
    "fields": [
      {"name": "productName", "type": "String", "column": "product_name"},
      {"name": "price", "type": "BigDecimal", "column": "price"},
      {"name": "stock", "type": "Integer"}
    ]
  }
}
```

生成内容：完整的 JPA Entity，含 `@Entity`、`@Table`、`@Id`、`@GeneratedValue`、`@Data`、`@PrePersist`、`@PreUpdate`

#### repository — Spring Data JPA Repository
```json
{
  "name": "spring_generate",
  "params": {
    "type": "repository",
    "name": "Product",
    "package": "com.myapp.repository"
  }
}
```

生成内容：JpaRepository 接口，含 `findByName()` 和 `existsByNameIgnoreCase()` 方法

#### service — Service 层
```json
{
  "name": "spring_generate",
  "params": {
    "type": "service",
    "name": "ProductService",
    "package": "com.myapp.service"
  }
}
```

生成内容：Service 类，含 `findAll()`、`findById()`、`save()`、`deleteById()`、`count()` 方法，带 `@Transactional` 注解

#### controller — REST Controller
```json
{
  "name": "spring_generate",
  "params": {
    "type": "controller",
    "name": "ProductController",
    "package": "com.myapp.controller"
  }
}
```

生成内容：REST Controller，含完整 CRUD API（GET/POST/PUT/DELETE），路径 `/api/product`

#### dto — Data Transfer Object
```json
{
  "name": "spring_generate",
  "params": {
    "type": "dto",
    "name": "CreateProductRequest",
    "package": "com.myapp.dto",
    "fields": [
      {"name": "productName", "type": "String", "required": "true", "max": "255"},
      {"name": "price", "type": "BigDecimal"}
    ]
  }
}
```

生成内容：DTO 类，含 `@NotBlank`、`@Size` 等验证注解

#### config — 配置类
```json
{
  "name": "spring_generate",
  "params": {
    "type": "config",
    "name": "CorsConfig",
    "package": "com.myapp.config"
  }
}
```

生成内容：Spring 配置类，含 CORS 跨域配置

---

## java_compile

编译 Java 源代码（预览用，不生成 class 文件）

**参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `code` | String | ✅ | Java 源代码 |

**示例：**
```json
{
  "name": "java_compile",
  "params": {
    "code": "public class Hello { public static void main(String[] args) { System.out.println(\"Hello MCP!\"); } }"
  }
}
```

---

## health_check

检查 MCP 服务器健康状态，无需参数。

**调用：**
```json
{
  "name": "health_check",
  "params": {}
}
```

**返回：**
```json
{
  "status": "UP",
  "server": "java-spring-mcp",
  "version": "1.0.0",
  "timestamp": "2026-03-24T10:00:00Z"
}
```
