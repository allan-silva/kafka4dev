# kafka4dev [![Build Status](https://travis-ci.org/allan-silva/kafka4dev.svg?branch=master)](https://travis-ci.org/allan-silva/kafka4dev)
Docker kafka image for local development. Zookeeper and Kafka running in the same container.  

## Usage:
`docker run --name local_kafka -p 2181:2181 -p 9092:9092 allansilva/kafka4dev`  

## Tags:
- Kafka 1.0.2 with Scala 2.12 build:
    `docker run --name local_kafka -p 2181:2181 -p 9092:9092 allansilva/kafka4dev:1.0.2-scala.2.12`  
- Kafka 2.2.0 with Scala 2.12 build:
    `docker run --name local_kafka -p 2181:2181 -p 9092:9092 allansilva/kafka4dev:2.2.0-scala.2.12`  
