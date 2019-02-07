package com.nikolay.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.nikolay.model.Employee;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type Test employee dao.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
public class TestEmployeeDao {

  public static final Logger LOGGER = LogManager.getLogger();

  private static final long CORRECT_AMOUNT_EMPLOYEES = 13L;

  private static final long CORRECT_EMPLOYEE_ID = 1L;
  private static final long CORRECT_EMPLOYEE_DEPARTMENT_ID = 3L;
  private static final String CORRECT_EMPLOYEE_DEPARTMENT_NAME = "Engineering";
  private static final String CORRECT_EMPLOYEE_FULL_NAME = "Clem Hudspith";
  private static final LocalDate CORRECT_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal CORRECT_EMPLOYEE_SALARY = BigDecimal.valueOf(810);

  private static final long NEW_EMPLOYEE_DEPARTMENT_ID = 2L;
  private static final String NEW_EMPLOYEE_DEPARTMENT_NAME = "DWBI";
  private static final String NEW_EMPLOYEE_FULL_NAME = "Jeck Hudspith";
  private static final LocalDate NEW_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal NEW_EMPLOYEE_SALARY = BigDecimal.valueOf(200.12);

  private static final LocalDate DATE_FROM = LocalDate.of(1982, 4, 2);
  private static final LocalDate DATE_TO = LocalDate.of(1991, 7, 20);

  private static final Long INCORRECT_EMPLOYEE_ID = 200L;

  private static final Long INCORRECT_EMPLOYEE_DEPARTMENT_ID = 200L;

  private static final Employee employee = new Employee(
      NEW_EMPLOYEE_DEPARTMENT_ID,
      NEW_EMPLOYEE_DEPARTMENT_NAME,
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
    Employee newEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);
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
    List<Employee> employeeList = employeeDao
        .getEmployeesByDateOfBirthday(CORRECT_EMPLOYEE_BIRTHDAY);
    assertNotNull(employeeList);
    assertEquals(1, employeeList.size());
    Employee newEmployee = employeeList.get(0);
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
    List<Employee> employeeList = employeeDao
        .getEmployeesBetweenDatesOfBirthday(DATE_FROM, DATE_TO);
    assertNotNull(employeeList);
    assertEquals(3, employeeList.size());
  }

  @Test
  public void testGetAllEmployee() {
    LOGGER.debug("test DAO: run testGetAllEmployee()");
    List<Employee> employees = employeeDao.getAllEmployees();
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
    assertEquals(sizeBefore + 1, sizeAfter);
    Employee newEmployee = employeeDao.getEmployeeById(employeeId);
    assertNotNull(newEmployee);
    assertEquals(employeeId, newEmployee.getId().longValue());
    assertEquals(NEW_EMPLOYEE_DEPARTMENT_ID, employee.getDepartmentId().longValue());
    assertEquals(NEW_EMPLOYEE_FULL_NAME, employee.getFullName());
    assertEquals(NEW_EMPLOYEE_BIRTHDAY, employee.getBirthday());
    assertEquals(NEW_EMPLOYEE_SALARY, employee.getSalary());
  }

  @Test
  public void testSaveEmployeeWithFloatSalary() {
    LOGGER.debug("test DAO: run testSaveEmployeeWithFloatSalary()");
    long sizeBefore = employeeDao.getAllEmployees().size();
    Employee employee = new Employee(2L, "Sales", "Nikolay Kozak",
        LocalDate.of(1999, 12, 28), BigDecimal.valueOf(245.51));
    long employeeId = employeeDao.saveEmployee(employee);
    long sizeAfter = employeeDao.getAllEmployees().size();
    assertEquals(sizeBefore + 1, sizeAfter);
    Employee newEmployee = employeeDao.getEmployeeById(employeeId);
    assertNotNull(newEmployee);
    assertEquals(employee.getSalary(), newEmployee.getSalary().stripTrailingZeros());
    assertEquals(2L, newEmployee.getDepartmentId().longValue());
  }

  @Test
  public void testUpdateEmployee() {
    LOGGER.debug("test DAO: run testUpdateEmployee()");
    Employee newEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);
    assertNotNull(newEmployee);
    assertEquals(CORRECT_EMPLOYEE_ID, newEmployee.getId().longValue());
    newEmployee.setDepartmentId(NEW_EMPLOYEE_DEPARTMENT_ID);
    newEmployee.setSalary(NEW_EMPLOYEE_SALARY);
    assertTrue(employeeDao.updateEmployee(newEmployee));
    Employee changedEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);
    assertNotNull(newEmployee);
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

  @Test(expected = DataIntegrityViolationException.class)
  public void negativeTestSaveIncorrectEmployee() {
    LOGGER.debug("test DAO: run negativeTestSaveIncorrectEmployee()");
    Employee newEmployee = employeeDao.getEmployeeById(CORRECT_EMPLOYEE_ID);
    newEmployee.setDepartmentId(INCORRECT_EMPLOYEE_DEPARTMENT_ID);
    assertNull(employeeDao.saveEmployee(newEmployee));
  }
}
