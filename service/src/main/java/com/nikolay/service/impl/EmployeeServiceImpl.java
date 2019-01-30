package com.nikolay.service.impl;

import com.nikolay.dao.EmployeeDao;
import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
import com.nikolay.service.exception.OperationFailedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Employee service.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  @Value("${employeeService.incorrectId}")
  private String incorrectId;

  @Value("${employeeService.incorrectEmployee}")
  private String incorrectEmployee;

  @Value("${employeeService.incorrectDepartmentId}")
  private String incorrectDepartmentId;

  @Value("${employeeService.incorrectId}")
  private String incorrectFullName;

  @Value("${employeeService.incorrectId}")
  private String incorrectBirthday;

  @Value("${employeeService.incorrectId}")
  private String incorrectSalary;

  private EmployeeDao employeeDao;

  /**
   * Sets employee dao.
   *
   * @param employeeDao the employee dao
   */
  public void setEmployeeDao(EmployeeDao employeeDao) {
    LOGGER.debug("setEmployeeDao");
    this.employeeDao = employeeDao;
  }

  @Override
  public Employee getEmployeeById(Long employeeId) {
    LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
    if (employeeId == null || employeeId < 0) {
      throw new OperationFailedException(incorrectId);
    }
    return employeeDao.getEmployeeById(employeeId);
  }

  @Override
  public Long saveEmployee(Employee employee) {
    LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
    checkEmployee(employee);
    if (employee.getId() != null) {
      throw new OperationFailedException(incorrectId);
    }
    return employeeDao.saveEmployee(employee);
  }

  @Override
  public void updateEmployee(Employee employee) {
    LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
    checkEmployee(employee);
    if (employee.getId() == null) {
      throw new OperationFailedException(incorrectId);
    }
    employeeDao.updateEmployee(employee);
  }

  @Override
  public void deleteEmployee(Long employeeId) {
    LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
    if (employeeId == null || employeeId < 0) {
      throw new OperationFailedException(incorrectId);
    }
    employeeDao.deleteEmployee(employeeId);

  }

  @Override
  public List<Employee> getAllEmployees() {
    LOGGER.debug("getAllEmployees()");
    return employeeDao.getAllEmployees();
  }

  @Override
  public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
    LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeeByDateOfBirthday(date);
  }

  @Override
  public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
    LOGGER.debug(
        "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
        dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
  }

  private void checkEmployee(Employee employee) throws OperationFailedException {
    if (employee == null) {
      throw new OperationFailedException(incorrectEmployee);
    }
    if (employee.getDepartmentId() == null) {
      throw new OperationFailedException(incorrectDepartmentId);
    }
    if (employee.getFullName() == null) {
      throw new OperationFailedException(incorrectFullName);
    }
    if (employee.getBirthday() == null) {
      throw new OperationFailedException(incorrectBirthday);
    }
    if (employee.getSalary() == null) {
      throw new OperationFailedException(incorrectSalary);
    }
  }
}
