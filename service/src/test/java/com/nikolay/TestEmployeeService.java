package com.nikolay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-service.xml"})
public class TestEmployeeService {

    @Qualifier("employeeDAO")
    @Autowired
    EmployeeDAO employeeDAOMock;

    @Autowired
    EmployeeService employeeService;


    @Test
    public void testGetEmployeeById() {

    }
}
