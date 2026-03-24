# Getting Started with MCP

## What is MCP?

MCP (Model Context Protocol) enables AI assistants to connect to external tools and data sources through a standardized interface.

## Installation

```bash
npm install @modelcontextprotocol/sdk
```

## Basic Example

```typescript
import { Server } from '@modelcontextprotocol/sdk';

const server = new Server({
  name: 'my-mcp-server',
  version: '1.0.0',
}, {
  capabilities: {
    tools: {},
    resources: {},
  },
});

server.start();
```

## Resources

- [Official MCP Documentation](https://modelcontextprotocol.io)
- [MCP SDK Reference](https://github.com/modelcontextprotocol/sdk)
