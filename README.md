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
