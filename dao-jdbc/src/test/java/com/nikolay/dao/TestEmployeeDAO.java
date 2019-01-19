package com.nikolay.dao;

import com.nikolay.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * The type Test employee dao.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath*:/test-dao.xml"})
public class TestEmployeeDAO {

    public static final Logger LOGGER = LogManager.getLogger();
    private final static long AMOUNT_EMPLOYEES = 13L;
    private final static long EMPLOYEE_ID = 1L;
    private final static long NEW_DEPARTMENT_ID = 2L;
    private final static LocalDate DATE_FROM = LocalDate.of(1982, 4, 2);
    private final static LocalDate DATE_TO = LocalDate.of(1991, 7, 20);
    private final static BigDecimal EMPLOYEE_SALARY = BigDecimal.valueOf(200.12);
    private final static String EMPLOYEE_FULL_NAME = "Clem Hudspith";
    @Autowired
    private EmployeeDAO employeeDAO;

    @Before
    public void beforeTest() {
        LOGGER.error("execute: beforeTest()");
    }

    @After
    public void afterTest() {
        LOGGER.error("execute: afterTest()");
    }

    @Test
    public void testGetEmployee() {
        LOGGER.debug("test DAO: run testGetEmployee()");
        Employee employee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(employee);
        Assert.assertEquals(EMPLOYEE_FULL_NAME, employee.getFullName());
    }

    @Test
    public void testGetEmployeeByBirthday() {
        LOGGER.debug("test DAO: run testGetEmployeeByBirthday()");
        List<Employee> employeeList = employeeDAO.getEmployeeByDateOfBirthday(DATE_FROM);
        Assert.assertNotNull(employeeList);
        Assert.assertEquals(1, employeeList.size());
    }

    @Test
    public void testGetEmployeeBetweenDatesOfBirthday() {
        LOGGER.debug("test DAO: run testGetEmployeeBetweenDatesOfBirthday()");
        List<Employee> employeeList = employeeDAO
                .getEmployeeBetweenDatesOfBirthday(DATE_FROM, DATE_TO);
        Assert.assertNotNull(employeeList);
        Assert.assertEquals(3, employeeList.size());
        Assert.assertEquals(EMPLOYEE_FULL_NAME, employeeList.get(0).getFullName());
    }

    @Test
    public void testGetAllEmployee() {
        LOGGER.debug("test DAO: run testGetAllEmployee()");
        List<Employee> employees = employeeDAO.getAllEmployees();
        Assert.assertNotNull(employees);
        Assert.assertEquals(AMOUNT_EMPLOYEES, employees.size());
    }

    @Test
    public void testDeleteEmployee() {
        LOGGER.debug("test DAO: run testDeleteEmployee()");
        long sizeBefore = employeeDAO.getAllEmployees().size();
        employeeDAO.deleteEmployee(14L);
        long sizeAfter = employeeDAO.getAllEmployees().size();
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void testSaveEmployee() {
        LOGGER.debug("test DAO: run testSaveEmployee()");
        long sizeBefore = employeeDAO.getAllEmployees().size();
        Employee employee = new Employee(sizeBefore + 1, 2L, "Nikolay Kozak",
                LocalDate.of(1999, 12, 28), BigDecimal.valueOf(300));
        long employeeId = employeeDAO.saveEmployee(employee);
        long sizeAfter = employeeDAO.getAllEmployees().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Employee newEmployee = employeeDAO.getEmployeeById(employeeId);
        Assert.assertNotNull(newEmployee);
        Assert.assertEquals(2L, newEmployee.getDepartmentId().longValue());
    }

    @Test
    public void testSaveEmployeeWithFloatSalary(){
        LOGGER.debug("test DAO: run testSaveEmployeeWithFloatSalary()");
        long sizeBefore = employeeDAO.getAllEmployees().size();
        Employee employee = new Employee(sizeBefore + 1, 2L, "Nikolay Kozak",
            LocalDate.of(1999, 12, 28), BigDecimal.valueOf(245.51));
        long employeeId = employeeDAO.saveEmployee(employee);
        long sizeAfter = employeeDAO.getAllEmployees().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Employee newEmployee = employeeDAO.getEmployeeById(employeeId);
        Assert.assertNotNull(newEmployee);
        Assert.assertEquals(employee.getSalary(), newEmployee.getSalary().stripTrailingZeros());
        Assert.assertEquals(2L, newEmployee.getDepartmentId().longValue());
    }

    @Test
    public void testUpdateEmployee() {
        LOGGER.debug("test DAO: run testUpdateEmployee()");
        Employee employee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(employee);
        Assert.assertEquals(1L, employee.getId().longValue());
        employee.setDepartmentId(NEW_DEPARTMENT_ID);
        employee.setSalary(EMPLOYEE_SALARY);
        employeeDAO.updateEmployee(employee);
        Employee newEmployee = employeeDAO.getEmployeeById(EMPLOYEE_ID);
        Assert.assertNotNull(newEmployee);
        Assert.assertEquals(NEW_DEPARTMENT_ID, newEmployee.getDepartmentId().byteValue());
        Assert.assertEquals(EMPLOYEE_SALARY, newEmployee.getSalary().stripTrailingZeros());
    }
}
