package com.nikolay.rest;

import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
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
    private EmployeeService employeeService;

    /**
     * Instantiates a new Employee rest controller.
     *
     * @param employeeService the employee service
     */
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Gets all employees.
     *
     * @return the all employees
     */
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                          @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
                                                          @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {
        if (dateFrom != null && dateTo != null) {
            List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
            return new ResponseEntity<>(employees, HttpStatus.FOUND);
        } else if (date != null) {
            List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(date);
            return new ResponseEntity<>(employees, HttpStatus.FOUND);
        }
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
     * Update employee response entity.
     *
     * @param newEmployee the new employee
     * @param id          the id
     * @return the response entity
     */
    @PutMapping("/{id}")
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
