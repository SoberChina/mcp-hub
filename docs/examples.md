# Examples 🦞

## Example 1: Full CRUD in One Shot

In Claude Code, say:

> "Generate a BlogPost Entity with fields: title(String, required, max 200), content(Text), author(String), publishedAt(LocalDateTime). Then generate the full CRUD stack."

MCP Server generates:
- `BlogPost.java` — JPA Entity
- `BlogPostRepository.java` — Spring Data JPA Repository
- `BlogPostService.java` — Business logic
- `BlogPostController.java` — REST API Controller
- `CreateBlogPostRequest.java` — Request DTO
- `CorsConfig.java` — CORS config

---

## Example 2: Step by Step

**Step 1: Entity**
```
Generate a Product entity with: productName(String), sku(String), quantity(Integer), price(BigDecimal)
```

**Step 2: Repository**
```
Generate the Product repository
```

**Step 3: Service + Controller**
```
Generate the ProductService and ProductController
```

---

## Example 3: Real Project Scenario

Building an inventory management system — in Claude Code:

```
For the product module, generate complete code:
- Product Entity: productName, sku(unique), quantity, price
- ProductRepository: with findBySku method
- ProductService: with stock increase/decrease methods
- ProductController: REST API
- DTOs: CreateProductRequest, UpdateStockRequest
```

---

## Comparison: With vs Without MCP Hub

| Task | Manual | With MCP Hub |
|------|--------|-------------|
| Generate one Entity | ~3 min | 3 sec |
| Full CRUD module | ~30 min | 30 sec |
| Check docs + write code | Always needed | Auto |

## Next Steps

- 🔧 Want to add a new tool? See [CONTRIBUTING.md](../CONTRIBUTING.md)
- 📖 More: [Tools Reference](./tools-reference.md)
