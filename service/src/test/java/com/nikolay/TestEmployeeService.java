package com.nikolay;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestEmployeeService {

    @Autowired
    EmployeeDAO employeeDAOMock;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() {
    }
}
