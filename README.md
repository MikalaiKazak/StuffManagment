# department-app
A project for departments and employees management

## Built With
* JDK 8
* [Maven](https://maven.apache.org/) - Dependency Management
* [Tomcat](http://tomcat.apache.org/) - Tomcat


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
curl -X GET -v localhost:8080/department/ --- Return all departments

curl -X GET -v localhost:8080/department/1

curl -X DELETE -v localhost:8080/department/1

curl -X GET -v localhost:8080/employee/ --- Return all employees

curl -X GET -v localhost:8080/employee/1

```

| URL | Description |
| --- | --- |
  | GET /department/ |                                    Return all departments
  | GET /department/{id} |                                Return department by identifier
  | DELETE /department/{id} |                             Delete department by identifier
  | POST /department/ |                                   Create new department (The values are sent in the request body)
  | PUT /department/ |                                    Update department (The values are sent in the request body)
  | GET /employee/ |                                      Return all employees          
  | GET /employee/{id} |                                  Return employee by identifier  
  | GET /employee/?date={date} |                          Return employees by date of birthday
  | GET /employee/?dateFrom={dateFrom}&dateTo={dateTo} |  Return employees with day of birthday in interval
  | DELETE /employee/{id} |                               Delete employee by edintifier
  | POST /employee/ |                                     Add new employee (The values are sent in the request body)
  | PUT /employee/ |                                      Update employee (The values are sent in the request body)
  
