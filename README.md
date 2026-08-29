# Enterprise Mission-Critical Integration Architecture — Android to WebSphere 9

The project demonstrates how to modernize the integration and observability layer around a legacy enterprise platform without replacing the underlying system.

It combines:

Legacy Enterprise Systems + REST + Java EE + DB2 + Distributed Tracing + Containerization + Performance Engineering

with a practical focus on scalability, observability, resource management, and resilience.

---

## Problem

Modern mobile applications often need to integrate with mission-critical legacy systems that cannot be easily replaced due to operational risk, business complexity, and high migration costs.

This project addresses the challenge of exposing legacy enterprise services through a secure and resilient REST API while preserving the stability of the existing IBM WebSphere Application Server Traditional 9 environment.

The architecture also focuses on validating the backend under concurrent load, database pressure, and resource contention.

---

## Solution

Designed and implemented an end-to-end integration architecture connecting an Android application to a REST API hosted on IBM WebSphere Application Server 9.

The solution includes:

REST API for CRUD operations.
Integration with IBM DB2.
OpenTelemetry instrumentation for distributed observability.
Automatic collection of application traces and JVM metrics.
JMeter load testing for concurrent request simulation.
Validation of WebSphere thread pools and DB2 connection pools.
Containerized development environment using Docker Compose.
Separation between application, database, and observability components.
The architecture is designed to expose legacy services without requiring a rewrite of the underlying enterprise platform.

---

## Functionalities

Backend
- RESTful CRUD operations.
- Customer management.
- DB2 persistence.
- JDBC integration.
- HTTP request handling.
- Database query instrumentation.
- Distributed tracing.

Observability
- HTTP request tracing.
- JDBC/database tracing.
- JVM metrics.
- Service metadata and resource attributes.
- OpenTelemetry Collector.
-  exporter for trace validation during development.

Performance & Resilience
-  Concurrent load testing with Apache JMeter.
-  CRUD performance validation.
-  Thread Pool behavior analysis.
-  DB2 Connection Pool analysis.
-  Response-time and throughput analysis.
-  Identification of saturation points.
-  Recovery behavior under high concurrency.


---

## Stack


- Layer	Technology
- Mobile	Android
- Java / Java EE
- Server	IBM WebSphere Application Server Traditional 9
- Java Runtime	Java SE 8
- Database	IBM DB2
- 	REST
- 	JDBC
- Observability	OpenTelemetry
- Telemetry Agent	OpenTelemetry Java Agent
- Collector	OpenTelemetry Collector Contrib
- Load Testing	Apache JMeter
- Containers	Docker / Docker Compose
- OS Environment	Linux / WSL2 / Docker Desktop


---

## Architecture

```text

                    ┌─────────────────────┐
                    │    Android Client   │
                    └──────────┬──────────┘
                               │
                               │ HTTPS / REST
                               ▼

                    ┌─────────────────────┐
                    │   WebSphere WAS 9   │
                    │                     │
                    │  REST API / Java EE │
                    │                     │
                    │ OpenTelemetry Agent │
                    └───────┬───────┬─────┘
                            │       │
                     JDBC   │       │ OTLP
                            │       ▼
                            │  ┌──────────────────┐
                            │  │ OTel Collector   │
                            │  └──────────────────┘
                            │
                            ▼
                    ┌─────────────────────┐
                    │       IBM DB2       │
                    │                     │
                    │      testdb         │
                    └─────────────────────┘


             Performance Validation
             ───────────────────────

                    ┌─────────────────┐
                    │    Apache       │
                    │     JMeter      │
                    └────────┬────────┘
                             │
                    Concurrent Requests
                             │
                             ▼
                    ┌─────────────────┐
                    │   WebSphere 9   │
                    │                 │
                    │ Thread Pool      │
                    │ Connection Pool  │
                    └────────┬────────┘
                             │
                             ▼
                           DB2

```

## Engineering Focus

The main engineering objective is not simply to make the CRUD API work, but to understand how the complete system behaves under increasing concurrency.

The load-testing strategy evaluates:

- Throughput: requests processed per second.
- Latency: response-time distribution under load.
- Concurrency: behavior with multiple simultaneous clients.
- WebSphere Thread Pool: utilization and saturation.
- DB2 Connection Pool: connection utilization and contention.
- JVM: CPU and memory behavior.
- Database: query execution and response time.
- Resilience: behavior when infrastructure resources become constrained.



## Load Test Strategy

Apache JMeter is used to simulate concurrent mobile clients performing CRUD operations.

Example workload:

JMeter

```text
JMeter
   │
   ├── GET /customers/findAll
   ├── GET /customers/{id}
   ├── POST /customers
   ├── PUT /customers/{id}
   └── DELETE /customers/{id}
             │
             ▼
        WebSphere 9
             │
             ▼
            DB2
```

The objective is to progressively increase concurrency and identify the system's saturation point before failures such as connection exhaustion, excessive latency, HTTP errors, or OutOfMemoryError.


```text
WebSphere JVM
     │
     │ OpenTelemetry Java Agent
     │
     ├── HTTP Spans
     ├── JDBC Spans
     └── JVM Metrics
             │
             │ OTLP / gRPC
             ▼
      OpenTelemetry Collector
             │
             ▼
       Debug / Backend
```

## How to Run

Status: This project is currently in early development.

Prerequisites

Make sure the following tools are installed and available in your environment:

Docker
Docker Compose
Java / Maven
IBM WebSphere Application Server 9
Apache JMeter


1. Build the WebSphere Docker Image

Build the WebSphere Docker image using Docker Buildx:

docker buildx build --load -t websphere-otel:9.0.5.24 .

2. Start the Application Stack

Start the application stack in detached mode:

docker compose up -d


Check the running containers:

docker compose ps


The expected services are:

websphere
db2
otel-collector


3. Build the Java Application

Build the Java application using Maven:

mvn clean package


The generated WAR file will be available under:

target/

4. Deploy to WebSphere

Deploy the generated .war file to IBM WebSphere Application Server 9.

After deployment, the REST API can be accessed through the HTTP/HTTPS ports configured for the WebSphere application server.


5. Validate OpenTelemetry

The application is instrumented with the OpenTelemetry Java Agent.

To verify that telemetry is being collected, check the OpenTelemetry Collector logs:

docker logs --tail 100 otel-collector


HTTP requests and JDBC operations should appear as OpenTelemetry traces.


6. Run Load Tests

Apache JMeter
 can be used to simulate concurrent clients and evaluate application performance under load.

The following metrics can be monitored during load tests:

API response time
Throughput
HTTP error rate
WebSphere thread pool utilization
DB2 connection pool behavior
JVM CPU utilization
JVM memory usage
OpenTelemetry traces during load





