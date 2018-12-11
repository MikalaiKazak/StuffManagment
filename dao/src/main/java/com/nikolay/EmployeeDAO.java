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
     * @param id the employee id that you want get
     * @return the employee
     */
    Employee getEmployeeById(Long id);

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
     * @return the identifier of the employee updated
     */
    Long updateEmployee(Employee employee);

    /**
     * Delete employee by identifier
     *
     * @param employeeId the employee identifier
     * @return the identifier of the deleted employee
     */
    Long deleteEmployee(Long employeeId);

    /**
     * Gets employee by department name.
     *
     * @param departmentName the department name
     * @return the employee by department name
     */
    List<Employee> getEmployeeByDepartmentName(String departmentName);

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
