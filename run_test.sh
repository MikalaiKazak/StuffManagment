mkdir -p /tmp/logs/rest_logs /tmp/logs/webapp_logs
docker-compose up -d

cd functional-test/
mvn test -Pfun-test

docker-compose down