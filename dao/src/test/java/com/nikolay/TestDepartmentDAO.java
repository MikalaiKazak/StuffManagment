package com.nikolay;

import com.nikolay.impl.DepartmentDAOImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/** @author Mikalai Kazak */
public class TestDepartmentDAO {

  private DepartmentDAO departmentDAO;

  @Before
  public void setUp() {

    departmentDAO = new DepartmentDAOImpl();
  }

  @Test
  public void testGetDepartment() {
    Department department = departmentDAO.getDepartmentById(1L);
    Assert.assertNotNull(department);
    Assert.assertEquals("java", department.getDepartmentName());
  }

  @Test
  public void testSaveDepartment() {
    Department department = new Department(1L, "java", BigDecimal.valueOf(200));
    Long departmentId = departmentDAO.saveDepartment(department);
    Assert.assertEquals(department.getId(), departmentId);
  }

  @Test
  public void testDeleteDepartment() {
    Long departmentId = departmentDAO.deleteDepartment(1L);
    Assert.assertEquals(1L, departmentId.longValue());
    List<Department> departments = departmentDAO.getAllDepartments();
    Assert.assertEquals(1, departments.size());
  }

  @Test
  public void testGetAllDepartment() {
    List<Department> departments = departmentDAO.getAllDepartments();
    Assert.assertEquals(1, departments.size());
  }

  @Test
  public void testUpdateDepartment() {
    Department department = departmentDAO.getDepartmentById(1L);
    Department newDepartment = new Department(1L, "java", BigDecimal.valueOf(200));
    Long newDepartmentId = departmentDAO.updateDepartment(newDepartment);
    Assert.assertEquals(department.getId(), newDepartmentId);
  }
}
