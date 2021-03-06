package com.nikolay.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
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

import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
@Transactional
@Rollback
public class TestDepartmentDao {

  public static final Logger LOGGER = LogManager.getLogger();

  private static final long CORRECT_AMOUNT_DEPARTMENTS = 14L;

  private static final long CORRECT_DEPARTMENT_ID = 1L;
  private static final String CORRECT_DEPARTMENT_NAME = "Accounting";
  private static final BigDecimal CORRECT_DEPARTMENT_AVERAGE_SALARY = new BigDecimal(2399.5)
      .setScale(2);

  private static final String NEW_DEPARTMENT_NAME = "DWBI";
  private static final long ID_DEPARTMENT_ID = 1L;

  private static final String CHANGED_DEPARTMENT_NAME = "Department";

  private static final Long INCORRECT_DEPARTMENT_ID = 200L;

  private static final Department department = new Department(
      ID_DEPARTMENT_ID,
      NEW_DEPARTMENT_NAME
  );

  @Autowired
  private DepartmentDao departmentDao;

  @Before
  public void beforeTest() {
    LOGGER.error("execute: beforeTest()");
  }

  @After
  public void afterTest() {
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetDepartment() {
    LOGGER.debug("test Dao: run testGetDepartment()");
    ResponseDepartmentDto newDepartment = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);

    assertNotNull(newDepartment);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY, newDepartment.getAverageSalary());
  }

  @Test
  public void testSaveDepartment() {
    LOGGER.debug("test DAO: run testSaveDepartment()");
    long sizeBefore = departmentDao.getAllDepartments().size();
    Long departmentId = departmentDao.saveDepartment(department);
    long sizeAfter = departmentDao.getAllDepartments().size();

    assertEquals(sizeBefore + 1, sizeAfter);
    ResponseDepartmentDto newDepartment = departmentDao.getDepartmentById(departmentId);
    assertNotNull(newDepartment);
    assertEquals(departmentId.longValue(), newDepartment.getId().longValue());
    assertEquals(department.getDepartmentName(), newDepartment.getDepartmentName());
  }

  @Test
  public void testDeleteDepartment() {
    LOGGER.debug("test DAO: run testDeleteDepartment()");
    long sizeBefore = departmentDao.getAllDepartments().size();
    assertEquals(CORRECT_AMOUNT_DEPARTMENTS, sizeBefore);
    assertTrue(departmentDao.deleteDepartment(CORRECT_DEPARTMENT_ID));
    long sizeAfter = departmentDao.getAllDepartments().size();
    assertEquals(sizeBefore - 1, sizeAfter);
  }

  @Test
  public void testGetAllDepartment() {
    LOGGER.debug("test DAO: run testGetAllDepartment()");
    List<ResponseDepartmentDto> departmentList = departmentDao.getAllDepartments();

    assertNotNull(departmentList);
    assertEquals(CORRECT_AMOUNT_DEPARTMENTS, departmentList.size());
  }

  @Test
  public void testUpdateDepartment() {
    LOGGER.debug("test DAO: run testUpdateDepartment()");
    ResponseDepartmentDto department = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);

    assertNotNull(department);
    assertEquals(CORRECT_DEPARTMENT_ID, department.getId().longValue());

    Department newDepartment = new Department(CORRECT_DEPARTMENT_ID, CHANGED_DEPARTMENT_NAME);

    assertTrue(departmentDao.updateDepartment(newDepartment));

    ResponseDepartmentDto changedDepartment = departmentDao
        .getDepartmentById(CORRECT_DEPARTMENT_ID);
    assertEquals(CORRECT_DEPARTMENT_ID, changedDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY, changedDepartment.getAverageSalary());
    assertEquals(CHANGED_DEPARTMENT_NAME, changedDepartment.getDepartmentName());
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void negativeTestGetDepartmentById() {
    LOGGER.debug("test DAO: run negativeTestGetDepartmentById()");
    assertNull(departmentDao.getDepartmentById(null));
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void negativeTestGetDepartmentByIncorrectId() {
    LOGGER.debug("test DAO: run negativeTestGetDepartmentByIncorrectId()");
    assertNull(departmentDao.getDepartmentById(INCORRECT_DEPARTMENT_ID));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestSaveNull() {
    LOGGER.debug("test DAO: run negativeTestSaveNull()");
    assertNull(departmentDao.saveDepartment(null));
  }

  @Test(expected = NullPointerException.class)
  public void negativeTestUpdateNull() {
    LOGGER.debug("test DAO: run negativeTestUpdateNull()");
    assertFalse(departmentDao.updateDepartment(null));
  }

}
