# PROD
FROM tomcat:7.0-jre8
RUN mkdir -p /cdb
# VOLUME ["CDB_Volume:/cdb"]
#ONBUILD RUN cp -a /cdb/target/computerdb.war /usr/local/tomcat/webapps
# RUN cp /cdb/target/computerdb.war /usr/local/tomcat/

# CMD /etc/init.d/mysql start


# RUN
# docker volume create --name WAR_CDB
# docker build -t radequin/tomcat_prod:1.0 .
# docker run --network=network_prod -it -p 8888:8080 --name prod_tomcat -v WAR_CDB:/usr/local/tomcat/webapps radequin/tomcat_prod:1.0
