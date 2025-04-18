#!/bin/bash

# Generate random username and password
DB_USER="user_$(head /dev/urandom | tr -dc a-z0-9 | head -c6)"
DB_PASS="$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c20)"
DB_ROOT_PASS="$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c20)"
DB_NAME="prod_db"

# Create or overwrite the .env file
cat <<EOF > .env
# Generated on $(date)
MYSQL_USER=$DB_USER
MYSQL_ROOT_PASSWORD=$DB_ROOT_PASS
MYSQL_PASSWORD=$DB_PASS
MYSQL_DATABASE=$DB_NAME
SPRING_DATASOURCE_USERNAME=$DB_USER
SPRING_DATASOURCE_PASSWORD=$DB_PASS
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/$DB_NAME
EOF

# Start Docker Compose
echo "Starting Docker Compose..."
docker-compose up --build -d