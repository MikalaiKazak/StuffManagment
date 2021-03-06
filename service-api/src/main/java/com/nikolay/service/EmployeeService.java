package com.nikolay.service;

import java.time.LocalDate;
import java.util.List;

import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;
import com.nikolay.service.exception.OperationFailedException;

/**
 * The interface Employee service.
 */
public interface EmployeeService {

  /**
   * Gets employee by id.
   *
   * @param employeeId the employee id
   * @return the employee by id
   * @throws OperationFailedException the operation failed exception
   */
  ResponseEmployeeDto getEmployeeById(final Long employeeId) throws OperationFailedException;

  /**
   * Save employee long.
   *
   * @param employee the employee
   * @return the long
   * @throws OperationFailedException the operation failed exception
   */
  Long saveEmployee(final Employee employee) throws OperationFailedException;

  /**
   * Update employee boolean.
   *
   * @param employee the employee
   * @return the boolean
   * @throws OperationFailedException the operation failed exception
   */
  Boolean updateEmployee(final Employee employee)
      throws OperationFailedException;

  /**
   * Delete employee boolean.
   *
   * @param employeeId the employee id
   * @return the boolean
   * @throws OperationFailedException the operation failed exception
   */
  Boolean deleteEmployee(final Long employeeId) throws OperationFailedException;

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  List<ResponseEmployeeDto> getAllEmployees();

  /**
   * Gets employees by date of birthday.
   *
   * @param date the date
   * @return the employees by date of birthday
   */
  List<ResponseEmployeeDto> getEmployeesByDateOfBirthday(final LocalDate date);

  /**
   * Gets employees between dates of birthday.
   *
   * @param dateFrom the date from
   * @param dateTo the date to
   * @return the employees between dates of birthday
   */
  List<ResponseEmployeeDto> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo);
}
