sed -i -r 's/^broker\.id.*$/broker.id=42/g' "$KAFKA_DIR/kafka_2.12-1.0.2/config/server.properties"
sed -i -r 's/^.advertised\.listeners.*$/advertised.listeners=PLAINTEXT:\/\/localhost:9092/g' "$KAFKA_DIR/kafka_2.12-1.0.2/config/server.properties"

/kafka/kafka_2.12-1.0.2/bin/kafka-server-start.sh /kafka/kafka_2.12-1.0.2/config/server.properties