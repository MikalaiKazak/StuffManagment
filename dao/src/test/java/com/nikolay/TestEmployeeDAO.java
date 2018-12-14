package com.nikolay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
public class TestEmployeeDAO {

    @Autowired
    private EmployeeDAO employeeDAO;

    private final static long AMOUNT_EMPLOYEES = 13L;
    private final static long EMPLOYEE_ID = 1L;
    private final static long NEW_DEPARTMENT_ID = 2L;
    private final static LocalDate DATE_FROM = LocalDate.of(1982, 4, 2);
    private final static LocalDate DATE_TO = LocalDate.of(1991, 7, 20);
    private final static BigDecimal EMPLOYEE_SALARY = BigDecimal.valueOf(200);
    private final static String EMPLOYEE_FULL_NAME = "Clem Hudspith";
    private final static String EMPLOYEE_FULL_NAME_NEW = "Channa Thinn";

    @Test
    public void testGetEmployee() {
        Employee employee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(employee);
        Assert.assertEquals(EMPLOYEE_FULL_NAME, employee.getFullName());
    }

    @Test
    public void testGetEmployeeByBirthday() {
        List<Employee> employeeList = employeeDAO.getEmployeeByDateOfBirthday(DATE_TO);
        Assert.assertNotNull(employeeList);
        Assert.assertEquals(1L, employeeList.size());
        Assert.assertEquals(EMPLOYEE_FULL_NAME_NEW, employeeList.get(0).getFullName());
    }

    @Test
    public void testGetEmployeeBetweenDatesOfBirtday() {
        List<Employee> employeeList = employeeDAO.getEmployeeBetweenDatesOfBirtday(DATE_FROM, DATE_TO);
        Assert.assertNotNull(employeeList);
        Assert.assertEquals(3, employeeList.size());
        Assert.assertEquals(EMPLOYEE_FULL_NAME, employeeList.get(0).getFullName());
    }

    @Test
    public void testGetAllEmployee() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        Assert.assertNotNull(employees);
        Assert.assertEquals(AMOUNT_EMPLOYEES, employees.size());
    }

    @Test
    public void testDeleteEmployee() {
        long sizeBefore = employeeDAO.getAllEmployees().size();
        employeeDAO.deleteEmployee(14L);
        long sizeAfter = employeeDAO.getAllEmployees().size();
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void testSaveEmployee() {
        long sizeBefore = employeeDAO.getAllEmployees().size();
        Employee employee = new Employee(sizeBefore + 1, 2L, "Nikolay Kozak", LocalDate.of(1999, 12, 28), BigDecimal.valueOf(300));
        long employeeId = employeeDAO.saveEmployee(employee);
        long sizeAfter = employeeDAO.getAllEmployees().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Employee newEmployee = employeeDAO.getEmployeeById(employeeId);
        Assert.assertNotNull(newEmployee);
        Assert.assertEquals(2L, newEmployee.getDepartmentId().longValue());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(employee);
        Assert.assertEquals(1L, employee.getId().longValue());
        employee.setDepartmentId(NEW_DEPARTMENT_ID);
        employee.setSalary(EMPLOYEE_SALARY);
        employeeDAO.updateEmployee(employee);
        Employee newEmployee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(newEmployee);
        Assert.assertEquals(NEW_DEPARTMENT_ID, newEmployee.getDepartmentId().byteValue());
        Assert.assertEquals(EMPLOYEE_SALARY, newEmployee.getSalary());
    }
}
