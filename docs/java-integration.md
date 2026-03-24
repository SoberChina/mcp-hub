# Java Integration with MCP

## Overview

This guide shows how to integrate MCP with your Java projects using Spring Boot.

## Maven Dependency

```xml
<dependency>
    <groupId>io.modelcontextprotocol</groupId>
    <artifactId>mcp-java-sdk</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Quick Start

```java
@SpringBootApplication
public class McpJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(McpJavaApplication.class, args);
    }
}

@RestController
@RequestMapping("/mcp")
public class McpController {
    
    @Resource
    private McpServer mcpServer;
    
    @PostMapping("/execute")
    public Map<String, Object> executeTool(@RequestBody ToolRequest request) {
        return mcpServer.execute(request.getToolName(), request.getParams());
    }
}
```

## Tools Available

| Tool | Description |
|------|-------------|
| `java_compile` | Compile Java code |
| `java_test` | Run JUnit tests |
| `spring_generate` | Generate Spring Boot components |

## Claude Code Integration

Configure Claude Code to use your MCP server:

```json
{
  "mcpServers": {
    "java-tools": {
      "command": "java",
      "args": ["-jar", "mcp-java-server.jar"]
    }
  }
}
```
