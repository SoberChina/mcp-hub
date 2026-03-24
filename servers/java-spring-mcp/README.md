# Java Spring Boot MCP Server

A Spring Boot starter for building MCP (Model Context Protocol) servers with Java.

## Features

- ✅ Spring Boot integration
- ✅ Tool registration via annotations
- ✅ Resource providers
- ✅ Claude Code compatible

## Quick Start

```bash
./mvnw spring-boot:run
```

## Configuration

```yaml
mcp:
  server:
    name: java-mcp-server
    version: 1.0.0
  port: 8080
```

## Building

```bash
./mvnw clean package
```

## License

MIT
