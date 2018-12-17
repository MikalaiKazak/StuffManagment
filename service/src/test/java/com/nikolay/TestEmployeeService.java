package com.nikolay;

import com.nikolay.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestEmployeeService {

    @Mock
    EmployeeDAO employeeDAOMock;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee emp1;
    private Employee emp2;
    private Employee emp3;
    private List<Employee> employees;

    @Before
    public void setUp() {
        emp3 = new Employee(3L, "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(350));
        emp1 = new Employee(1L, 1L, "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(350));
        emp2 = new Employee(2L, 1L, "Dmitry Kozak", LocalDate.of(2000, 12, 5), BigDecimal.valueOf(300));
        employees = Arrays.asList(emp1, emp2);
    }

    @Test
    public void testGetEmployeeById() {
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
        when(employeeDAOMock.saveEmployee(emp3)).thenReturn(3L);
        Long employeeId = employeeService.saveEmployee(emp3);
        verify(employeeDAOMock).saveEmployee(emp3);
        assertNotNull(employeeId);
        assertEquals(3L, employeeId.longValue());
    }

    @Test
    public void testUpdateEmployee() {
        doNothing().when(employeeDAOMock).updateEmployee(emp1);
        employeeService.updateEmployee(emp1);
        verify(employeeDAOMock).updateEmployee(emp1);
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(employeeDAOMock).deleteEmployee(2L);
        employeeService.deleteEmployee(2L);
        verify(employeeDAOMock).deleteEmployee(2L);
    }

    @Test
    public void testGetAllEmployee() {
        when(employeeDAOMock.getAllEmployees()).thenReturn(employees);
        List<Employee> employees = employeeService.getAllEmployees();
        verify(employeeDAOMock).getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    public void testGetEmployeeByDateOfBirthday() {
        LocalDate date = LocalDate.of(1999, 2, 28);
        when(employeeDAOMock.getEmployeeByDateOfBirthday(date)).thenReturn(Collections.singletonList(emp1));
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(date);
        verify(employeeDAOMock).getEmployeeByDateOfBirthday(date);
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(emp1.getFullName(), employees.get(0).getFullName());
    }

    @Test
    public void testGetEmployeeBetweenDatesOfBirthday() {
        LocalDate dateFrom = LocalDate.of(1999, 2, 28);
        LocalDate dateTo = LocalDate.of(2000, 12, 5);
        when(employeeDAOMock.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo)).thenReturn(employees);
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
        verify(employeeDAOMock).getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(emp1.getFullName(), employees.get(0).getFullName());
        assertEquals(emp2.getFullName(), employees.get(1).getFullName());
    }
}

