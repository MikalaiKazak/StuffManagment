package com.nikolay.dao;

import com.nikolay.model.Department;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestDepartmentDAO {

    public static final Logger LOGGER = LogManager.getLogger();

    private final static long AMOUNT_DEPARTMENTS = 14L;
    private final static long DEPARTMENT_ID = 1L;
    private final static String DEPARTMENT_NAME = "Accounting";
    private final static String NEW_DEPARTMENT_NAME = "New department";
    private final static String CHANGED_DEPARTMENT_NAME = "Department";
    private final static BigDecimal DEPARTMENT_AVERAGE_SALARY = new BigDecimal(2399.5);

    @Autowired
    private DepartmentDAO departmentDAO;

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
        LOGGER.debug("test DAO: run testGetDepartment()");
        Department department = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertNotNull(department);
        Assert.assertEquals(DEPARTMENT_NAME, department.getDepartmentName());
        Assert.assertEquals(DEPARTMENT_AVERAGE_SALARY, department.getAverageSalary().stripTrailingZeros());
    }

    @Test
    public void testGetDepartmentByName() {
        LOGGER.debug("test DAO: run testGetDepartmentByName()");
        Department department = departmentDAO.getDepartmentByName(DEPARTMENT_NAME);
        Assert.assertNotNull(department);
        Assert.assertEquals(DEPARTMENT_ID, department.getId().longValue());
        Assert.assertEquals(DEPARTMENT_NAME, department.getDepartmentName());
        Assert.assertEquals(DEPARTMENT_AVERAGE_SALARY, department.getAverageSalary().stripTrailingZeros());
    }

    @Test
    public void testSaveDepartment() {
        LOGGER.debug("test DAO: run testSaveDepartment()");
        Department department = new Department(NEW_DEPARTMENT_NAME, DEPARTMENT_AVERAGE_SALARY);
        long sizeBefore = departmentDAO.getAllDepartments().size();
        Long departmentId = departmentDAO.saveDepartment(department);
        long sizeAfter = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Department newDepartment = departmentDAO.getDepartmentById(departmentId);
        Assert.assertNotNull(newDepartment);
        Assert.assertEquals(department.getDepartmentName(), newDepartment.getDepartmentName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteDepartmentException(){
        LOGGER.debug("test DAO: run testDeleteDepartmentException()");
        departmentDAO.deleteDepartment(100L);
    }

    @Test
    public void testDeleteDepartment() {
        LOGGER.debug("test DAO: run testDeleteDepartment()");
        long sizeBefore = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(AMOUNT_DEPARTMENTS, sizeBefore);
        departmentDAO.deleteDepartment(14L);
        long sizeAfter = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void testGetAllDepartment() {
        LOGGER.debug("test DAO: run testGetAllDepartment()");
        List<Department> departments = departmentDAO.getAllDepartments();
        Assert.assertNotNull(departments);
        Assert.assertEquals(AMOUNT_DEPARTMENTS, departments.size());
    }

    @Test
    public void testUpdateDepartment() {
        LOGGER.debug("test DAO: run testUpdateDepartment()");
        Department department = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertNotNull(department);
        Assert.assertEquals(1L, department.getId().longValue());
        department.setDepartmentName(CHANGED_DEPARTMENT_NAME);
        departmentDAO.updateDepartment(department);
        Department newDepartment = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertEquals(CHANGED_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    }
}
