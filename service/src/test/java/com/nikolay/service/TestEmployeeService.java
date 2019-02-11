package com.nikolay.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.nikolay.dao.EmployeeDao;
import com.nikolay.model.Employee;
import com.nikolay.service.exception.OperationFailedException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type Test employee service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-service-mock.xml"})
public class TestEmployeeService {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();
  private static final long CORRECT_AMOUNT_EMPLOYEES = 2L;
  private static final long CORRECT_EMPLOYEE_ID = 1L;
  private static final long CORRECT_EMPLOYEE_DEPARTMENT_ID = 3L;
  private static final String CORRECT_EMPLOYEE_DEPARTMENT_NAME = "Engineering";
  private static final String CORRECT_EMPLOYEE_FULL_NAME = "Clem Hudspith";
  private static final LocalDate CORRECT_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal CORRECT_EMPLOYEE_SALARY = BigDecimal.valueOf(810);
  private static final long NEW_EMPLOYEE_ID = 3L;
  private static final long NEW_EMPLOYEE_DEPARTMENT_ID = 2L;
  private static final String NEW_EMPLOYEE_DEPARTMENT_NAME = "DWBI";
  private static final String NEW_EMPLOYEE_FULL_NAME = "Jeck Hudspith";
  private static final LocalDate NEW_EMPLOYEE_BIRTHDAY = LocalDate.of(1982, 4, 2);
  private static final BigDecimal NEW_EMPLOYEE_SALARY = BigDecimal.valueOf(200.12);
  private static final LocalDate DATE = LocalDate.of(1982, 4, 2);
  private static final LocalDate DATE_FROM = LocalDate.of(1982, 4, 2);
  private static final LocalDate DATE_TO = LocalDate.of(1991, 7, 20);
  private static final Long INCORRECT_EMPLOYEE_ID = -20L;

  /**
   * The Employee dao mock.
   */
  @Autowired
  EmployeeDao employeeDaoMock;

  /**
   * The Employee service.
   */
  @Autowired
  EmployeeService employeeService;

  private Employee correctEmployee;
  private Employee saveEmployee;
  private List<Employee> employees;

  /**
   * Sets up.
   */
  @Before
  public void setUp() {
    saveEmployee = new Employee(
        NEW_EMPLOYEE_DEPARTMENT_ID,
        NEW_EMPLOYEE_FULL_NAME,
        NEW_EMPLOYEE_DEPARTMENT_NAME,
        NEW_EMPLOYEE_BIRTHDAY,
        NEW_EMPLOYEE_SALARY
    );

    correctEmployee = new Employee(
        CORRECT_EMPLOYEE_ID,
        CORRECT_EMPLOYEE_DEPARTMENT_ID,
        CORRECT_EMPLOYEE_DEPARTMENT_NAME,
        CORRECT_EMPLOYEE_FULL_NAME,
        CORRECT_EMPLOYEE_BIRTHDAY,
        CORRECT_EMPLOYEE_SALARY
    );

    employees = Arrays.asList(saveEmployee, correctEmployee);

    LOGGER.error("execute: setUp()");
  }

  /**
   * After test.
   */
  @After
  public void afterTest() {
    verifyNoMoreInteractions(employeeDaoMock);
    reset(employeeDaoMock);
    LOGGER.error("execute: afterTest()");
  }

  /**
   * Test get employee by id.
   */
  @Test
  public void testGetEmployeeById() {
    LOGGER.debug("test Service: run testGetEmployeeById()");
    when(employeeDaoMock.getEmployeeById(CORRECT_EMPLOYEE_ID)).thenReturn(correctEmployee);
    Employee newEmployee = employeeService.getEmployeeById(CORRECT_EMPLOYEE_ID);
    verify(employeeDaoMock).getEmployeeById(CORRECT_EMPLOYEE_ID);
    assertNotNull(newEmployee);
    assertEquals(CORRECT_EMPLOYEE_ID, newEmployee.getId().longValue());
    assertEquals(CORRECT_EMPLOYEE_FULL_NAME, newEmployee.getFullName());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_ID, newEmployee.getDepartmentId().longValue());
    assertEquals(CORRECT_EMPLOYEE_DEPARTMENT_NAME, newEmployee.getDepartmentName());
    assertEquals(CORRECT_EMPLOYEE_SALARY, newEmployee.getSalary());
    assertEquals(CORRECT_EMPLOYEE_BIRTHDAY, newEmployee.getBirthday());
  }

  /**
   * Test save employee.
   */
  @Test
  public void testSaveEmployee() {
    LOGGER.debug("test Service: run testSaveEmployee()");
    when(employeeDaoMock.saveEmployee(saveEmployee)).thenReturn(NEW_EMPLOYEE_ID);
    Long employeeId = employeeService.saveEmployee(saveEmployee);
    verify(employeeDaoMock).saveEmployee(saveEmployee);
    assertNotNull(employeeId);
    assertEquals(NEW_EMPLOYEE_ID, employeeId.longValue());
  }

  /**
   * Test update employee.
   */
  @Test
  public void testUpdateEmployee() {
    LOGGER.debug("test Service: run testUpdateEmployee()");
    when(employeeDaoMock.updateEmployee(correctEmployee)).thenReturn(true);
    employeeService.updateEmployee(correctEmployee);
    verify(employeeDaoMock).updateEmployee(correctEmployee);
  }

  /**
   * Test delete employee.
   */
  @Test
  public void testDeleteEmployee() {
    LOGGER.debug("test Service: run testDeleteEmployee()");
    when(employeeDaoMock.deleteEmployee(CORRECT_EMPLOYEE_ID)).thenReturn(true);
    employeeService.deleteEmployee(CORRECT_EMPLOYEE_ID);
    verify(employeeDaoMock).deleteEmployee(CORRECT_EMPLOYEE_ID);
  }

  /**
   * Test get all employee.
   */
  @Test
  public void testGetAllEmployee() {
    LOGGER.debug("test Service: run testGetAllEmployee()");
    when(employeeDaoMock.getAllEmployees()).thenReturn(employees);
    List<Employee> employees = employeeService.getAllEmployees();
    verify(employeeDaoMock).getAllEmployees();
    assertNotNull(employees);
    assertEquals(CORRECT_AMOUNT_EMPLOYEES, employees.size());
  }

  /**
   * Test get employee by date of birthday.
   */
  @Test
  public void testGetEmployeeByDateOfBirthday() {
    LOGGER.debug("test Service: run testGetEmployeeByDateOfBirthday()");
    when(employeeDaoMock.getEmployeesByDateOfBirthday(DATE))
        .thenReturn(Collections.singletonList(correctEmployee));
    List<Employee> employees = employeeService.getEmployeesByDateOfBirthday(DATE);
    assertNotNull(employees);
    verify(employeeDaoMock).getEmployeesByDateOfBirthday(DATE);
  }

  /**
   * Test get employee between dates of birthday.
   */
  @Test
  public void testGetEmployeeBetweenDatesOfBirthday() {
    LOGGER.debug("test Service: run testGetEmployeeBetweenDatesOfBirthday()");
    when(employeeDaoMock.getEmployeesBetweenDatesOfBirthday(DATE_FROM, DATE_TO))
        .thenReturn(employees);
    List<Employee> employees = employeeService
        .getEmployeesBetweenDatesOfBirthday(DATE_FROM, DATE_TO);
    verify(employeeDaoMock).getEmployeesBetweenDatesOfBirthday(DATE_FROM, DATE_TO);
    assertNotNull(employees);
    assertEquals(CORRECT_AMOUNT_EMPLOYEES, employees.size());
  }

  /**
   * Test get employee by id exception.
   */
  @Test(expected = OperationFailedException.class)
  public void testGetEmployeeByIdException() {
    LOGGER.debug("test Service: run testGetEmployeeByIdException()");
    when(employeeDaoMock.getEmployeeById(INCORRECT_EMPLOYEE_ID))
        .thenThrow(OperationFailedException.class);
    Employee employee = employeeService.getEmployeeById(INCORRECT_EMPLOYEE_ID);
    assertNull(employee);
    verifyZeroInteractions(employeeDaoMock.getEmployeeById(CORRECT_EMPLOYEE_ID));
  }
}

