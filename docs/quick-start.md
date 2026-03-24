# Quick Start - 5 Minutes 🚀

## Prerequisites

- Java 17+
- Maven 3.9+
- Claude Code installed

## Step 1: Build the JAR

```bash
git clone https://github.com/SoberChina/mcp-hub.git
cd mcp-hub/servers/java-spring-mcp
mvn clean package -DskipTests
# JAR at: target/java-spring-mcp-1.0.0.jar
```

## Step 2: Configure Claude Code

Add to your Claude Code `settings.json`:

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

## Step 3: Restart Claude Code

Restart Claude Code to load the new MCP server configuration.

## Verify

In Claude Code, type:
```
health_check
```
Should return:
```json
{
  "status": "UP",
  "server": "java-spring-mcp",
  "version": "1.0.0"
}
```

## Next Steps

- 📖 [Tools Reference](./tools-reference.md) — All tools and parameters
- 📝 [Examples](./examples.md) — Real-world usage cases
