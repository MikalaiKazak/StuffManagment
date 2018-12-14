package com.nikolay;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Employee dao.
 *
 * @author Mikalai_Kazak @epam.com 10.12.2018 Defines DAO operations for the employee model.
 */
public interface EmployeeDAO {

    /**
     * Get one employee by identifier
     *
     * @param employeeId the employee id that you want get
     * @return the employee
     */
    Employee getEmployeeById(Long employeeId);

    /**
     * Save employee
     *
     * @param employee the employee that you want to save
     * @return the identifier of the employee created
     */
    Long saveEmployee(Employee employee);

    /**
     * Update employee
     *
     * @param employee the employee that you want to change
     */
    void updateEmployee(Employee employee);

    /**
     * Delete employee by identifier
     *
     * @param employeeId the employee identifier
     */
    void deleteEmployee(Long employeeId);

    /**
     * Get the list of all employees
     *
     * @return the list of all employees
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
     * Gets employee between dates of birtday.
     *
     * @param dateFrom the date from
     * @param dateTo   the date to
     * @return the employee between dates of birtday
     */
    List<Employee> getEmployeeBetweenDatesOfBirtday(LocalDate dateFrom, LocalDate dateTo);
}
