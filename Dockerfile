FROM postgres:16.2

ENV POSTGRES_USER=folky_dev_user
ENV POSTGRES_PASSWORD=pass
ENV POSTGRES_DB=folky_dev_db

# Optional setting time
# ENV TZ=Europe/Warsaw

# Optional setting charset
# ENV LANG=en_US.utf8

# Optional giving additional settings
# COPY postgresql.conf /etc/postgresql/postgresql.conf

# Additional SQL commands used after database creation
# COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432