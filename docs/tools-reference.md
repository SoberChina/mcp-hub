# Tools Reference

## spring_generate

Generates Spring Boot component code.

### Parameters

| Param | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `type` | String | ✅ | — | entity / repository / service / controller / dto / config |
| `name` | String | ✅ | — | Component name (without type suffix) |
| `package` | String | ❌ | com.example | Java package name |
| `fields` | List[Map] | ❌ | — | Field definitions (for entity/dto only) |

### type Examples

**entity:**
```json
{
  "name": "spring_generate",
  "params": {
    "type": "entity",
    "name": "User",
    "package": "com.example.entity",
    "fields": [
      {"name": "username", "type": "String", "column": "user_name"},
      {"name": "email", "type": "String"}
    ]
  }
}
```

**repository:** `{"name": "spring_generate", "params": {"type": "repository", "name": "User", "package": "com.example.repository"}}`

**service:** `{"name": "spring_generate", "params": {"type": "service", "name": "UserService", "package": "com.example.service"}}`

**controller:** `{"name": "spring_generate", "params": {"type": "controller", "name": "UserController", "package": "com.example.controller"}}`

**dto:** `{"name": "spring_generate", "params": {"type": "dto", "name": "CreateUserRequest", "package": "com.example.dto", "fields": [{"name": "username", "type": "String", "required": "true"}]}}`

**config:** `{"name": "spring_generate", "params": {"type": "config", "name": "CorsConfig", "package": "com.example.config"}}`

---

## java_compile

Validates Java source code compilation.

**Params:** `code` (String, required) — Java source code

---

## health_check

Returns server health status. No parameters required.
