package com.nikolay.dao;

import com.nikolay.model.Employee;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface defines the various operations to be performed on a Department object.
 *
 * @author Mikalai Kazak
 * @version 1.0
 * @see Employee
 */
public interface EmployeeDao {

  /**
   * Gets the employee object using employee identifier.
   *
   * @param employeeId the employee id
   * @return the employee by id
   */
  Employee getEmployeeById(final Long employeeId);

  /**
   * Save employee.
   *
   * @param employee the employee
   * @return the long
   */
  Long saveEmployee(final Employee employee);

  /**
   * Update employee.
   *
   * @param employee the employee
   * @return the boolean
   */
  Boolean updateEmployee(final Employee employee);

  /**
   * Delete employee.
   *
   * @param employeeId the employee id
   * @return the boolean
   */
  Boolean deleteEmployee(final Long employeeId);

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  List<Employee> getAllEmployees();

  /**
   * Gets employees by date of birthday.
   *
   * @param date the date
   * @return the employee by date of birthday
   */
  List<Employee> getEmployeesByDateOfBirthday(final LocalDate date);

  /**
   * Gets employees between dates of birthday.
   *
   * @param dateFrom the date from
   * @param dateTo the date to
   * @return the employee between dates of birthday
   */
  List<Employee> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo);
}
