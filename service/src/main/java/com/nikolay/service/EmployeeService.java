package com.nikolay.service;

import com.nikolay.service.exception.EmployeeNotFoundException;
import com.nikolay.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Employee service.
 */
public interface EmployeeService {

    /**
     * Gets employee by id.
     *
     * @param employeeId the employee id
     * @return the employee by id
     * @throws EmployeeNotFoundException the employee not found exception
     */
    Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Save employee long.
     *
     * @param employee the employee
     * @return the long
     * @throws EmployeeNotFoundException the employee not found exception
     */
    Long saveEmployee(Employee employee) throws EmployeeNotFoundException;

    /**
     * Update employee.
     *
     * @param employee the employee
     * @throws EmployeeNotFoundException the employee not found exception
     */
    void updateEmployee(Employee employee) throws EmployeeNotFoundException;

    /**
     * Delete employee.
     *
     * @param employeeId the employee id
     * @throws EmployeeNotFoundException the employee not found exception
     */
    void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Gets all employees.
     *
     * @return the all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by date of birthday.
     *
     * @param date the date
     * @return the employee by date of birthday
     */
    List<Employee> getEmployeeByDateOfBirthday(LocalDate date);

    /**
     * Gets employee between dates of birthday.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the employee between dates of birthday
     */
    List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo);
}
