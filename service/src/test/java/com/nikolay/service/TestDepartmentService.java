package com.nikolay.service;

import com.nikolay.dao.DepartmentDAO;
import com.nikolay.model.Department;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-service-mock.xml"})
public class TestDepartmentService {

    @Autowired
    private DepartmentDAO departmentDAOMock;

    @Autowired
    private DepartmentService departmentService;

    private Department dep1;
    private Department dep2;
    private List<Department> departments;

    @Before
    public void setUp() {
        dep1 = new Department("New Department", BigDecimal.valueOf(500));
        dep2 = new Department(14L, "Services", BigDecimal.valueOf(3249));
        departments = Arrays.asList(dep1, dep2);
    }

    @Test
    public void testGetDepartmentById() {
        when(departmentDAOMock.getDepartmentById(14L)).thenReturn(dep2);
        Department department = departmentService.getDepartmentById(14L);
        verify(departmentDAOMock).getDepartmentById(14L);
        assertNotNull(department);
        assertEquals(14L, department.getId().longValue());
        assertEquals("Services", department.getDepartmentName());
        assertEquals(BigDecimal.valueOf(3249), department.getAverageSalary());
    }

    @Test
    public void testGetDepartmentByName() {
        when(departmentDAOMock.getDepartmentByName("Services")).thenReturn(dep2);
        Department department = departmentService.getDepartmentByName("Services");
        verify(departmentDAOMock).getDepartmentByName("Services");
        assertNotNull(department);
        assertEquals("Services", department.getDepartmentName());
    }

    @Test
    public void testGetAllDepartment() {
        when(departmentDAOMock.getAllDepartments()).thenReturn(departments);
        List<Department> departmentList = departmentService.getAllDepartments();
        verify(departmentDAOMock).getAllDepartments();
        assertNotNull(departmentList);
        assertEquals(2, departmentList.size());
    }

    @Test
    public void testSaveDepartment() {
        when(departmentDAOMock.saveDepartment(dep1)).thenReturn(15L);
        Long departmentId = departmentService.saveDepartment(dep1);
        verify(departmentDAOMock).saveDepartment(dep1);
        assertNotNull(departmentId);
        assertEquals(15L, departmentId.longValue());
    }

    @Test
    public void testGetDepartmentAverageSalary() {
        when(departmentDAOMock.getDepartmentAverageSalary(14L)).thenReturn(dep2.getAverageSalary());
        BigDecimal averageSalary = departmentService.getDepartmentAverageSalary(14L);
        verify(departmentDAOMock).getDepartmentAverageSalary(14L);
        assertNotNull(averageSalary);
        assertEquals(dep2.getAverageSalary(), averageSalary);
    }


    @Test
    public void testUpdateDepartment() {
        doNothing().when(departmentDAOMock).updateDepartment(dep2);
        departmentService.updateDepartment(dep2);
        verify(departmentDAOMock).updateDepartment(dep2);
    }

    @Test
    public void testDeleteDepartment() {
        doNothing().when(departmentDAOMock).deleteDepartment(anyLong());
        departmentService.deleteDepartment(14L);
        departmentService.deleteDepartment(13L);
        verify(departmentDAOMock, times(2)).deleteDepartment(anyLong());
    }
}
