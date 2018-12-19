package com.nikolay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Employee rest controller.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    /**
     * The Employee service.
     */
    @Autowired
    EmployeeService employeeService;

    /**
     * Gets all employees.
     *
     * @return the all employees
     */
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Gets employee by id.
     *
     * @param id the id
     * @return the employee by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.FOUND);
    }

    /**
     * Add employee response entity.
     *
     * @param employee the employee
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity<Long> addEmployee(@RequestBody Employee employee) {
        Long id = employeeService.saveEmployee(employee);
        employee.setId(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * Remove employee response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity removeEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets employee by date of birthday.
     *
     * @param date the date
     * @return the employee by date of birthday
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Employee>> getEmployeeByDateOfBirthday(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(date);
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }

    /**
     * Gets employee between dates of birthday.
     *
     * @param from the from
     * @param to   the to
     * @return the employee between dates of birthday
     */
    @GetMapping("/date/{from}/{to}")
    public ResponseEntity<List<Employee>> getEmployeeBetweenDatesOfBirthday(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(from, to);
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }

    /**
     * Update employee response entity.
     *
     * @param newEmployee the new employee
     * @param id          the id
     * @return the response entity
     */
    @PostMapping("/{id}")
    public ResponseEntity updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        employee.setDepartmentId(newEmployee.getDepartmentId());
        employee.setFullName(newEmployee.getFullName());
        employee.setBirthday(newEmployee.getBirthday());
        employee.setSalary(newEmployee.getSalary());
        employeeService.updateEmployee(employee);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
