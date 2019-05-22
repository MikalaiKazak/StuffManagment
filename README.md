# department-app
A project for staff managing

## Necessary tools
* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Docker 18.09.1](https://www.docker.com/)
* [Docker compose 1.23.2](https://docs.docker.com/compose/) 
* [Maven 3.6.0](https://maven.apache.org/)

## Getting started
* Clone this repo to your local machine using: ``` git clone https://github.com/MikalaiKazak/department-app.git ```

## Run application
To run the application go to the project root and execute the following commands:
1. Install docker --- sudo apt install docker-ce
2. Install docker-compose --- sudo apt install docker-compose
3. sudo apt-get install openjdk-8-jdk
4. sudo apt-get maven
5. sudo sh ./build.sh

## View Zipkin

To view Zipkin you need to go to the next URL: http://localhost:9411

## View Kibana

To view Kibana you need to go to the next URL: http://localhost:5601

## ElasticSearch

You can view ElasticSearch in : http://localhost:9200


## Explore Rest APIs

| URL | Description |
| --- | --- |
  | GET http://localhost:8081/department/ |                                    Return all departments
  | GET http://localhost:8081/department/{id} |                                Return department by identifier
  | DELETE http://localhost:8081/department/{id} |                             Delete department by identifier
  | POST http://localhost:8081/department/ |                                   Create new department (The values are sent in the request body)
  | PUT http://localhost:8081/department/ |                                    Update department (The values are sent in the request body)
  | GET http://localhost:8081/employee/ |                                      Return all employees          
  | GET http://localhost:8081/employee/{id} |                                  Return employee by identifier  
  | GET http://localhost:8081/employee/?date={date} |                          Return employees by date of birthday
  | GET http://localhost:8081/employee/?dateFrom={dateFrom}&dateTo={dateTo} |  Return employees with day of birthday in interval
  | DELETE http://localhost:8081/employee/{id} |                               Delete employee by edintifier
  | POST http://localhost:8081/employee/ |                                     Add new employee (The values are sent in the request body)
  | PUT http://localhost:8081/employee/ |                                      Update employee (The values are sent in the request body)

## Technology stack 
* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Spring Boot 2.1.5](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/)
* [Docker 18.09.1](https://www.docker.com/)
* [Docker compose 1.23.2](https://docs.docker.com/compose/) 
* [Maven 3.6.0](https://maven.apache.org/)
* [Zipkin Brave 5.6.3](https://github.com/apache/incubator-zipkin-brave)
* [ElasticSearch 6.7.0](https://www.elastic.co/) 
* [Logstash 6.7.0](https://www.elastic.co/products/logstash)
* [Kibana 6.7.0](https://www.elastic.co/products/kibana) 
  
