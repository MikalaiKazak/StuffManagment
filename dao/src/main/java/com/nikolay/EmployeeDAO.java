package com.nikolay;

import java.util.List;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 *
 * Defines DAO operations for the employee model.
 */
public interface EmployeeDAO {

    /**
     * Get the list of all employees
     *
     * @return the list of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Get one employee by identifier
     *
     * @param  id the employee id that you want get
     * @return the employee
     */
    Employee getEmployeeById(Long id);

    /**
     * Save employee
     *
     * @param  employee the employee that you want to save
     * @return the identifier of the employee created
     */
    Long saveEmployee(Employee employee);

    /**
     *  Update employee
     *
     * @param employee the employee that you want to change
     * @return the identifier of the employee updated
     */
    Long updateEmployee(Employee employee);

    /**
     * Delete employee by identifier
     *
     * @param employeeId the employee identifier
     */
    void deleteEmployee(Long employeeId);
}
