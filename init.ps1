# Generate random strings for user and password
$DB_USER = "user_" + -join ((48..57) + (97..122) | Get-Random -Count 6 | ForEach-Object {[char]$_})
$DB_PASS = -join ((65..90) + (97..122) + (48..57) | Get-Random -Count 20 | ForEach-Object {[char]$_})
$DB_ROOT_PASS = -join ((65..90) + (97..122) + (48..57) | Get-Random -Count 20 | ForEach-Object {[char]$_})
$DB_NAME = "prod_db"

# Create content for the .env file
$envContent = @"
# Generated on $(Get-Date)
MYSQL_USER=$DB_USER
MYSQL_ROOT_PASSWORD=$DB_ROOT_PASS
MYSQL_PASSWORD=$DB_PASS
MYSQL_DATABASE=$DB_NAME
SPRING_DATASOURCE_USERNAME=$DB_USER
SPRING_DATASOURCE_PASSWORD=$DB_PASS
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/$DB_NAME
"@

# Write to .env file
$envContent | Set-Content -Path ".env" -Encoding UTF8

# Start Docker Compose
Write-Host "Starting Docker Compose..."
docker-compose up --build -d