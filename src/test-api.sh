#!/bin/bash
echo "Testing Water RAG API..."
curl -s -X POST http://localhost:8080/api/water/chat -H "Content-Type: application/json" -d "{\"message\": \"$1\"}" | jq .
