package com.nikolay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/** @author Mikalai Kazak */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
public class TestDepartmentDAO {

    private final static long AMOUNT_DEPARTMENTS = 14L;
    private final static long DEPARTMENT_ID = 1L;
    private final static String DEPARTMENT_NAME = "Accounting";
    private final static String NEW_DEPARTMENT_NAME = "New department";
    private final static String CHANGED_DEPARTMENT_NAME = "Department";
    private final static BigDecimal DEPARTMENT_AVERAGE_SALARY = BigDecimal.valueOf(2399);

    @Autowired
    private DepartmentDAO departmentDAO;

    @Test
    public void testGetDepartment() {
        Department department = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertNotNull(department);
        Assert.assertEquals(DEPARTMENT_NAME, department.getDepartmentName());
    }

    @Test
    public void testGetDepartmentByName() {
        Department department = departmentDAO.getDepartmentByName("Java");
        Assert.assertNotNull(department);
        Assert.assertEquals("Java", department.getDepartmentName());
    }

    @Test
    public void testGetDepartmentAverageSalary(){
        BigDecimal averageSalary = departmentDAO.getDepartmentAverageSalary(DEPARTMENT_ID);
        Assert.assertNotNull(averageSalary);
        Assert.assertEquals(DEPARTMENT_AVERAGE_SALARY, averageSalary);
    }

    @Test
    public void testSaveDepartment() {
        Department department = new Department(NEW_DEPARTMENT_NAME, BigDecimal.valueOf(200));
        long sizeBefore = departmentDAO.getAllDepartments().size();
        Long departmentId = departmentDAO.saveDepartment(department);
        long sizeAfter = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Department newDepartment = departmentDAO.getDepartmentById(departmentId);
        Assert.assertNotNull(newDepartment);
        Assert.assertEquals(department.getDepartmentName(), newDepartment.getDepartmentName());
    }
    @Test
    public void testDeleteDepartment() {
        long sizeBefore = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(AMOUNT_DEPARTMENTS, sizeBefore);
        departmentDAO.deleteDepartment(sizeBefore);
        long sizeAfter = departmentDAO.getAllDepartments().size();
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void testGetAllDepartment() {
        List<Department> departments = departmentDAO.getAllDepartments();
        Assert.assertNotNull(departments);
        Assert.assertEquals(AMOUNT_DEPARTMENTS, departments.size());
    }

    @Test
    public void testUpdateDepartment() {
        Department department = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertNotNull(department);
        Assert.assertEquals(1L, department.getId().longValue());
        department.setDepartmentName(CHANGED_DEPARTMENT_NAME);
        departmentDAO.updateDepartment(department);
        Department newDepartment = departmentDAO.getDepartmentById(DEPARTMENT_ID);
        Assert.assertEquals(CHANGED_DEPARTMENT_NAME, newDepartment.getDepartmentName());
    }
}
