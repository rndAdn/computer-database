# PROD
FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# ADD . /usr/src/app # On utilise docker volume a la place ^^
CMD mvn package




# docker build -t radequin/maven_prod:1.0 .
# docker volume create --name CDB_Volume
# docker run --network=network_prod --name prod_maven -v CDB_Volume:/usr/src/app radequin/maven_prod:1.0
