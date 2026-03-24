package com.mcp.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * MCP Tool Controller
 * Handles incoming tool call requests from AI clients (Claude Code, etc.)
 */
@Slf4j
@RestController
@RequestMapping("/mcp")
public class McpToolController {

    private final Map<String, ToolHandler> tools = new HashMap<>();

    public McpToolController() {
        // Register built-in tools
        tools.put("java_compile", new JavaCompileTool());
        tools.put("spring_generate", new SpringGenerateTool());
        tools.put("health_check", new HealthCheckTool());
    }

    @PostMapping("/tools")
    public ToolResponse executeTool(@RequestBody ToolRequest request) {
        log.info("Executing tool: {}", request.getName());
        
        ToolHandler handler = tools.get(request.getName());
        if (handler == null) {
            return ToolResponse.error("Tool not found: " + request.getName());
        }
        
        try {
            Object result = handler.execute(request.getParams());
            return ToolResponse.success(result);
        } catch (Exception e) {
            log.error("Tool execution failed", e);
            return ToolResponse.error(e.getMessage());
        }
    }

    @GetMapping("/tools")
    public List<Map<String, String>> listTools() {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map.Entry<String, ToolHandler> entry : tools.entrySet()) {
            Map<String, String> tool = new HashMap<>();
            tool.put("name", entry.getKey());
            tool.put("description", entry.getValue().getDescription());
            result.add(tool);
        }
        return result;
    }

    // ========== Tool Handlers ==========

    static class JavaCompileTool implements ToolHandler {
        public String getDescription() { return "Compile Java source code"; }
        public Object execute(Map<String, Object> params) {
            return Map.of("status", "ok", "message", "Java compilation would be performed here");
        }
    }

    static class SpringGenerateTool implements ToolHandler {
        public String getDescription() { return "Generate Spring Boot components (Controller, Service, Repository)"; }
        public Object execute(Map<String, Object> params) {
            String type = (String) params.getOrDefault("type", "controller");
            String name = (String) params.getOrDefault("name", "MyComponent");
            return Map.of(
                "status", "generated",
                "type", type,
                "name", name,
                "content", generateSpringComponent(type, name)
            );
        }
        private String generateSpringComponent(String type, String name) {
            return switch (type) {
                case "controller" -> "@RestController\npublic class " + name + "Controller {}";
                case "service" -> "@Service\npublic class " + name + "Service {}";
                case "repository" -> "@Repository\npublic interface " + name + "Repository extends JpaRepository<" + name + ", Long> {}";
                default -> "// Component: " + name;
            };
        }
    }

    static class HealthCheckTool implements ToolHandler {
        public String getDescription() { return "Check MCP server health status"; }
        public Object execute(Map<String, Object> params) {
            return Map.of("status", "healthy", "timestamp", System.currentTimeMillis());
        }
    }

    // ========== DTOs ==========

    @lombok.Data
    public static class ToolRequest {
        private String name;
        private Map<String, Object> params;
    }

    @lombok.Data
    public static class ToolResponse {
        private boolean success;
        private Object result;
        private String error;

        public static ToolResponse success(Object result) {
            ToolResponse r = new ToolResponse();
            r.setSuccess(true);
            r.setResult(result);
            return r;
        }

        public static ToolResponse error(String message) {
            ToolResponse r = new ToolResponse();
            r.setSuccess(false);
            r.setError(message);
            return r;
        }
    }

    // ========== Tool Handler Interface ==========
    interface ToolHandler {
        String getDescription();
        Object execute(Map<String, Object> params);
    }
}
