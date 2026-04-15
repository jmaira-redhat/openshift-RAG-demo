# California Water Billing RAG Assistant (2026)

This project is an end-to-end Retrieval-Augmented Generation (RAG) system deployed on OpenShift.

## Project Structure
- **/src**: Spring Boot 3.5 Backend (Java 17)
- **/water-ui**: Next.js 16 Frontend
- **/scripts**: Data ingestion tools for PGVector
- **/documentation**: Architecture specs and NotebookLM sources

## Deployment Quick-Start
1. **Backend**: `oc new-app java:17 --name=water-backend --binary && oc start-build water-backend --from-dir=.`
2. **Frontend**: `oc new-app nodejs:20 --name=water-ui --binary && oc start-build water-ui --from-dir=./water-ui`

## Environment Variables
Required for the backend:
- `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASS`
- `AI_BASE_URL`, `AI_MODEL_NAME`
