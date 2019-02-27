package com.nikolay.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nikolay.dao.EmployeeDao;
import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
import com.nikolay.service.exception.OperationFailedException;

/**
 * The type Employee service.
 */
@Service
@Transactional
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

  @Value("${employeeService.incorrectFullName}")
  private String incorrectFullName;

  @Value("${employeeService.incorrectBirthday}")
  private String incorrectBirthday;

  @Value("${employeeService.incorrectSalary}")
  private String incorrectSalary;

  @Value("${employeeService.notUpdated}")
  private String employeeNotUpdated;

  @Value("${employeeService.notDeleted}")
  private String employeeNotDeleted;

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
  public Boolean updateEmployee(Employee employee) {
    LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
    checkEmployee(employee);
    Boolean resultUpdateEmployee = employeeDao.updateEmployee(employee);
    if (!resultUpdateEmployee) {
      throw new OperationFailedException(employeeNotUpdated);
    } else {
      return resultUpdateEmployee;
    }
  }

  @Override
  public Boolean deleteEmployee(Long employeeId) {
    LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
    Boolean resultDeleteEmployee = employeeDao.deleteEmployee(employeeId);
    if (!resultDeleteEmployee) {
      throw new OperationFailedException(employeeNotDeleted);
    } else {
      return resultDeleteEmployee;
    }
  }

  @Override
  public List<Employee> getAllEmployees() {
    LOGGER.debug("getAllEmployees()");
    return employeeDao.getAllEmployees();
  }

  @Override
  public List<Employee> getEmployeesByDateOfBirthday(LocalDate date) {
    LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeesByDateOfBirthday(date);
  }

  @Override
  public List<Employee> getEmployeesBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
    LOGGER.debug(
        "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
        dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
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
