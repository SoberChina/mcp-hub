# MCP Hub 🦞

> AI 编程时代的一站式 MCP（Model Context Protocol）工具集，专注 Java Spring Boot 开发提效。

## 🎯 MCP 是什么？

MCP（Model Context Protocol，模型上下文协议）是一种开放协议，让 Claude Code、Cursor 等 AI 编程工具能够连接外部工具、访问数据源、生成代码。

## 📦 Java Spring Boot MCP 服务器 ✅ 生产就绪

**让 Claude Code 变身为 Java Spring Boot 代码生成机器。**

| 工具 | 功能 |
|------|------|
| `spring_generate` | 生成 Entity、Repository、Service、Controller、DTO、Config — 真可用代码 |
| `java_compile` | 编译 Java 源代码 |
| `health_check` | 检查服务器健康状态 |

## 🚀 快速开始

### 1. 构建 JAR

```bash
git clone https://github.com/SoberChina/mcp-hub.git
cd mcp-hub/servers/java-spring-mcp
mvn clean package -DskipTests
```

### 2. 配置 Claude Code

在 Claude Code 设置中添加：

```json
{
  "mcpServers": {
    "java-spring-mcp": {
      "command": "java",
      "args": ["-jar", "D:/openclaw_workspace/mcp-hub/servers/java-spring-mcp/target/java-spring-mcp-1.0.0.jar"]
    }
  }
}
```

### 3. 开始使用

在 Claude Code 中直接说：
> "生成一个 User Entity，包含 username、email、password 字段，然后生成完整 CRUD 代码"

## 📚 文档

| 文档 | 说明 |
|------|------|
| [快速开始](./docs/quick-start_zh-CN.md) | 5分钟上手指南 |
| [工具参数详解](./docs/tools-reference_zh-CN.md) | 每个工具的详细参数说明 |
| [实战示例](./docs/examples_zh-CN.md) | 真实使用场景演示 |
| [Contributing](../CONTRIBUTING.md) | 如何贡献新 MCP 服务器 |

## 🤝 如何贡献

1. Fork 本仓库
2. 创建目录：`servers/你的服务器名/`
3. 添加 README.md 和源代码
4. 提交 Pull Request

## 📊 数据

![Stars](https://img.shields.io/github/stars/SoberChina/mcp-hub?style=social)
![Forks](https://img.shields.io/github/forks/SoberChina/mcp-hub?style=social)

## 🔗 相关链接

- [MCP 官方文档](https://modelcontextprotocol.io)
- [Claude Code](https://claude.ai/code)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [GitHub 仓库](https://github.com/SoberChina/mcp-hub)

---

用 ❤️ 和 AI 打造 by [SoberChina](https://github.com/SoberChina)
