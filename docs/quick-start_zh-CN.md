# 快速开始 - 5分钟上手 🦞

## 前置要求

- Java 17+
- Maven 3.9+
- Claude Code 已安装

## 第一步：下载或构建 JAR

**方式A：直接下载（推荐）**
```bash
# 从 GitHub Releases 下载最新的 java-spring-mcp-1.0.0.jar
```

**方式B：自己构建**
```bash
git clone https://github.com/SoberChina/mcp-hub.git
cd mcp-hub/servers/java-spring-mcp
mvn clean package -DskipTests
# JAR 文件生成在: target/java-spring-mcp-1.0.0.jar
```

## 第二步：配置 Claude Code

找到 Claude Code 的配置文件 `settings.json`（通常在 `~/.claude/settings.json` 或通过 Claude Code 输入 `/settings` 找到），添加：

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

> ⚠️ Windows 用户注意：使用正斜杠 `/` 或双反斜杠 `\\` 写路径

## 第三步：重启 Claude Code

保存配置后，重启 Claude Code让配置生效。

## 验证安装成功

在 Claude Code 中输入：
```
health_check
```
应该返回：
```json
{
  "status": "UP",
  "server": "java-spring-mcp",
  "version": "1.0.0"
}
```

## 常见问题

**Q: 显示连接失败？**
检查 JAR 路径是否正确，Java 17+ 是否已安装

**Q: 需要先启动服务吗？**
不需要！Claude Code 会自动通过 `java -jar` 启动 MCP 服务器

## 下一步

- 📖 [工具参数详解](./tools-reference.md) - 了解每个工具的详细用法
- 📝 [实战示例](./examples.md) - 看真实使用案例
