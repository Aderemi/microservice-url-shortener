FROM mysql:8
MAINTAINER dayo.aderemi@andela.com

ENV MYSQL_DATABASE=urldb \
    MYSQL_ROOT_PASSWORD=pa55w0rd

ADD preloaded-schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306
