FROM mysql:8.0

VOLUME /mySQL
ENV MYSQL_ROOT_PASSWORD=root

# Add sql file in entryPoint to populate DB in docker run
ADD ./1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
ADD ./2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
ADD ./3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql


# CMD /etc/init.d/mysql start


# RUN
# docker run -i --name test_mysql renaud/mysql:1.0
