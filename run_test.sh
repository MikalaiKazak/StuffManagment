docker-compose up -d

cd functional-test/

port=$(docker inspect --format='{{(index (index .NetworkSettings.Ports "8080/tcp") 0).HostPort}}' rest-server )
base=/
host=http://127.0.0.1

url="$host:$port$bash rse"

attempt=0

while true; do
  if curl --output /dev/null --silent --head "$url"; then
   mvn test -Pfun-test -Dserver.port=$port -Dserver.base=$base -Dserver.host=$host
   break
  fi
  if [ "$attempt" -eq "25" ]; then
    break
  fi
  attempt=$((attempt+1))
  sleep 5
done

docker-compose down