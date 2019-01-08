package com.nikolay.client;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao-rest.xml"})
public class TestEmployeeRestDao {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${app.url}")
    private String url;

    @Value("${point.employees}")
    private String employeesPoint;

    @Autowired
    private EmployeeService employeeRestDao;

    @Autowired
    private RestTemplate mockRestTemplate;


    private Employee emp1;
    private Employee emp2;
    private Employee emp3;
    private LocalDate date;
    private LocalDate dateTo;
    private LocalDate dateFrom;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        emp1 = new Employee(3L, "Nikolay Kozak", LocalDate.of(1999, 2, 28),
                BigDecimal.valueOf(350));
        emp2 = new Employee(1L, 1L, "Nikolay", LocalDate.of(1999, 2, 28),
                BigDecimal.valueOf(350));
        emp3 = new Employee(2L, 1L, "Dmitry Kozak", LocalDate.of(2000, 12, 5),
                BigDecimal.valueOf(300));
        date = LocalDate.of(1999, 2, 28);
        dateFrom = LocalDate.of(1998, 2, 2);
        dateTo = LocalDate.of(2000, 12, 5);
        LOGGER.error("execute: beforeTest()");
    }

    @Test
    public void testGetEmployeeById() {
        LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeById()");
        when(mockRestTemplate.getForObject(url + employeesPoint + 1, Employee.class))
                .thenReturn(emp2);
        Employee employee = employeeRestDao.getEmployeeById(1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId().longValue());
        verify(mockRestTemplate, times(1)).getForObject(url + employeesPoint + 1, Employee.class);
    }

    @Test
    public void testGetAllEmployee() {
        LOGGER.debug("test TestEmployeeRestDao: run testGetAllEmployee()");
        when(mockRestTemplate.getForObject(url + employeesPoint, Employee[].class))
                .thenReturn(new Employee[]{emp2, emp3});
        List<Employee> employees = employeeRestDao.getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
        verify(mockRestTemplate, times(1)).getForObject(url + employeesPoint, Employee[].class);
    }

    @Test
    public void testSaveEmployee() {
        LOGGER.debug("test TestEmployeeRestDao: run testSaveEmployee()");
        when(mockRestTemplate.postForEntity(url + employeesPoint, emp1, Long.class))
                .thenReturn(new ResponseEntity<>(1L, HttpStatus.FOUND));
        Long employeeId = employeeRestDao.saveEmployee(emp1);
        assertNotNull(employeeId);
        assertEquals(1L, employeeId.longValue());
        verify(mockRestTemplate, times(1)).postForEntity(url + employeesPoint, emp1, Long.class);
    }

    @Test
    public void testGetEmployeeByDateOfBirthday() {
        LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeByDateOfBirthday()");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + employeesPoint)
                .queryParam("date", date);
        when(mockRestTemplate.getForObject(builder.toUriString(), Employee[].class))
                .thenReturn(new Employee[]{emp2});
        List<Employee> employeeList = employeeRestDao.getEmployeeByDateOfBirthday(date);
        assertNotNull(employeeList);
        assertEquals(1, employeeList.size());
        verify(mockRestTemplate, times(1)).getForObject(builder.toUriString(), Employee[].class);
    }

    @Test
    public void testGetEmployeeWithDateOfBirthday() {
        LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeWithDateOfBirthday()");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + employeesPoint)
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo);
        when(mockRestTemplate.getForObject(builder.toUriString(), Employee[].class))
                .thenReturn(new Employee[]{emp2, emp3});
        List<Employee> employeeList = employeeRestDao
                .getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        verify(mockRestTemplate, times(1)).getForObject(builder.toUriString(), Employee[].class);
    }

    @Test
    public void testDeleteEmployee() {
        LOGGER.debug("test TestEmployeeRestDao: run testDeleteEmployee()");
        doNothing().when(mockRestTemplate).delete(url + employeesPoint + 1);
        employeeRestDao.deleteEmployee(1L);
        verify(mockRestTemplate, times(1)).delete(url + employeesPoint + 1);
    }

    @Test
    public void testUpdateEmployee() {
        LOGGER.debug("test TestEmployeeRestDao: run testUpdateEmployee()");
        doNothing().when(mockRestTemplate).put(url + employeesPoint + 1, emp2);
        employeeRestDao.updateEmployee(emp2);
        verify(mockRestTemplate, times(1)).put(url + employeesPoint + 1, emp2);
    }

}
