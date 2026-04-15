# 🚀 OpenShift Deployment Command Reference

This guide provides step-by-step commands to deploy the **California Water Billing RAG Assistant** on OpenShift.

---

## 📦 Phase 1: Database & Secrets

Before deploying applications, ensure storage and credentials exist.

### Create the Vector Database

```bash
oc new-app centos/postgresql-12-centos7 --name=water-db
```

### Create Secret for Credentials

```bash
oc create secret generic water-db-creds   --from-literal=password=calif-water-pass-2026
```

---

## ⚙️ Phase 2: Backend (Spring Boot)

### Initialize Binary Build

```bash
oc new-app java:17 --name=water-backend --binary
```

### Trigger Build

```bash
oc start-build water-backend --from-dir=. --follow
```

### Inject Environment Variables

```bash
oc set env deployment/water-backend     DB_HOST="water-db-service"     DB_NAME="water_billing"     DB_USER="wateradmin"     DB_PASS="calif-water-pass-2026"     AI_BASE_URL="http://llama-service-api/v1"     AI_MODEL_NAME="llama-4-maverick-17b"
```

---

## 🌐 Phase 3: Frontend (Next.js)

### Initialize Binary Build

```bash
oc new-app nodejs:20 --name=water-ui --binary
```

### Trigger Build

```bash
oc start-build water-ui --from-dir=./water-ui --follow
```

### Fix Service Port (8080 → 3000)

```bash
oc patch svc/water-ui -p '{"spec":{"ports":[{"port":8080,"targetPort":3000}]}}'
```

### Link Frontend to Backend

```bash
oc set env deployment/water-ui   BACKEND_URL="http://$(oc get route water-backend -o jsonpath='{.spec.host}')"
```

---

## 🌍 Phase 4: Traffic & Exposure

### Expose UI to Public Internet

```bash
oc expose svc/water-ui
```

### Retrieve Application URL

```bash
oc get route water-ui --template='http://{{.spec.host}}{{"\n"}}'
```

---

## 🛠️ Phase 5: Maintenance & Debugging

### Check Pod Health

```bash
oc get pods
```

### Stream Logs

```bash
oc logs -f deployment/water-backend
```

### Restart Deployment

```bash
oc rollout restart deployment/water-ui
```

### Access Running Pod

```bash
oc rsh <pod-name>
```

---

## ✅ Summary

This deployment flow covers:

- Database setup with PostgreSQL + pgvector
- Backend deployment (Spring Boot + LangChain4j)
- Frontend deployment (Next.js)
- Routing & exposure via OpenShift
- Debugging and operational commands

---

**Tip:** Keep credentials in OpenShift Secrets for production instead of inline environment variables.
