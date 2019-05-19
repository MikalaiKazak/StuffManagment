# department-app
A project for departments and employees management

## Built With
* JDK 8
* [Docker 18.09.1](https://www.docker.com/) - Docker
* [Docker compose 1.23.2](https://docs.docker.com/compose/) - Docker Compose
* [Maven 3.6.0](https://maven.apache.org/) - Dependency Management
* [Apache Tomcat 9.0.13](http://tomcat.apache.org/) - Tomcat
* [Zipkin Brave 5.6.3](https://github.com/apache/incubator-zipkin-brave) Zipkin
* [ElasticSearch 6.7.0](https://www.elastic.co/) ElasticSearch
* [Logstash 6.7.0](https://www.elastic.co/products/logstash) Logstash
* [Kibana 6.7.0](https://www.elastic.co/products/kibana) Kibana 

## Get project
* Clone this project, ``` git clone https://github.com/MikalaiKazak/department-app.git ```

## Run through tomcat 
1. Install Tomcat --- sudo apt-get install tomcat9
2. Modify tomcat-users.xml file accordingly
```
 <role rolename="manager-script"/>  
 <role rolename="manager-gui"/>  
 <role rolename="manager-jmx"/>  
 <role rolename="manager-status"/>  
 <user username="tomcat" password="tomcat" roles="manager-gui,manager-jmx,manager-script,manager-status"/>  
```
3. Start the Tomcat server:sudo service tomcat run

## Deploy on tomcat
1. Build the war file with maven, mvn clean install
2. Copy war-files which you can find in ``` /rest/target/rest.war ``` and ``` /webapp/target/webapp.war``` to your tomcat server webapps folder.
3. Restart the Tomcat server: sudo service tomcat restart
4. And then you can see result in browser: http://localhost:8080/webapp/ For REST service: http://localhost:8080/rest/department/ or http://localhost:8080/rest/employee

## Run through docker
1. Install docker --- sudo apt install docker-ce
2. Install docker-compose --- sudo apt install docker-compose
3. sudo chmod +x build.sh
4. ./build.sh
5. And then you can see result in browser: http://localhost:8082/webapp/ For Rest service: http://localhost:8081/rest/department or http://localhost:8081/rest/employee

## Open Zipkin

You can open Zipkin in the browser: http://localhost:9411

## Open Kibana

You can open Kibana in the browser: http://localhost:5601

## Open Logstash

You can open Logstash in the browser: http://localhost:9600

## Open ElasticSearch

You can open ElasticSearch in the browser: http://localhost:9200


## Explore Rest APIs

```
curl -X GET -v http://localhost:8080/rest/department/ --- Return all departments

curl -X GET -v http://localhost:8080/rest/department/0

curl -X DELETE -v http://localhost:8080/rest/department/0

curl -X GET -v http://localhost:8080/employee/ --- Return all employees

curl -X GET -v http://localhost:8080/rest/employee/0

```

| URL | Description |
| --- | --- |
  | GET http://localhost:8080/rest/department/ |                                    Return all departments
  | GET http://localhost:8080/rest/department/{id} |                                Return department by identifier
  | DELETE http://localhost:8080/rest/department/{id} |                             Delete department by identifier
  | POST http://localhost:8080/rest/department/ |                                   Create new department (The values are sent in the request body)
  | PUT http://localhost:8080/rest/department/ |                                    Update department (The values are sent in the request body)
  | GET http://localhost:8080/rest/employee/ |                                      Return all employees          
  | GET http://localhost:8080/rest/employee/{id} |                                  Return employee by identifier  
  | GET http://localhost:8080/rest/employee/?date={date} |                          Return employees by date of birthday
  | GET http://localhost:8080/rest/employee/?dateFrom={dateFrom}&dateTo={dateTo} |  Return employees with day of birthday in interval
  | DELETE http://localhost:8080/rest/employee/{id} |                               Delete employee by edintifier
  | POST http://localhost:8080/rest/employee/ |                                     Add new employee (The values are sent in the request body)
  | PUT http://localhost:8080/rest/employee/ |                                      Update employee (The values are sent in the request body)
  
