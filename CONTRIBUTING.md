# Contributing to MCP Hub

Welcome! We're excited that you want to contribute.

## Ways to Contribute

1. **Add a new MCP server** - Create a new directory under `/servers/` with your implementation
2. **Improve documentation** - Fix typos, add examples, translate
3. **Report bugs** - Open an issue with details
4. **Request features** - Suggest new MCP server ideas

## Steps to Add a New MCP Server

1. Fork this repository
2. Create a directory: `servers/your-server-name/`
3. Add README.md with description and usage
4. Add source code (main implementation)
5. Submit a Pull Request

## Server Template

```bash
servers/
  your-server-name/
    README.md          # Description, installation, usage
    package.json/      # (for JS/TS) or pom.xml (for Java)
    src/               # Source code
    tests/             # Tests
```

## Guidelines

- Follow existing code style
- Include README with clear instructions
- Add tests for core functionality
- Keep focused on MCP protocol compatibility

## License

By contributing, you agree that your contributions will be licensed under MIT License.
