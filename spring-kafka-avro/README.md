# 📌 Kafka Schema Registry & Avro

## 1️⃣ What is Avro?
**Apache Avro** is a data serialization system that provides:
- **Compact** and **fast** binary data format.
- **Schema-based** serialization, meaning the data structure is defined in a **JSON schema**.
- **Language independent** — can be used with Java, Python, C++, etc.

### ✅ Key Benefits:
- Reduces payload size compared to JSON/XML.
- Enforces schema for strong data typing.
- Ensures forward and backward compatibility when schema evolves.

---

## 2️⃣ What is Kafka Schema Registry?
**Schema Registry** is a centralized service that stores and retrieves schemas for Kafka topics.

### **Purpose:**
- Ensures **producers** and **consumers** agree on data structure.
- Prevents data corruption due to schema mismatch.
- Supports **schema evolution** (backward, forward, full compatibility modes).

---

## 3️⃣ How They Work Together
1. **Producer**
    - Serializes messages using **Avro**.
    - Registers the schema with **Schema Registry** (if not already stored).
    - Sends the message (with schema ID) to **Kafka**.

2. **Consumer**
    - Fetches the schema from **Schema Registry** using the schema ID in the message.
    - Deserializes the Avro message into an object.

---

## 4️⃣ Typical Flow Diagram

```plaintext
Producer App
    ↓
[KafkaAvroSerializer] → Register schema → Schema Registry
    ↓
Kafka Broker (Topic)
    ↓
Consumer App
    ↓
Fetch schema → [KafkaAvroDeserializer] → Deserialize
