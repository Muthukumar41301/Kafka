# Apache Kafka in Spring Boot

## What is Apache Kafka?  
**Apache Kafka** is a distributed streaming platform designed for building **real-time data pipelines** and **event-driven applications**.  
It works as a **publish-subscribe messaging system** where producers send messages to topics, and consumers read those messages asynchronously.

In microservices, Kafka is widely used for **asynchronous, decoupled communication**, ensuring scalability and fault tolerance.

---

## Key Features
- ✅ **High Throughput & Low Latency** – Handles millions of messages per second.  
- ✅ **Scalable & Fault-Tolerant** – Distributed architecture with replication.  
- ✅ **Durability & Reliability** – Messages are persisted on disk.  
- ✅ **Decoupled Communication** – Producers and consumers are independent.  
- ✅ **Supports Real-Time Processing** – Works with stream processing frameworks.  

---

## Kafka Core Concepts
1. **Producer** – Publishes messages to topics.  
2. **Consumer** – Subscribes to topics and processes messages.  
3. **Broker** – Kafka server that stores and serves messages.  
4. **Topic** – Logical channel where messages are stored.  
5. **Partition** – Topics are divided into partitions for parallelism.  
6. **Consumer Group** – Set of consumers that share message consumption.

# 🔄 Kafka Retry Strategies & Dead Letter Topics

## 1️⃣ Why Retries are Needed
In Kafka, message processing can fail due to:
- Temporary downstream service unavailability.
- Data format or validation issues.
- External API/network failures.

Instead of losing these messages, we use **retry strategies** and **dead letter topics (DLT)** to handle failures gracefully.

---

## 2️⃣ Kafka Retry Strategies

### **A. Immediate Retries**
- Retry the message immediately upon failure.
- Usually implemented **inside the consumer code**.
- Drawback: Can block consumption of other messages (especially with partitions).

### **B. Delayed Retries (Backoff Retries)**
- Introduce a **wait time** before retrying.
- Can be exponential (e.g., 1s → 2s → 4s) or fixed delay.
- Implemented using:
  - **Spring Kafka RetryTemplate**
  - **ScheduledExecutorService**
  - **Separate Retry Topics**

### **C. Retry Topics Approach (Recommended)**
- Failed messages are sent to a **retry topic** with delayed processing.
- Retry topics can be chained (e.g., retry-1 → retry-2 → retry-3 → DLT).
- Benefits:
  - Non-blocking.
  - Fine-grained retry control per topic.

---

## 3️⃣ Dead Letter Topics (DLT)

### **Definition:**
A **Dead Letter Topic** is a Kafka topic where messages go after exceeding the retry limit.

### **Purpose:**
- Store **unrecoverable** messages.
- Allow manual inspection & reprocessing.
- Prevent bad messages from blocking consumers.

---

## 4️⃣ Example Flow

```plaintext
Kafka Topic: orders
    ↓
Consumer (processes message)
    ↓ Failure
Retry 1 Topic (delay 5s)
    ↓ Failure
Retry 2 Topic (delay 30s)
    ↓ Failure
Dead Letter Topic (DLT)
