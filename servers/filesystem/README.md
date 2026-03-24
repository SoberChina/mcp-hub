# Filesystem MCP Server

A TypeScript MCP server for file system operations.

## Install

```bash
cd servers/filesystem
npm install
```

## Run

```bash
npm start
```

## Tools

| Tool | Description |
|------|-------------|
| `read_file` | Read file contents |
| `write_file` | Write content to file |
| `list_directory` | List directory contents |
| `create_directory` | Create a new directory |
| `delete_file` | Delete a file |

## Claude Code Configuration

Add to your Claude Code config:

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "node",
      "args": ["/path/to/servers/filesystem/dist/index.js"]
    }
  }
}
```
