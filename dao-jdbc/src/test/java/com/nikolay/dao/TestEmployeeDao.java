package com.nikolay.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
@Transactional
@Rollback
public class TestEmployeeDao {

  public static final Logger LOGGER = LogManager.getLogger();

  private static final long CORRECT_AMOUNT_EMPLOYEES = 13L;

  private static final long CORRECT_EMPLOYEE_ID = 1L;
  private static final long CORRECT_EMPLOYEE_DEPARTMENT_ID = 3L;
  private static final String CORRECT_EMPLOYEE_DEPARTMENT_NAME = "Engineering";
  private static final String CORRECT_EMPLOYEE_FULL_NAME = "Clem Hudspith";
  private static final LocalDate CORRECT_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal CORRECT_EMPLOYEE_SALARY = BigDecimal.valueOf(810).setScale(2);

  private static final long NEW_EMPLOYEE_ID = 1L;
  private static final long NEW_EMPLOYEE_DEPARTMENT_ID = 2L;
  private static final String NEW_EMPLOYEE_FULL_NAME = "Jeck Hudspith";
  private static final LocalDate NEW_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal NEW_EMPLOYEE_SALARY = BigDecimal.valueOf(200.12);

  private static final LocalDate DATE_FROM = LocalDate.of(1982, 4, 2);
  private static final LocalDate DATE_TO = LocalDate.of(1991, 7, 20);

  private static final Long INCORRECT_EMPLOYEE_ID = 200L;

  private static final Employee employee = new Employee(
      NEW_EMPLOYEE_ID,
      NEW_EMPLOYEE_DEPARTMENT_ID,
      NEW_EMPLOYEE_FULL_NAME,
      NEW_EMPLOYEE_BIRTHDAY,
      NEW_EMPLOYEE_SALARY
  );

  @Autowired
  private EmployeeDao employeeDao;

  @Before
  public void beforeTest() {
    LOGGER.error("execute: beforeTest()");
  }

  @After
  public void afterTest() {
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetEmployeeById() {
    LOGGER.debug("test DAO: run testGetEmployeeById()");
    ResponseEmployeeDto newEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);

    assertNotNull(newEmployee);
    assertEquals(CORRECT_EMPLOYEE_ID, newEmployee.getId().longValue());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_ID, newEmployee.getDepartmentId().longValue());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_NAME, newEmployee.getDepartmentName());
    assertEquals(CORRECT_EMPLOYEE_FULL_NAME, newEmployee.getFullName());
    assertEquals(CORRECT_EMPLOYEE_BIRTHDAY, newEmployee.getBirthday());
    assertEquals(CORRECT_EMPLOYEE_SALARY, newEmployee.getSalary());
  }

  @Test
  public void testGetEmployeeByBirthday() {
    LOGGER.debug("test DAO: run testGetEmployeeByBirthday()");
    List<ResponseEmployeeDto> employeeList = employeeDao
        .getEmployeesByDateOfBirthday(CORRECT_EMPLOYEE_BIRTHDAY);

    assertNotNull(employeeList);
    assertEquals(1, employeeList.size());
    ResponseEmployeeDto newEmployee = employeeList.get(0);
    assertEquals(CORRECT_EMPLOYEE_ID, newEmployee.getId().longValue());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_ID, newEmployee.getDepartmentId().longValue());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_NAME, newEmployee.getDepartmentName());
    assertEquals(CORRECT_EMPLOYEE_FULL_NAME, newEmployee.getFullName());
    assertEquals(CORRECT_EMPLOYEE_BIRTHDAY, newEmployee.getBirthday());
    assertEquals(CORRECT_EMPLOYEE_SALARY, newEmployee.getSalary());
  }

  @Test
  public void testGetEmployeeBetweenDatesOfBirthday() {
    LOGGER.debug("test DAO: run testGetEmployeeBetweenDatesOfBirthday()");
    List<ResponseEmployeeDto> employeeList = employeeDao
        .getEmployeesBetweenDatesOfBirthday(DATE_FROM, DATE_TO);

    assertNotNull(employeeList);
    assertEquals(3, employeeList.size());
  }

  @Test
  public void testGetAllEmployee() {
    LOGGER.debug("test DAO: run testGetAllEmployee()");
    List<ResponseEmployeeDto> employees = employeeDao.getAllEmployees();

    assertNotNull(employees);
    assertEquals(CORRECT_AMOUNT_EMPLOYEES, employees.size());
  }

  @Test
  public void testDeleteEmployee() {
    LOGGER.debug("test DAO: run testDeleteEmployee()");
    long sizeBefore = employeeDao.getAllEmployees().size();
    assertTrue(employeeDao.deleteEmployee(CORRECT_EMPLOYEE_ID));
    long sizeAfter = employeeDao.getAllEmployees().size();
    assertEquals(sizeBefore - 1, sizeAfter);
  }

  @Test
  public void testSaveEmployee() {
    LOGGER.debug("test DAO: run testSaveEmployee()");
    long sizeBefore = employeeDao.getAllEmployees().size();
    long employeeId = employeeDao.saveEmployee(employee);
    long sizeAfter = employeeDao.getAllEmployees().size();
    ResponseEmployeeDto newEmployee = employeeDao.getEmployeeById(employeeId);

    assertEquals(sizeBefore + 1, sizeAfter);
    assertNotNull(newEmployee);
    assertEquals(employeeId, newEmployee.getId().longValue());
    assertEquals(NEW_EMPLOYEE_DEPARTMENT_ID, employee.getDepartmentId().longValue());
    assertEquals(NEW_EMPLOYEE_FULL_NAME, employee.getFullName());
    assertEquals(NEW_EMPLOYEE_BIRTHDAY, employee.getBirthday());
    assertEquals(NEW_EMPLOYEE_SALARY, employee.getSalary());
  }

  @Test
  public void testUpdateEmployee() {
    LOGGER.debug("test DAO: run testUpdateEmployee()");
    ResponseEmployeeDto newEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);
    Employee saveEmployeeDto = new Employee();
    saveEmployeeDto.setId(newEmployee.getId());
    saveEmployeeDto.setDepartmentId(NEW_EMPLOYEE_DEPARTMENT_ID);
    saveEmployeeDto.setFullName(newEmployee.getFullName());
    saveEmployeeDto.setBirthday(newEmployee.getBirthday());
    saveEmployeeDto.setSalary(NEW_EMPLOYEE_SALARY);
    Boolean result = employeeDao.updateEmployee(saveEmployeeDto);
    ResponseEmployeeDto changedEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);

    assertNotNull(newEmployee);
    assertNotNull(changedEmployee);
    assertEquals(CORRECT_EMPLOYEE_ID, newEmployee.getId().longValue());
    assertTrue(result);
    assertEquals(CORRECT_EMPLOYEE_ID, changedEmployee.getId().longValue());
    assertEquals(NEW_EMPLOYEE_DEPARTMENT_ID, changedEmployee.getDepartmentId().byteValue());
    assertEquals(NEW_EMPLOYEE_SALARY, changedEmployee.getSalary().stripTrailingZeros());
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void negativeTestGetEmployeeById() {
    LOGGER.debug("test DAO: run negativeTestGetEmployeeById()");
    assertNull(employeeDao.getEmployeeById(null));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestGetEmployeeByBirthday() {
    LOGGER.debug("test DAO: run negativeTestGetEmployeeByBirthday()");
    assertNull(employeeDao.getEmployeesByDateOfBirthday(null));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestGetEmployeeBetweenDatesOfBirthday() {
    LOGGER.debug("test DAO: run negativeTestGetEmployeeBetweenDatesOfBirthday()");
    assertNull(employeeDao.getEmployeesBetweenDatesOfBirthday(null, null));
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void negativeTestGetEmployeeByIncorrectId() {
    LOGGER.debug("test DAO: run negativeTestGetEmployeeByIncorrectId()");
    assertNull(employeeDao.getEmployeeById(INCORRECT_EMPLOYEE_ID));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestSaveNull() {
    LOGGER.debug("test DAO: run negativeTestSaveNull()");
    assertNull(employeeDao.saveEmployee(null));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestUpdateNull() {
    LOGGER.debug("test DAO: run negativeTestUpdateNull()");
    assertFalse(employeeDao.updateEmployee(null));
  }

}
