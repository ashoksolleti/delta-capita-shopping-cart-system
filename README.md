# Shopping Cart System

A time-boxed reference solution for the **Shopping Cart System Interview Challenge**.

> **Tech stack**
> - Java 21 + Spring Boot 3 (core pricing logic + REST endpoint)
> - Node.js (Express) API layer that fronts the Java service
> - Python (optional) analytics utility
> - Docker Compose for running both services together

## Problem Recap

- Items arrive as a list of names: `["Apple", "Apple", "Banana"]`
- Prices (in GBP pence):
  - Apple — 35p
  - Banana — 20p
  - Melon — 50p (Buy-One-Get-One-Free)
  - Lime — 15p (Three for the price of two)

The API returns totals in pence and formatted as currency.

## Repo Structure

```
shopping-cart-system/
├─ README.md
├─ docker-compose.yml
├─ java-backend/
│  ├─ pom.xml
│  └─ src/
│     ├─ main/java/com/example/cart/
│     │  ├─ CartApplication.java
│     │  ├─ model/Item.java
│     │  ├─ dto/CartRequest.java
│     │  ├─ dto/CartTotalResponse.java
│     │  ├─ service/PricingService.java
│     │  └─ web/CartController.java
│     └─ test/java/com/example/cart/service/
│        └─ PricingServiceTest.java
├─ js-gateway/
│  ├─ package.json
│  └─ src/index.js
└─ python-utils/
   ├─ analyze.py
   └─ sample_data/carts.csv
```

## How to Run (Quickstart)

### Option A: Docker Compose (recommended)

```bash
docker compose up --build
# Java backend: http://localhost:8080
# JS API layer: http://localhost:3000
```

### Option B: Run locally

**Java backend**

```bash
cd java-backend
./mvnw spring-boot:run   # or: mvn spring-boot:run
```

**JS API**

```bash
cd js-gateway
npm install
# Set the Java base URL if not default:
# export JAVA_BASE_URL=http://localhost:8080
npm start
```

**Python analytics (optional)**

```bash
cd python-utils
python3 analyze.py sample_data/carts.csv
```

## API

### Java backend

`POST /api/v1/cart/total`

Request:
```json
{ "items": ["Apple","Banana","Melon","Melon","Lime","Lime","Lime"] }
```

Response:
```json
{
  "totalPence": 165,
  "totalFormatted": "£1.65"
}
```

### JS API (gateway)

- `POST /api/cart/:cartId/add` body: `{ "item": "Apple" }`
- `GET  /api/cart/:cartId/view`
- `POST /api/cart/:cartId/total` (computes total by delegating to Java backend)

> **Auth (optional):** set `BASIC_USER` and `BASIC_PASS` env vars to enable Basic Auth on the JS API.

## Design Notes (Time‑boxed)

- **Pricing** is coded centrally in the Java `PricingService` to avoid drift.
- **Validation** rejects unknown items (case-insensitive allowed for known ones).
- **Offers**:
  - BOGOF: charge `count - floor(count/2)`
  - 3-for-2: charge `count - floor(count/3)`
- **Currencies** are computed in integer pence to avoid floating-point issues.
- **Gateway** remains stateless; carts are in-memory maps keyed by `cartId` for demo.
- **Testing**: JUnit 5 coverage for core rules.

## Real-time Synchronization Challenge (Why it’s impossible as phrased)

> “Perfect real-time synchronization without latency, even during network outages” is **physically impossible**: during an outage, no network messages can be exchanged, so cross-node states cannot be proven equal in real time. Similarly, “zero drift tolerance” conflicts with the impossibility results for distributed consensus under partitions (CAP).

**Pragmatic approach**:
- **Offline-first** client with local state (IndexedDB) + **op logs**.
- **Bidirectional sync** via WebSockets when online; **store-and-forward** when offline.
- **Conflict-free replicated data types (CRDTs)** or **versioned totals** (e.g., vector clocks) to automatically converge.
- **Deterministic pricing** centralized in Java service; gateway passes only item names and receives the authoritative total (or share the same rules via WASM for client-side preview).
- **Drift detection**: include a `pricingVersion` hash in every total to detect mismatches.

These trade-offs yield **eventual** convergence with great UX, while acknowledging constraints.

## What I’d do with more time

- Persist carts in a DB (PostgreSQL/Redis), add idempotency keys, structured logging, OpenAPI docs, CI.
- Publish the pricing module as a library shared with other services; compile to WASM for clients.
- Add property-based tests for edge cases and fuzz inputs.
- Container healthchecks and graceful shutdowns.
- Rate limiting and API keys on the JS layer.

---

© 2025
