package com.nikolay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/test-service.xml")
public class TestDepartmentService {


    @Autowired
    DepartmentService departmentService;

    @Qualifier("departmentDAOImpl")
    @Autowired
    DepartmentDAO departmentDAOMock;

    @Test
    public void testGetEmployeeById() {
        Mockito.when(departmentDAOMock.getDepartmentById(1L).getId()).thenReturn(1L);
        Department newDepartment = departmentService.getDepartmentById(1L);
        Assert.assertEquals(1L, newDepartment.getId().longValue());
    }

}
