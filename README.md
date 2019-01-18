# department-app
A project for departments and employees management

## Built With
* JDK 8
* [Maven 3.6.0](https://maven.apache.org/) - Dependency Management
* [Apache Tomcat 9.0.13](http://tomcat.apache.org/) - Tomcat


## Tomcat 
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

## Usage
1. Clone this project, git clone https://github.com/MikalaiKazak/department-app.git
2. Build the war file with maven, mvn clean install
3. Copy war-files which you can find in ``` /rest/target/rest.war ``` and ``` /webapp/target/web.war``` to your tomcat server webapps folder.
4. Restart the Tomcat server: sudo service tomcat restart
5. And then you can see result in browser: ```http://localhost:8080/web/``` For REST service:``` http://localhost:8080/rest/department/```


## Explore Webapp Content
| URL | Description |
| --- | --- |
  | http://localhost:8080/webapp/ |                    Main page
  | http://localhost:8080/webapp/departments |         Page view all departments
  | http://localhost:8080/webapp/employees |           Page view all employees

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
  