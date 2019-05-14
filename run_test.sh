docker-compose up -d

cd functional-test/
mvn test -Pfun-test

docker-compose down
