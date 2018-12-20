# department-app
A project for departments and employees management

## Usage
1. Clone this project, git clone https://github.com/MikalaiKazak/department-app.git
3. Build the war file with maven, mvn clean install
4. Start REST:\
    cd rest\
    mvn tomcat7:run
    
and try CURL requests like:

curl -X GET -v localhost:8060/department/ --- Return all departments

curl -X GET -v localhost:8060/department/1 --- Return department by identifier

curl -H "Content-Type: application/json" -X POST 
        -d '{"departmentName":"Services"}' 
        -v localhost:8088/department/ --- Create new department

curl -H "Content-Type: application/json" -X PUT 
             -d '{"id":"1", "departmentName":"Services"}' 
             -v localhost:8088/department/ --- Update department

curl -X DELETE -v localhost:8060/department/1 --- Remove department by identifier

curl -X GET -v localhost:8060/employee/ --- Return all employees

curl -X GET -v localhost:8060/employee/1 --- Return employee by identifier   

curl -X GET -v localhost:8060/employee/?date='1999-02-02' --- Return employees by date of birthday

curl -X GET -v localhost:8060/employee/?dateFrom='1999-02-02'&dateTo='2000-02-02' --- Return employees with date of birthday in interval
curl -H "Content-Type: application/json" -X POST 
        -d '{"departmentId":"1", "fullName":"Nikolay", "birthday":"1999-02-02","salary":"300"}' 
        -v localhost:8088/employee/ --- Create new employee

curl -X DELETE -v localhost:8060/employee/1 --- Delete employee by identifier

curl -H "Content-Type: application/json" -X PUT 
        -d '{"id":"1", "departmentId":"1", "fullName":"Nikolay", "birthday":"1999-02-02","salary":"300"}' 
        -v localhost:8088/employee/  --- Update employee
