package com.nikolay.service;

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
     */
    Employee getEmployeeById(Long employeeId);

    /**
     * Save employee long.
     *
     * @param employee the employee
     * @return the long
     */
    Long saveEmployee(Employee employee);

    /**
     * Update employee.
     *
     * @param employee the employee
     */
    void updateEmployee(Employee employee);

    /**
     * Delete employee.
     *
     * @param employeeId the employee id
     */
    void deleteEmployee(Long employeeId);

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
     * @param dateTo the date to
     * @return the employee between dates of birthday
     */
    List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo);
}
