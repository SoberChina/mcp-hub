package com.mcp.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcp.server.controller.McpToolController.ToolRequest;
import com.mcp.server.controller.McpToolController.ToolResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(McpToolController.class)
class McpToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /mcp/tools")
    class ListToolsTests {

        @Test
        @DisplayName("should return all registered tools")
        void shouldReturnAllRegisteredTools() throws Exception {
            mockMvc.perform(get("/mcp/tools"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(4)))
                    .andExpect(jsonPath("$[0].name", is("java_compile")))
                    .andExpect(jsonPath("$[1].name", is("spring_generate")))
                    .andExpect(jsonPath("$[2].name", is("health_check")))
                    .andExpect(jsonPath("$[3].name", is("code_review")));
        }
    }

    @Nested
    @DisplayName("POST /mcp/tools")
    class ExecuteToolTests {

        @Test
        @DisplayName("should return error for unknown tool")
        void shouldReturnErrorForUnknownTool() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("unknown_tool");
            request.setParams(Map.of());

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("Tool not found")));
        }

        @Test
        @DisplayName("should return error when name is missing")
        void shouldReturnErrorWhenNameIsMissing() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("");
            request.setParams(Map.of());

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("Tool name is required")));
        }
    }

    @Nested
    @DisplayName("java_compile tool")
    class JavaCompileToolTests {

        @Test
        @DisplayName("should execute java_compile with valid code")
        void shouldExecuteJavaCompileWithValidCode() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("java_compile");
            request.setParams(Map.of("code", "public class Hello {}"));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.status", is("ok")));
        }

        @Test
        @DisplayName("should return error when code parameter is missing")
        void shouldReturnErrorWhenCodeIsMissing() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("java_compile");
            request.setParams(Map.of());

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("'code' is required")));
        }
    }

    @Nested
    @DisplayName("spring_generate tool")
    class SpringGenerateToolTests {

        @Test
        @DisplayName("should return error when name is missing")
        void shouldReturnErrorWhenNameIsMissing() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("spring_generate");
            request.setParams(Map.of("type", "entity"));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("'name' is required")));
        }

        @Test
        @DisplayName("should return error when type is missing")
        void shouldReturnErrorWhenTypeIsMissing() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("spring_generate");
            request.setParams(Map.of("name", "User"));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("'type' is required")));
        }

        @Test
        @DisplayName("should return error for unsupported type")
        void shouldReturnErrorForUnsupportedType() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("spring_generate");
            request.setParams(Map.of("name", "User", "type", "unknown"));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("Unsupported type")));
        }
    }

    @Nested
    @DisplayName("health_check tool")
    class HealthCheckToolTests {

        @Test
        @DisplayName("should return health status")
        void shouldReturnHealthStatus() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("health_check");
            request.setParams(Map.of());

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.status", is("UP")))
                    .andExpect(jsonPath("$.result.server", is("java-spring-mcp")))
                    .andExpect(jsonPath("$.result.version", is("1.0.0")))
                    .andExpect(jsonPath("$.result.timestamp", notNullValue()));
        }
    }

    @Nested
    @DisplayName("code_review tool")
    class CodeReviewToolTests {

        @Test
        @DisplayName("should return error when code parameter is missing")
        void shouldReturnErrorWhenCodeIsMissing() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("code_review");
            request.setParams(Map.of());

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error", containsString("'code' is required")));
        }

        @Test
        @DisplayName("should detect System.out.println usage")
        void shouldDetectSystemOutUsage() throws Exception {
            String badCode = "public class Test {\n    public void run() {\n        System.out.println(\"hello\");\n    }\n}";
            ToolRequest request = new ToolRequest();
            request.setName("code_review");
            request.setParams(Map.of("code", badCode));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.status", is("reviewed")))
                    .andExpect(jsonPath("$.result.totalIssues", greaterThanOrEqualTo(1)))
                    .andExpect(jsonPath("$.result.findings[0].severity", is("WARN")))
                    .andExpect(jsonPath("$.result.findings[0].category", is("LOGGING")));
        }

        @Test
        @DisplayName("should detect empty catch block")
        void shouldDetectEmptyCatchBlock() throws Exception {
            String badCode = "public class Test {\n    public void run() {\n        try { } catch(Exception e) { }\n    }\n}";
            ToolRequest request = new ToolRequest();
            request.setName("code_review");
            request.setParams(Map.of("code", badCode));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.findings[0].severity", is("ERROR")))
                    .andExpect(jsonPath("$.result.findings[0].category", is("ERROR_HANDLING")));
        }

        @Test
        @DisplayName("should return clean report for good code")
        void shouldReturnCleanReportForGoodCode() throws Exception {
            String cleanCode = "package com.example;\n\nimport org.springframework.stereotype.Service;\nimport lombok.RequiredArgsConstructor;\n\n@Service\n@RequiredArgsConstructor\npublic class UserService {\n\n    private final UserRepository userRepository;\n\n    public User findById(Long id) {\n        return userRepository.findById(id).orElse(null);\n    }\n}";
            ToolRequest request = new ToolRequest();
            request.setName("code_review");
            request.setParams(Map.of("code", cleanCode));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.status", is("reviewed")))
                    .andExpect(jsonPath("$.result.totalIssues", is(0)))
                    .andExpect(jsonPath("$.result.report", containsString("No issues found")));
        }

        @Test
        @DisplayName("should include line count in report")
        void shouldIncludeLineCountInReport() throws Exception {
            ToolRequest request = new ToolRequest();
            request.setName("code_review");
            request.setParams(Map.of("code", "public class Test {}\n"));

            mockMvc.perform(post("/mcp/tools")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.result.totalLines", is(1)));
        }
    }
}
