FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

CMD mvn test




# docker build -t renaud/maven_test:1.0 .
# docker volume create --name CDB_Volume
# docker run --network=test_network --name test_maven -v CDB_Volume:/usr/src/app renaud/maven_test:1.0
