# department-app
A project for departments and employees management

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management

## Usage
1. Clone this project, git clone https://github.com/MikalaiKazak/department-app.git
3. Build the war file with maven, mvn clean install
4. Start REST:\
    cd rest\
    mvn tomcat7:run
    
and try CURL requests like:
```
curl -X GET -v localhost:8060/department/ --- Return all departments

curl -X GET -v localhost:8060/department/1

curl -X DELETE -v localhost:8060/department/1

curl -X GET -v localhost:8060/employee/ --- Return all employees

curl -X GET -v localhost:8060/employee/1
```

##Explore Rest APIs
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
