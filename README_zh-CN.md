# MCP Hub 🦞

> AI 编程时代的一站式 MCP（Model Context Protocol）工具与服务器精选集。

## 🎯 MCP 是什么？

MCP（Model Context Protocol，模型上下文协议）是一种开放协议，让 AI 模型能够连接外部工具和数据源。通过 MCP，Claude Code、Cursor 等 AI 编程工具可以调用你的自定义工具、访问你的数据库、操作你的文件系统。

## 📦 包含的 MCP 服务器

### Java Spring Boot MCP Server

**快速生成 Spring Boot 组件**，支持 Claude Code 直接调用。

| 工具 | 功能 |
|------|------|
| `spring_generate` | 生成 Entity、Repository、Service、Controller、DTO、Config |
| `java_compile` | 编译 Java 代码 |
| `health_check` | 检查 MCP 服务器健康状态 |

**使用示例：**

```json
// Claude Code 中调用
{
  "name": "spring_generate",
  "params": {
    "type": "entity",
    "name": "User",
    "package": "com.example.myapp",
    "fields": [
      {"name": "username", "type": "String", "column": "user_name"},
      {"name": "email", "type": "String"}
    ]
  }
}
```

生成结果：完整的 JPA Entity，包含 `@Entity`、`@Table`、`@Data`、`@PrePersist` 等注解。

### 文件系统 MCP 服务器

**AI 读写操作本地文件**，适合自动化脚本和开发工作流。

### API 网关 MCP 服务器

**快速生成 REST API 脚手架代码**，基于 Java Spring Boot。

## 🚀 快速开始

### Java Spring Boot MCP 服务器

```bash
# 克隆项目
git clone https://github.com/SoberChina/mcp-hub.git
cd mcp-hub/servers/java-spring-mcp

# 构建项目
mvn clean package

# 启动服务
mvn spring-boot:run

# 或运行 JAR
java -jar target/java-spring-mcp-1.0.0.jar
```

服务地址：`http://localhost:8080`

### Claude Code 配置

在 Claude Code 中添加 MCP 服务器：

```json
{
  "mcpServers": {
    "java-spring-mcp": {
      "command": "java",
      "args": ["-jar", "/path/to/java-spring-mcp-1.0.0.jar"]
    }
  }
}
```

### Spring 代码生成示例

```bash
# 启动后，在 Claude Code 中输入：
# 生成一个 User Entity，包含 username、email、password 字段

# 或生成完整 CRUD Service + Controller
```

## 🛠️ 工具详细说明

### spring_generate

生成 Spring Boot 各类组件代码：

| type 参数 | 生成内容 |
|-----------|---------|
| `entity` | JPA Entity，含 @Id、@Table、@Data、@PrePersist |
| `repository` | JpaRepository，含自定义查询方法 |
| `service` | Service 类，含 CRUD + 日志 |
| `controller` | REST Controller，含 CRUD API |
| `dto` | DTO 类，含验证注解 |
| `config` | 配置类，含 CORS 配置 |

## 📚 文档

- [英文文档](../README.md)
- [MCP 协议入门](../docs/getting-started.md)
- [Java 集成指南](../docs/java-integration.md)

## 🤝 如何贡献

欢迎提交 MCP 服务器或改进现有代码！

1. Fork 本仓库
2. 创建目录：`servers/你的服务器名/`
3. 添加 README.md 和源代码
4. 提交 Pull Request

## 📊 统计

![Stars](https://img.shields.io/github/stars/SoberChina/mcp-hub?style=social)
![Forks](https://img.shields.io/github/forks/SoberChina/mcp-hub?style=social)

## 🔗 相关链接

- [MCP 官方文档](https://modelcontextprotocol.io)
- [MCP SDK](https://github.com/modelcontextprotocol/sdk)
- [Claude Code](https://claude.ai/code)
- [GitHub 仓库](https://github.com/SoberChina/mcp-hub)

---

用 ❤️ 和 AI 打造 by [SoberChina](https://github.com/SoberChina)
