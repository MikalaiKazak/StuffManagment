package com.nikolay.dao;

import java.time.LocalDate;
import java.util.List;

import com.nikolay.model.Employee;

/**
 * The interface Employee dao.
 */
public interface EmployeeDao {

  /**
   * Gets employee by id.
   *
   * @param employeeId the employee id
   * @return the employee by id
   */
  Employee getEmployeeById(final Long employeeId);

  /**
   * Save employee long.
   *
   * @param employee the employee
   * @return the long
   */
  Long saveEmployee(final Employee employee);

  /**
   * Update employee boolean.
   *
   * @param employee the employee
   * @return the boolean
   */
  Boolean updateEmployee(final Employee employee);

  /**
   * Delete employee boolean.
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
   * @return the employees by date of birthday
   */
  List<Employee> getEmployeesByDateOfBirthday(final LocalDate date);

  /**
   * Gets employees between dates of birthday.
   *
   * @param dateFrom the date from
   * @param dateTo the date to
   * @return the employees between dates of birthday
   */
  List<Employee> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo);
}
