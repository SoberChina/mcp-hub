# MCP Hub — Claude Code Guide

You are working in the `SoberChina/mcp-hub` GitHub repository.

## Project Overview

This is a collection of MCP (Model Context Protocol) servers. The primary focus is the **Java Spring Boot MCP Server** in `servers/java-spring-mcp/`.

## Current Focus

The Java Spring Boot MCP Server (`java-spring-mcp`) is **production-ready** and fully tested (9/9 tests passing).

Key facts:
- Package: `com.mcp.server`
- Server runs on port **8080**
- Tools: `spring_generate`, `java_compile`, `health_check`
- Spring Boot 3.2.3, Java 17+

## Architecture

The MCP server is a **Spring Boot REST API** (not a native MCP SDK server). It exposes HTTP endpoints:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/mcp/tools` | POST | Execute a tool |
| `/mcp/tools` | GET | List all available tools |

## How to Use

After the JAR is built and Claude Code is configured, users interact with the MCP server by asking Claude Code to generate Spring Boot code. Claude Code translates natural language → MCP tool calls.

## Before Editing Java Code

1. Make changes to `src/main/java/com/mcp/server/controller/McpToolController.java`
2. Run `mvn compile` to verify compilation
3. Run `mvn test` to ensure all tests pass
4. The JAR is built with `mvn package -DskipTests`

## Maven Usage

```bash
mvn clean compile        # compile
mvn test                 # run tests
mvn package -DskipTests  # build JAR
mvn spring-boot:run      # run without JAR
```
