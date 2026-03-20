# 🔍 ScanForge

AI-powered distributed code scanning platform built with microservices, Kafka event streaming, and real-time WebSocket updates.

---

## 🚀 Overview

ScanForge is a **scalable, event-driven platform** that analyzes uploaded codebases using a distributed pipeline powered by Kafka and AI.

It demonstrates real-world backend engineering concepts including:

- Microservices architecture
- Event-driven communication (Kafka)
- Real-time updates via WebSockets
- AI-powered code analysis
- Full-stack integration with Next.js

---

## 🏗️ Architecture
      ┌──────────────┐
      │  Next.js UI  │
      └──────┬───────┘
             │ HTTP (Upload)
             ▼
    ┌──────────────────┐
    │   API Service    │
    └────────┬─────────┘
             │ Kafka (scan-request)
             ▼
    ┌──────────────────┐
    │ Scanner Service  │
    └────────┬─────────┘
             │ Kafka (scan-detected)
             ▼
    ┌──────────────────┐
    │    AI Service    │
    └────────┬─────────┘
             │ Kafka (scan-enriched)
             ▼
    ┌──────────────────┐
    │ Result Service   │
    └────────┬─────────┘
             │ WebSocket (/topic/scan/{id})
             ▼
      ┌──────────────┐
      │   Frontend   │
      └──────────────┘

---

## ⚡ Flow

1. User uploads a ZIP file from the frontend
2. API Service generates a `scanId` and sends event → Kafka
3. Scanner Service processes files and emits detection events
4. AI Service enriches results with analysis
5. Result Service:
    - Stores results
    - Publishes real-time updates via WebSocket
6. Frontend subscribes and updates UI live

---

## 🧰 Tech Stack

### Backend
- Java + Spring Boot
- Apache Kafka
- WebSockets (STOMP + SockJS)

### Frontend
- Next.js (App Router)
- Tailwind CSS v4
- shadcn/ui

### Infrastructure
- Docker (planned)
- Event-driven microservices

---

## 📸 Screenshots

> Add screenshots here after deployment

Examples:
- Upload UI
- Real-time status updates
- Scan results

---

## ▶️ Getting Started

### 1. Clone the repo

```bash
    git clone https://github.com/jeet7122/scanforge.git
    cd scanforge
```

### 2. Start Docker
```bash
    docker-compose up -d
```

### 3. Run Frontend
```bash
    cd scanforge-ui
    npm install
    npm run dev
```

### 4. Open Browser
```url
    http://localhost:3000
 ```

#### WebSockets Endpoint
```url
    ws://localhost:8083/ws
```

##### Topic:
```url
    /topic/scan/{scanId}
```

---
## 🧠 Key Features

* Real-time scan status updates 
* Distributed processing pipeline 
* Event-driven architecture with Kafka 
* AI-based issue detection 
* Clean separation of services

---

## 📌 Future Improvements

* Authentication & multi-user support 
* Persistent database storage 
* CI/CD pipeline 
* Cloud deployment (AWS/GCP)
* Rate limiting & API keys
---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---
## 👨‍💻 Author

Jeet Thakkar