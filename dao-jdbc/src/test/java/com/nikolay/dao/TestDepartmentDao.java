package com.nikolay.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nikolay.model.Department;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type Test department dao.
 *
 * @author Mikalai Kazak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestDepartmentDao {

  public static final Logger LOGGER = LogManager.getLogger();

  private static final long CORRECT_AMOUNT_DEPARTMENTS = 14L;

  private static final long CORRECT_DEPARTMENT_ID = 1L;
  private static final String CORRECT_DEPARTMENT_NAME = "Accounting";
  private static final BigDecimal CORRECT_DEPARTMENT_AVERAGE_SALARY = new BigDecimal(2399.5);

  private static final String NEW_DEPARTMENT_NAME = "DWBI";
  private static final BigDecimal NEW_DEPARTMENT_AVERAGE_SALARY = new BigDecimal(300.25);

  private static final String CHANGED_DEPARTMENT_NAME = "Department";

  private static final Long INCORRECT_DEPARTMENT_ID = 200L;

  private static final Department department = new Department(
      NEW_DEPARTMENT_NAME,
      NEW_DEPARTMENT_AVERAGE_SALARY
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
    Department newDepartment = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);
    assertNotNull(newDepartment);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY, newDepartment.getAverageSalary());
  }

  @Test
  public void testGetDepartmentByName() {
    LOGGER.debug("test DAO: run testGetDepartmentByName()");
    Department newDepartment = departmentDao.getDepartmentByName(CORRECT_DEPARTMENT_NAME);
    assertNotNull(department);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    assertEquals(CORRECT_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    assertEquals(CORRECT_DEPARTMENT_AVERAGE_SALARY,
        newDepartment.getAverageSalary());
  }

  @Test
  public void testSaveDepartment() {
    LOGGER.debug("test DAO: run testSaveDepartment()");
    long sizeBefore = departmentDao.getAllDepartments().size();
    Long departmentId = departmentDao.saveDepartment(department);
    long sizeAfter = departmentDao.getAllDepartments().size();
    assertEquals(sizeBefore + 1, sizeAfter);
    Department newDepartment = departmentDao.getDepartmentById(departmentId);
    assertNotNull(newDepartment);
    assertEquals(departmentId.longValue(), newDepartment.getId().longValue());
    assertEquals(department.getDepartmentName(), newDepartment.getDepartmentName());
  }

  @Test
  public void testDeleteDepartment() {
    LOGGER.debug("test DAO: run testDeleteDepartment()");
    long sizeBefore = departmentDao.getAllDepartments().size();
    assertEquals(CORRECT_AMOUNT_DEPARTMENTS, sizeBefore);
    departmentDao.deleteDepartment(CORRECT_DEPARTMENT_ID);
    long sizeAfter = departmentDao.getAllDepartments().size();
    assertEquals(sizeBefore - 1, sizeAfter);
  }

  @Test
  public void testGetAllDepartment() {
    LOGGER.debug("test DAO: run testGetAllDepartment()");
    List<Department> newDepartment = departmentDao.getAllDepartments();
    assertNotNull(newDepartment);
    assertEquals(CORRECT_AMOUNT_DEPARTMENTS, newDepartment.size());
  }

  @Test
  public void testUpdateDepartment() {
    LOGGER.debug("test DAO: run testUpdateDepartment()");
    Department newDepartment = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);
    assertNotNull(newDepartment);
    assertEquals(CORRECT_DEPARTMENT_ID, newDepartment.getId().longValue());
    newDepartment.setDepartmentName(CHANGED_DEPARTMENT_NAME);
    departmentDao.updateDepartment(newDepartment);
    Department changedDepartment = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);
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
  public void negativeTestGetDepartmentByName() {
    LOGGER.debug("test DAO: run negativeTestGetDepartmentByName()");
    assertNull(departmentDao.getDepartmentByName(null));
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
    departmentDao.updateDepartment(null);
  }

  @Test(expected = DuplicateKeyException.class)
  public void negativeTestSaveExistDepartment() {
    LOGGER.debug("test DAO: run negativeTestSaveExistDepartment()");
    Department newDepartment = departmentDao.getDepartmentById(CORRECT_DEPARTMENT_ID);
    assertNull(departmentDao.saveDepartment(newDepartment));
  }

}
