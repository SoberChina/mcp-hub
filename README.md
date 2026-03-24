# MCP Hub 🦞

> A curated collection of MCP (Model Context Protocol) servers for AI-powered Java development.

## 🎯 What is MCP?

The Model Context Protocol (MCP) is an open protocol that enables AI models like Claude Code to connect with external tools. This repository focuses on **Java Spring Boot** MCP servers that supercharge your Java development workflow.

## 📦 Our MCP Server

### Java Spring Boot MCP Server ✅ Production Ready

**Turns Claude Code into a Java Spring Boot code generation machine.**

| Tool | What it does |
|------|-------------|
| `spring_generate` | Generates Entity, Repository, Service, Controller, DTO, Config — real working Java code |
| `java_compile` | Compiles Java source code |
| `health_check` | Checks server health status |

## 🚀 Quick Start

### 1. Build the JAR

```bash
git clone https://github.com/SoberChina/mcp-hub.git
cd mcp-hub/servers/java-spring-mcp
mvn clean package -DskipTests
```

### 2. Configure Claude Code

Add this to your Claude Code settings (`~/.claude/settings.json`):

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

### 3. Start coding

In Claude Code, just say:

> "Create a User entity with username, email, and password fields, then generate the full CRUD stack"

The MCP server will generate all the Spring Boot code for you.

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [Quick Start Guide](./docs/quick-start.md) | Fast-track setup in 5 minutes |
| [Tools Reference](./docs/tools-reference.md) | All available tools and their parameters |
| [Examples](./docs/examples.md) | Real-world usage examples |
| [Contributing](../CONTRIBUTING.md) | How to add your own MCP server |

## 🤝 Contributing

Contributions welcome! Read [CONTRIBUTING.md](../CONTRIBUTING.md) to learn how to add your MCP server.

## 📊 Stats

![Stars](https://img.shields.io/github/stars/SoberChina/mcp-hub?style=social)
![Forks](https://img.shields.io/github/forks/SoberChina/mcp-hub?style=social)

## 🔗 Links

- [MCP Protocol Spec](https://modelcontextprotocol.io)
- [Claude Code](https://claude.ai/code)
- [Spring Boot](https://spring.io/projects/spring-boot)

---

Built with ❤️ by [SoberChina](https://github.com/SoberChina)
