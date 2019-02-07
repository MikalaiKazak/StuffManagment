package com.nikolay.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.nikolay.dao.DepartmentDao;
import com.nikolay.model.Department;
import com.nikolay.service.exception.OperationFailedException;
import java.math.BigDecimal;
import java.util.Arrays;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-service-mock.xml"})
public class TestDepartmentService {

  public static final Logger LOGGER = LogManager.getLogger();
  private static final long CORRECT_AMOUNT_DEPARTMENTS = 2L;
  private static final long CORRECT_DEPARTMENT_ID = 1L;
  private static final String CORRECT_DEPARTMENT_NAME = "Accounting";
  private static final BigDecimal CORRECT_DEPARTMENT_AVERAGE_SALARY = new BigDecimal(2399.5);
  private static final long NEW_DEPARTMENT_ID = 2L;
  private static final String NEW_DEPARTMENT_NAME = "DWBI";
  private static final BigDecimal NEW_DEPARTMENT_AVERAGE_SALARY = new BigDecimal(300.25);

  @Autowired
  private DepartmentDao departmentDaoMock;

  @Autowired
  private DepartmentService departmentService;

  private Department saveDepartment;
  private Department correctDepartment;
  private List<Department> departments;

  @Before
  public void setUp() {
    LOGGER.error("execute: beforeTest()");

    saveDepartment = new Department(
        NEW_DEPARTMENT_NAME,
        NEW_DEPARTMENT_AVERAGE_SALARY);

    correctDepartment = new Department(
        CORRECT_DEPARTMENT_ID,
        CORRECT_DEPARTMENT_NAME,
        CORRECT_DEPARTMENT_AVERAGE_SALARY);

    departments = Arrays.asList(saveDepartment, correctDepartment);
  }

  /**
   * After test.
   */
  @After
  public void afterTest() {
    verifyNoMoreInteractions(departmentDaoMock);
    reset(departmentDaoMock);
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetDepartmentById() {
    LOGGER.debug("test Service: run testGetDepartmentById()");
    when(departmentDaoMock.getDepartmentById(CORRECT_DEPARTMENT_ID)).thenReturn(correctDepartment);
    Department newDepartment = departmentService.getDepartmentById(CORRECT_DEPARTMENT_ID);
    verify(departmentDaoMock).getDepartmentById(CORRECT_DEPARTMENT_ID);
    assertNotNull(newDepartment);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY, newDepartment.getAverageSalary());
  }

  @Test
  public void testGetDepartmentByName() {
    LOGGER.debug("test Service: run testGetDepartmentByName()");
    when(departmentDaoMock.getDepartmentByName(CORRECT_DEPARTMENT_NAME))
        .thenReturn(correctDepartment);
    Department newDepartment = departmentService.getDepartmentByName(CORRECT_DEPARTMENT_NAME);
    verify(departmentDaoMock).getDepartmentByName(CORRECT_DEPARTMENT_NAME);
    assertNotNull(newDepartment);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY, newDepartment.getAverageSalary());
  }

  @Test
  public void testGetAllDepartment() {
    LOGGER.debug("test Service: run testGetAllDepartment()");
    when(departmentDaoMock.getAllDepartments()).thenReturn(departments);
    List<Department> departmentList = departmentService.getAllDepartments();
    verify(departmentDaoMock).getAllDepartments();
    assertNotNull(departmentList);
    assertEquals(CORRECT_AMOUNT_DEPARTMENTS, departmentList.size());
  }

  @Test
  public void testSaveDepartment() {
    LOGGER.debug("test Service: run testSaveDepartment()");
    when(departmentDaoMock.saveDepartment(saveDepartment)).thenReturn(NEW_DEPARTMENT_ID);
    Long departmentId = departmentService.saveDepartment(saveDepartment);
    verify(departmentDaoMock).saveDepartment(saveDepartment);
    assertNotNull(departmentId);
    assertEquals(NEW_DEPARTMENT_ID, departmentId.longValue());
  }

  @Test
  public void testUpdateDepartment() {
    LOGGER.debug("test Service: run testUpdateDepartment()");
    when(departmentDaoMock.updateDepartment(correctDepartment)).thenReturn(true);
    departmentService.updateDepartment(correctDepartment);
    verify(departmentDaoMock).updateDepartment(correctDepartment);
  }

  @Test
  public void testDeleteDepartment() {
    LOGGER.debug("test Service: run testDeleteDepartment()");
    when(departmentDaoMock.deleteDepartment(anyLong())).thenReturn(true);
    departmentService.deleteDepartment(anyLong());
    departmentService.deleteDepartment(anyLong());
    verify(departmentDaoMock, times(2)).deleteDepartment(anyLong());
  }

  @Test(expected = OperationFailedException.class)
  public void testSaveDepartmentException() {
    LOGGER.debug("test Service: run testSaveDepartmentException()");
    when(departmentDaoMock.saveDepartment(correctDepartment)).thenReturn(0L);
    Long departmentId = departmentService.saveDepartment(correctDepartment);
    assertNull(departmentId);
    verifyZeroInteractions(departmentDaoMock.saveDepartment(correctDepartment));
  }

  @Test(expected = OperationFailedException.class)
  public void testUpdateDepartmentException() {
    LOGGER.debug("test Service: run testUpdateDepartmentException()");
    when(departmentDaoMock.updateDepartment(any(Department.class))).thenReturn(true);
    departmentService.updateDepartment(new Department());
    verifyNoMoreInteractions(departmentDaoMock);
  }

  @Test(expected = OperationFailedException.class)
  public void testGetDepartmentByIdException() {
    LOGGER.debug("test Service: run testGetDepartmentByIdException()");
    when(departmentDaoMock.getDepartmentById(-1L)).thenThrow(OperationFailedException.class);
    Department department = departmentService.getDepartmentById(-1L);
    assertNull(department);
    verifyZeroInteractions(departmentDaoMock.getDepartmentById(-1L));
  }
}
