# 🌊 California Water Billing RAG Assistant (2026)

![Architecture](https://img.shields.io/badge/Architecture-RAG-blue)
![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-green)
![Frontend](https://img.shields.io/badge/Frontend-Next.js-black)
![Platform](https://img.shields.io/badge/Platform-OpenShift-red)
![Database](https://img.shields.io/badge/DB-PostgreSQL-blue)

---

## 📌 Executive Summary

The California Water Billing Assistant is a production-grade AI solution designed to handle complex utility billing inquiries with 100% data grounding.

By combining Spring Boot, LangChain4j, and OpenShift AI, this system eliminates AI hallucinations by forcing the LLM to retrieve real 2026 water district policies and pricing rules before generating responses.

---

## 🏗️ Technical Architecture

- Frontend: Next.js 16+ (App Router)
- Backend: Spring Boot 3.5 (LangChain4j orchestration)
- Vector DB: PostgreSQL + pgvector
- Inference: Llama-4 Maverick (17B) on OpenShift AI
- Platform: Red Hat OpenShift

---

## 🔄 Architecture Flow

User Query → Next.js UI → Spring Boot API → LangChain4j  
→ Vector Search (pgvector)  
→ Retrieve 2026 Policy Data  
→ LLM (Llama-4)  
→ Grounded Response → UI  

---

## 📁 Project Structure

- /src: Spring Boot Backend  
- /water-ui: Next.js Frontend  
- /scripts: Data ingestion tools  
- /documentation: Architecture specs  

---

## 🚀 Why RAG is Critical

### ❌ Problem
Standard LLMs hallucinate outdated answers.

### ✅ Solution
RAG retrieves real-time 2026 policy data before answering.

### 🎯 Benefits
- Accurate billing calculations  
- No hallucinations  
- Reliable outputs  

---

## ⚙️ Setup & Deployment

### Database
CREATE EXTENSION IF NOT EXISTS vector;

### Backend
oc new-app java:17 --name=water-backend --binary
oc start-build water-backend --from-dir=.

### Frontend
oc new-app nodejs:20 --name=water-ui --binary
oc start-build water-ui --from-dir=./water-ui

### Env Config
oc set env deployment/water-backend DB_HOST=... AI_MODEL_NAME=...

---

## 🔄 Model Switching

Change model without rebuild:

oc set env deployment/water-backend AI_MODEL_NAME="llama-4-70b-pro"

---

## 🧪 Example

User: What is my bill for 15 CCF?  
System calculates using tier logic and returns accurate bill.

---

## 🛠️ Future Enhancements

- Hybrid Search  
- Citation UI  
- Chat Memory  
- Agentic RAG  

