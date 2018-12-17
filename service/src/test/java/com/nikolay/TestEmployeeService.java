package com.nikolay;

import com.nikolay.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestEmployeeService {

    @Mock
    EmployeeDAO employeeDAOMock;

    @InjectMocks
    EmployeeServiceImpl employeeService;


    @Test
    public void testGetEmployeeById() {

    }
}
