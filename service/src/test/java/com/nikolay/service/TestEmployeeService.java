package com.nikolay.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.nikolay.dao.EmployeeDAO;
import com.nikolay.model.Employee;
import com.nikolay.service.exception.OperationFailedException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-service-mock.xml"})
public class TestEmployeeService {

    public static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    EmployeeDAO employeeDAOMock;

    @Autowired
    EmployeeService employeeService;

    private Employee emp1;
    private Employee emp2;
    private Employee emp3;
    private List<Employee> employees;

    @Before
    public void setUp() {
        LOGGER.error("execute: setUp()");
        emp3 = new Employee(3L, "Nikolay Kozak", "Services", LocalDate.of(1999, 2, 28),
                BigDecimal.valueOf(350));
        emp1 = new Employee(1L, 1L, "Services", "Nikolay Kozak", LocalDate.of(1999, 2, 28),
                BigDecimal.valueOf(350));
        emp2 = new Employee(2L, 1L, "Services","Dmitry Kozak", LocalDate.of(2000, 12, 5),
                BigDecimal.valueOf(300));
        employees = Arrays.asList(emp1, emp2);
    }

    /**
     * After test.
     */
    @After
    public void afterTest() {
        verifyNoMoreInteractions(employeeDAOMock);
        reset(employeeDAOMock);
        LOGGER.error("execute: afterTest()");
    }

    @Test
    public void testGetEmployeeById() {
        LOGGER.debug("test Service: run testGetEmployeeById()");
        when(employeeDAOMock.getEmployeeById(1L)).thenReturn(emp1);
        Employee employee = employeeService.getEmployeeById(1L);
        verify(employeeDAOMock).getEmployeeById(1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId().longValue());
        assertEquals("Nikolay Kozak", employee.getFullName());
        assertEquals(1L, employee.getDepartmentId().longValue());
        assertEquals(LocalDate.of(1999, 2, 28), employee.getBirthday());
    }

    @Test
    public void testSaveEmployee() {
        LOGGER.debug("test Service: run testSaveEmployee()");
        when(employeeDAOMock.saveEmployee(emp3)).thenReturn(3L);
        Long employeeId = employeeService.saveEmployee(emp3);
        verify(employeeDAOMock).saveEmployee(emp3);
        assertNotNull(employeeId);
        assertEquals(3L, employeeId.longValue());
    }

    @Test
    public void testUpdateEmployee() {
        LOGGER.debug("test Service: run testUpdateEmployee()");
        doNothing().when(employeeDAOMock).updateEmployee(emp1);
        employeeService.updateEmployee(emp1);
        verify(employeeDAOMock).updateEmployee(emp1);
    }

    @Test
    public void testDeleteEmployee() {
        LOGGER.debug("test Service: run testDeleteEmployee()");
        doNothing().when(employeeDAOMock).deleteEmployee(2L);
        employeeService.deleteEmployee(2L);
        verify(employeeDAOMock).deleteEmployee(2L);
    }

    @Test
    public void testGetAllEmployee() {
        LOGGER.debug("test Service: run testGetAllEmployee()");
        when(employeeDAOMock.getAllEmployees()).thenReturn(employees);
        List<Employee> employees = employeeService.getAllEmployees();
        verify(employeeDAOMock).getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    public void testGetEmployeeByDateOfBirthday() {
        LOGGER.debug("test Service: run testGetEmployeeByDateOfBirthday()");
        LocalDate date = LocalDate.of(1999, 2, 28);
        when(employeeDAOMock.getEmployeeByDateOfBirthday(date))
                .thenReturn(Collections.singletonList(emp1));
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(date);
        verify(employeeDAOMock).getEmployeeByDateOfBirthday(date);
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(emp1.getFullName(), employees.get(0).getFullName());
    }

    @Test
    public void testGetEmployeeBetweenDatesOfBirthday() {
        LOGGER.debug("test Service: run testGetEmployeeBetweenDatesOfBirthday()");
        LocalDate dateFrom = LocalDate.of(1999, 2, 28);
        LocalDate dateTo = LocalDate.of(2000, 12, 5);
        when(employeeDAOMock.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo))
                .thenReturn(employees);
        List<Employee> employees = employeeService
                .getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
        verify(employeeDAOMock).getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(emp1.getFullName(), employees.get(0).getFullName());
        assertEquals(emp2.getFullName(), employees.get(1).getFullName());
    }
    @Test(expected = OperationFailedException.class)
    public void testGetEmployeeByIdException() {
        LOGGER.debug("test Service: run testGetEmployeeByIdException()");
        when(employeeDAOMock.getEmployeeById(-1L)).thenReturn(emp1);
        Employee employee = employeeService.getEmployeeById(-1L);
        verifyZeroInteractions(employeeDAOMock.getEmployeeById(1L));
    }

}

