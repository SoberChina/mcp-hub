package com.mcp.server.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

/**
 * MCP Tool Controller
 * Handles incoming tool call requests from AI clients (Claude Code, etc.)
 */
@Slf4j
@RestController
@RequestMapping("/mcp")
public class McpToolController {

    private final Map<String, ToolHandler> tools = new LinkedHashMap<>();

    public McpToolController() {
        tools.put("java_compile", new JavaCompileTool());
        tools.put("spring_generate", new SpringGenerateTool());
        tools.put("health_check", new HealthCheckTool());
    }

    @PostMapping("/tools")
    public ToolResponse executeTool(@Valid @RequestBody ToolRequest request) {
        log.info("Executing tool: {}", request.getName());

        ToolHandler handler = tools.get(request.getName());
        if (handler == null) {
            return ToolResponse.error("Tool not found: " + request.getName());
        }

        try {
            Object result = handler.execute(request.getParams());
            return ToolResponse.success(result);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid tool parameters: {}", e.getMessage());
            return ToolResponse.error("Invalid parameters: " + e.getMessage());
        } catch (Exception e) {
            log.error("Tool execution failed", e);
            return ToolResponse.error(e.getMessage());
        }
    }

    @GetMapping("/tools")
    public List<Map<String, String>> listTools() {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map.Entry<String, ToolHandler> entry : tools.entrySet()) {
            Map<String, String> tool = new LinkedHashMap<>();
            tool.put("name", entry.getKey());
            tool.put("description", entry.getValue().getDescription());
            result.add(tool);
        }
        return result;
    }

    // ========== Tool Handlers ==========

    static class JavaCompileTool implements ToolHandler {
        @Override
        public String getDescription() {
            return "Compile Java source code. Params: code (String) — the Java source code to compile.";
        }

        @Override
        public Object execute(Map<String, Object> params) {
            String code = (String) params.get("code");
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("Parameter 'code' is required");
            }
            return Map.of(
                    "status", "ok",
                    "message", "Java compilation would be performed here. Source code length: " + code.length() + " chars"
            );
        }
    }

    static class SpringGenerateTool implements ToolHandler {
        @Override
        public String getDescription() {
            return "Generate Spring Boot components. Params: type (entity|repository|service|controller|dto|config), name (String), package (String, optional, default com.example), fields (List<Map>, for entity/dto only)";
        }

        @Override
        public Object execute(Map<String, Object> params) {
            String type = (String) params.getOrDefault("type", "");
            String name = (String) params.getOrDefault("name", "");
            String pkg = (String) params.getOrDefault("package", "com.example");
            @SuppressWarnings("unchecked")
            List<Map<String, String>> fields = (List<Map<String, String>>) params.get("fields");

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Parameter 'name' is required");
            }
            if (type == null || type.isBlank()) {
                throw new IllegalArgumentException("Parameter 'type' is required. Supported: entity, repository, service, controller, dto, config");
            }

            String content = switch (type.toLowerCase()) {
                case "entity" -> generateEntity(pkg, name, fields);
                case "repository" -> generateRepository(pkg, name);
                case "service" -> generateService(pkg, name);
                case "controller" -> generateController(pkg, name);
                case "dto" -> generateDto(pkg, name, fields);
                case "config" -> generateConfig(pkg, name);
                default -> throw new IllegalArgumentException(
                        "Unsupported type: '" + type + "'. Supported: entity, repository, service, controller, dto, config");
            };

            return Map.of(
                    "status", "generated",
                    "type", type,
                    "name", name,
                    "package", pkg,
                    "content", content
            );
        }

        private String generateEntity(String pkg, String name, List<Map<String, String>> fields) {
            StringBuilder sb = new StringBuilder();
            sb.append("package ").append(pkg).append(".entity;\n\n");
            sb.append("import jakarta.persistence.*;\n");
            sb.append("import lombok.*;\n");
            sb.append("import java.time.LocalDateTime;\n\n");
            sb.append("@Entity\n");
            sb.append("@Table(name = \"").append(toSnakeCase(name)).append("\")\n");
            sb.append("@Data\n");
            sb.append("@NoArgsConstructor\n");
            sb.append("@AllArgsConstructor\n");
            sb.append("@Builder\n");
            sb.append("public class ").append(name).append(" {\n\n");
            sb.append("    @Id\n");
            sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            sb.append("    private Long id;\n\n");

            if (fields != null) {
                for (Map<String, String> field : fields) {
                    String fieldName = field.get("name");
                    String fieldType = field.getOrDefault("type", "String");
                    String colName = field.get("column");
                    if (fieldName != null && !fieldName.isBlank()) {
                        if (colName != null && !colName.isBlank()) {
                            sb.append("    @Column(name = \"").append(colName).append("\")\n");
                        }
                        sb.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
                    }
                }
            }

            sb.append("    @Column(name = \"created_at\", updatable = false)\n");
            sb.append("    private LocalDateTime createdAt;\n\n");
            sb.append("    @Column(name = \"updated_at\")\n");
            sb.append("    private LocalDateTime updatedAt;\n\n");
            sb.append("    @PrePersist\n");
            sb.append("    protected void onCreate() {\n");
            sb.append("        createdAt = LocalDateTime.now();\n");
            sb.append("        updatedAt = LocalDateTime.now();\n");
            sb.append("    }\n\n");
            sb.append("    @PreUpdate\n");
            sb.append("    protected void onUpdate() {\n");
            sb.append("        updatedAt = LocalDateTime.now();\n");
            sb.append("    }\n");
            sb.append("}\n");
            return sb.toString();
        }

        private String generateRepository(String pkg, String name) {
            return """
                    package %s.repository;

                    import %s.entity.%s;
                    import org.springframework.data.jpa.repository.JpaRepository;
                    import org.springframework.data.jpa.repository.Query;
                    import org.springframework.data.repository.query.Param;
                    import org.springframework.stereotype.Repository;

                    import java.util.List;

                    @Repository
                    public interface %sRepository extends JpaRepository<%s, Long> {

                        List<%s> findByNameContainingIgnoreCase(String name);

                        @Query("SELECT e FROM %s e WHERE e.active = true ORDER BY e.createdAt DESC")
                        List<%s> findAllActive();

                        @Query("SELECT COUNT(e) FROM %s e")
                        long countAll();

                        boolean existsByNameIgnoreCase(@Param("name") String name);
                    }
                    """.formatted(pkg, pkg, name, name, name, name, name, name, name);
        }

        private String generateService(String pkg, String name) {
            String entityName = name.replace("Service", "");
            String repositoryName = entityName + "Repository";
            String varName = toCamelCase(entityName);

            return """
                    package %s.service;

                    import %s.entity.%s;
                    import %s.repository.%sRepository;
                    import lombok.RequiredArgsConstructor;
                    import lombok.extern.slf4j.Slf4j;
                    import org.springframework.stereotype.Service;
                    import org.springframework.transaction.annotation.Transactional;

                    import java.util.List;
                    import java.util.Optional;

                    @Slf4j
                    @Service
                    @RequiredArgsConstructor
                    public class %sService {

                        private final %sRepository %sRepository;

                        @Transactional(readOnly = true)
                        public List<%s> findAll() {
                            log.debug("Finding all %s");
                            return %sRepository.findAll();
                        }

                        @Transactional(readOnly = true)
                        public Optional<%s> findById(Long id) {
                            log.debug("Finding %s by id: {}", id);
                            return %sRepository.findById(id);
                        }

                        @Transactional
                        public %s save(%s %s) {
                            log.info("Saving %s: {}", %s);
                            return %sRepository.save(%s);
                        }

                        @Transactional
                        public void deleteById(Long id) {
                            log.info("Deleting %s by id: {}", id);
                            %sRepository.deleteById(id);
                        }

                        @Transactional(readOnly = true)
                        public long count() {
                            return %sRepository.count();
                        }
                    }
                    """.formatted(
                    pkg, pkg, entityName, pkg, entityName,
                    name, entityName, varName,
                    entityName, entityName, varName,
                    entityName, entityName, varName,
                    entityName, entityName, varName, entityName, varName, varName, varName,
                    entityName, varName,
                    varName
            );
        }

        private String generateController(String pkg, String name) {
            String entityName = name.replace("Controller", "");
            String serviceName = entityName + "Service";
            String varName = toCamelCase(entityName);

            return """
                    package %s.controller;

                    import %s.entity.%s;
                    import %s.service.%sService;
                    import lombok.RequiredArgsConstructor;
                    import org.springframework.http.HttpStatus;
                    import org.springframework.http.ResponseEntity;
                    import org.springframework.web.bind.annotation.*;

                    import java.util.List;

                    @RestController
                    @RequestMapping("/api/%s")
                    @RequiredArgsConstructor
                    public class %sController {

                        private final %sService %sService;

                        @GetMapping
                        public ResponseEntity<List<%s>> getAll() {
                            return ResponseEntity.ok(%sService.findAll());
                        }

                        @GetMapping("/{id}")
                        public ResponseEntity<%s> getById(@PathVariable Long id) {
                            return %sService.findById(id)
                                    .map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.notFound().build());
                        }

                        @PostMapping
                        public ResponseEntity<%s> create(@RequestBody %s %s) {
                            %s saved = %sService.save(%s);
                            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
                        }

                        @PutMapping("/{id}")
                        public ResponseEntity<%s> update(@PathVariable Long id, @RequestBody %s %s) {
                            return %sService.findById(id)
                                    .map(existing -> {
                                        %s.setId(id);
                                        return ResponseEntity.ok(%sService.save(%s));
                                    })
                                    .orElse(ResponseEntity.notFound().build());
                        }

                        @DeleteMapping("/{id}")
                        public ResponseEntity<Void> delete(@PathVariable Long id) {
                            %sService.deleteById(id);
                            return ResponseEntity.noContent().build();
                        }
                    }
                    """.formatted(
                    pkg, pkg, entityName, pkg, serviceName, toKebabCase(entityName),
                    name, serviceName, varName,
                    entityName, varName,
                    entityName, varName,
                    entityName, entityName, varName, entityName, varName, varName,
                    entityName, entityName, varName, entityName, varName, varName, varName,
                    varName
            );
        }

        private String generateDto(String pkg, String name, List<Map<String, String>> fields) {
            StringBuilder sb = new StringBuilder();
            sb.append("package ").append(pkg).append(".dto;\n\n");
            sb.append("import jakarta.validation.constraints.*;\n");
            sb.append("import lombok.*;\n\n");
            sb.append("@Data\n");
            sb.append("@NoArgsConstructor\n");
            sb.append("@AllArgsConstructor\n");
            sb.append("@Builder\n");
            sb.append("public class ").append(name).append(" {\n\n");

            if (fields != null) {
                for (Map<String, String> field : fields) {
                    String fieldName = field.get("name");
                    String fieldType = field.getOrDefault("type", "String");
                    String requiredStr = field.get("required");
                    boolean required = "true".equalsIgnoreCase(requiredStr);
                    String maxStr = field.get("max");
                    String minStr = field.get("min");

                    if (fieldName != null && !fieldName.isBlank()) {
                        if (required) {
                            sb.append("    @NotBlank\n");
                        }
                        if ("String".equals(fieldType) && maxStr != null) {
                            sb.append("    @Size(max = ").append(maxStr).append(")\n");
                        }
                        if ("String".equals(fieldType) && minStr != null) {
                            sb.append("    @Size(min = ").append(minStr).append(")\n");
                        }
                        sb.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
                    }
                }
            }

            sb.append("}\n");
            return sb.toString();
        }

        private String generateConfig(String pkg, String name) {
            return """
                    package %s.config;

                    import org.springframework.context.annotation.Bean;
                    import org.springframework.context.annotation.Configuration;
                    import org.springframework.web.servlet.config.annotation.CorsRegistry;
                    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

                    @Configuration
                    public class %s implements WebMvcConfigurer {

                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                            registry.addMapping("/api/**")
                                    .allowedOrigins("*")
                                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                    .allowedHeaders("*");
                        }
                    }
                    """.formatted(pkg, name);
        }

        private String toSnakeCase(String input) {
            return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        }

        private String toCamelCase(String input) {
            return Character.toLowerCase(input.charAt(0)) + input.substring(1);
        }

        private String toKebabCase(String input) {
            return toSnakeCase(input).replace('_', '-');
        }
    }

    static class HealthCheckTool implements ToolHandler {
        @Override
        public String getDescription() {
            return "Check MCP server health status. Returns server name, version, status, and current timestamp.";
        }

        @Override
        public Object execute(Map<String, Object> params) {
            Map<String, Object> health = new LinkedHashMap<>();
            health.put("status", "UP");
            health.put("server", "java-spring-mcp");
            health.put("version", "1.0.0");
            health.put("timestamp", Instant.now().toString());
            health.put("uptimeMs", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            return health;
        }
    }

    // ========== DTOs ==========

    @Data
    public static class ToolRequest {
        @NotBlank(message = "Tool name is required")
        private String name;
        private Map<String, Object> params;
    }

    @Data
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
