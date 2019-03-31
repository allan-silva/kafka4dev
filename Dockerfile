FROM debian:stable

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

RUN apt-get update && \
    apt-get install -y default-jdk wget supervisor

COPY ./start_kafka.sh /kafka/
COPY ./binaries/zookeeper/zookeeper-3.4.13.tar.gz /kafka/
COPY ./binaries/2.2.0/kafka_2.12-2.2.0.tgz /kafka/
COPY ./supervisor/* /etc/supervisor/conf.d/

WORKDIR /kafka

RUN chmod +x start_kafka.sh
RUN tar -xzf zookeeper-3.4.13.tar.gz && rm zookeeper-3.4.13.tar.gz
RUN tar -xzf kafka_2.12-2.2.0.tgz && rm kafka_2.12-2.2.0.tgz

RUN mv zookeeper-3.4.13/conf/zoo_sample.cfg zookeeper-3.4.13/conf/zoo.cfg
RUN mkdir /tmp/zookeeper

EXPOSE 2181 9092

CMD [ "supervisord", "-n" ]
