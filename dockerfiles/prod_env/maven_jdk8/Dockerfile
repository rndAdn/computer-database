# PROD
FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# ADD . /usr/src/app # On utilise docker volume a la place ^^
CMD mvn package




# docker build -t renaud/maven_prod:1.0 .
# docker volume create --name CDB_Volume
# docker run --name prod_maven -v CDB_Volume:/usr/src/app renaud/maven_prod:1.0
