package com.nikolay.service.impl;

import java.math.BigDecimal;
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
import com.nikolay.model.dto.ResponseEmployeeDto;
import com.nikolay.service.EmployeeService;
import com.nikolay.service.exception.OperationFailedException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

  public static final Logger LOGGER = LogManager.getLogger();

  @Value("${employeeService.notUpdated}")
  private String employeeNotUpdated;

  @Value("${employeeService.notDeleted}")
  private String employeeNotDeleted;

  @Value("${employee.incorrectId}")
  private String incorrectEmployeeId;

  @Value("${employee.incorrectEmployee}")
  private String incorrectEmployee;

  @Value("${department.incorrectDepartmentId}")
  private String incorrectDepartmentId;

  @Value("${employee.incorrectFullName}")
  private String incorrectFullName;

  @Value("${employee.incorrectBirthday}")
  private String incorrectBirthday;

  @Value("${employee.incorrectSalary}")
  private String incorrectSalary;

  private final EmployeeDao employeeDao;

  public EmployeeServiceImpl(EmployeeDao employeeDao) {
    LOGGER.debug("setEmployeeDao");
    this.employeeDao = employeeDao;
  }

  @Override
  public ResponseEmployeeDto getEmployeeById(final Long employeeId) {
    LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
    if (employeeId == null || employeeId < 0) {
      throw new OperationFailedException(incorrectEmployeeId);
    }
    return employeeDao.getEmployeeById(employeeId);
  }

  @Override
  public Long saveEmployee(final Employee employee) {
    LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
    checkEmployee(employee.getDepartmentId(), employee.getFullName(), employee.getBirthday(),
        employee.getSalary());
    return employeeDao.saveEmployee(employee);
  }

  @Override
  public Boolean updateEmployee(final Employee employee) {
    LOGGER.debug("updateEmployee(employee)");
    checkEmployee(employee.getDepartmentId(), employee.getFullName(), employee.getBirthday(),
        employee.getSalary());
    if (employee.getId() == null || employee.getId() < 0) {
      throw new OperationFailedException(incorrectEmployeeId);
    }
    Boolean resultUpdateEmployee = employeeDao.updateEmployee(employee);
    if (!resultUpdateEmployee) {
      throw new OperationFailedException(employeeNotUpdated);
    } else {
      return resultUpdateEmployee;
    }
  }

  private void checkEmployee(Long departmentId, String fullName, LocalDate birthday,
      BigDecimal salary) {
    if (departmentId == null) {
      throw new OperationFailedException(incorrectDepartmentId);
    } else if (fullName == null) {
      throw new OperationFailedException(incorrectFullName);
    } else if (birthday == null) {
      throw new OperationFailedException(incorrectBirthday);
    } else if (salary == null) {
      throw new OperationFailedException(incorrectSalary);
    }
  }

  @Override
  public Boolean deleteEmployee(final Long employeeId) {
    LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
    if (employeeId == null || employeeId < 0) {
      throw new OperationFailedException(incorrectEmployeeId);
    }
    Boolean resultDeleteEmployee = employeeDao.deleteEmployee(employeeId);
    if (!resultDeleteEmployee) {
      throw new OperationFailedException(employeeNotDeleted);
    } else {
      return resultDeleteEmployee;
    }
  }

  @Override
  public List<ResponseEmployeeDto> getAllEmployees() {
    LOGGER.debug("getAllEmployees()");
    return employeeDao.getAllEmployees();
  }

  @Override
  public List<ResponseEmployeeDto> getEmployeesByDateOfBirthday(final LocalDate date) {
    LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeesByDateOfBirthday(date);
  }

  @Override
  public List<ResponseEmployeeDto> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo) {
    LOGGER.debug(
        "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
        dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return employeeDao.getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
  }

}
