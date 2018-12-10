package com.nikolay;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;


public class TestEmployeeDAO {

    private EmployeeDAO employeeDAO;

    @Before
    public void setUp(){
        employeeDAO = new EmployeeDAOImpl();
    }

    @Test
    public void testGetEmployee(){
        Employee employee = employeeDAO.getEmployeeById(1L);
        Assert.assertNotNull(employee);
        Assert.assertEquals("Nikolay Kozak", employee.getFullName());
    }

    @Test
    public void testSaveEmployee(){
        Employee employee = new Employee(1L, "java", "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(200), Currency.getInstance("USD"));
        Long employeeId = employeeDAO.saveEmployee(employee);
        Assert.assertEquals(employee.getId(), employeeId);
    }

    @Test
    public void testDeleteEmployee(){
        employeeDAO.deleteEmployee(1L);
        List<Employee> employees = employeeDAO.getAllEmployees();
        Assert.assertEquals(1, employees.size());
    }


    @Test
    public void testGetAllEmployee(){
        List<Employee> employees = employeeDAO.getAllEmployees();
        Assert.assertEquals(1, employees.size());

    }

    @Test
    public void testUpdateEmployee(){
        Employee employee = employeeDAO.getEmployeeById(1L);
        Employee newEmployee = new Employee(1L, "sql", "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(200), Currency.getInstance("USD"));
        Long newEmployeeId = employeeDAO.updateEmployee(newEmployee);
        Assert.assertEquals(employee.getId(), newEmployeeId);

    }
}
