package com.nikolay.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nikolay.dao.EmployeeDao;
import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao-rest.xml"})
public class TestEmployeeRestDao {

  private static final Logger LOGGER = LogManager.getLogger();

  @Value("${employee.endpoint}")
  private String url;

  @Value("${employee.endpoint.with.id}")
  private String urlWithParamUrl;

  @Autowired
  private EmployeeDao employeeRestDao;

  @Autowired
  private RestTemplate mockRestTemplate;


  private Employee emp1;
  private ResponseEmployeeDto emp2;
  private ResponseEmployeeDto emp3;
  private LocalDate date;
  private LocalDate dateTo;
  private LocalDate dateFrom;

  @Before
  public void setUp() {
    emp1 = new Employee(1L, 3L, "Nikolay Kozak", LocalDate.of(1999, 2, 28),
        BigDecimal.valueOf(350));
    emp2 = new ResponseEmployeeDto(1L, 1L, "Services", "Nikolay", LocalDate.of(1999, 2, 28),
        BigDecimal.valueOf(350));
    emp3 = new ResponseEmployeeDto(2L, 1L, "Services", "Dmitry Kozak", LocalDate.of(2000, 12, 5),
        BigDecimal.valueOf(300));
    date = LocalDate.of(1999, 2, 28);
    dateFrom = LocalDate.of(1998, 2, 2);
    dateTo = LocalDate.of(2000, 12, 5);
    LOGGER.error("execute: beforeTest()");
  }

  @Test
  public void testGetEmployeeById() {
    LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeById()");
    when(mockRestTemplate.getForObject(urlWithParamUrl, ResponseEmployeeDto.class, 1L))
        .thenReturn(emp2);
    ResponseEmployeeDto employee = employeeRestDao.getEmployeeById(1L);
    assertNotNull(employee);
    assertEquals(1L, employee.getId().longValue());
    verify(mockRestTemplate, times(1)).getForObject(urlWithParamUrl, ResponseEmployeeDto.class, 1L);
  }

  @Test
  public void testGetAllEmployee() {
    LOGGER.debug("test TestEmployeeRestDao: run testGetAllEmployee()");
    when(mockRestTemplate.getForObject(url, ResponseEmployeeDto[].class))
        .thenReturn(new ResponseEmployeeDto[]{emp2, emp3});
    List<ResponseEmployeeDto> employees = employeeRestDao.getAllEmployees();
    assertNotNull(employees);
    assertEquals(2, employees.size());
    verify(mockRestTemplate, times(1)).getForObject(url, ResponseEmployeeDto[].class);
  }

  @Test
  public void testSaveEmployee() {
    LOGGER.debug("test TestEmployeeRestDao: run testSaveEmployee()");
    when(mockRestTemplate.postForEntity(url, emp1, Long.class))
        .thenReturn(new ResponseEntity<>(1L, HttpStatus.FOUND));
    Long employeeId = employeeRestDao.saveEmployee(emp1);
    assertNotNull(employeeId);
    assertEquals(1L, employeeId.longValue());
    verify(mockRestTemplate, times(1)).postForEntity(url, emp1, Long.class);
  }

  @Test
  public void testGetEmployeeByDateOfBirthday() {
    LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeByDateOfBirthday()");
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("date", date);
    when(mockRestTemplate.getForObject(builder.toUriString(), ResponseEmployeeDto[].class))
        .thenReturn(new ResponseEmployeeDto[]{emp2});
    List<ResponseEmployeeDto> employeeList = employeeRestDao.getEmployeesByDateOfBirthday(date);
    assertNotNull(employeeList);
    assertEquals(1, employeeList.size());
    verify(mockRestTemplate, times(1))
        .getForObject(builder.toUriString(), ResponseEmployeeDto[].class);
  }

  @Test
  public void testGetEmployeeWithDateOfBirthday() {
    LOGGER.debug("test TestEmployeeRestDao: run testGetEmployeeWithDateOfBirthday()");
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("dateFrom", dateFrom)
        .queryParam("dateTo", dateTo);
    when(mockRestTemplate.getForObject(builder.toUriString(), ResponseEmployeeDto[].class))
        .thenReturn(new ResponseEmployeeDto[]{emp2, emp3});
    List<ResponseEmployeeDto> employeeList = employeeRestDao
        .getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
    assertNotNull(employeeList);
    assertEquals(2, employeeList.size());
    verify(mockRestTemplate, times(1))
        .getForObject(builder.toUriString(), ResponseEmployeeDto[].class);
  }

  @Test
  public void testDeleteEmployee() {
    LOGGER.debug("test TestEmployeeRestDao: run testDeleteEmployee()");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
    when(mockRestTemplate.exchange(urlWithParamUrl, HttpMethod.DELETE, entity, Boolean.class, 1L))
        .thenReturn(responseEntity);
    assertTrue(employeeRestDao.deleteEmployee(1L));
    verify(mockRestTemplate)
        .exchange(urlWithParamUrl, HttpMethod.DELETE, entity, Boolean.class, 1L);
  }

  @Test
  public void testUpdateEmployee() {
    LOGGER.debug("test TestEmployeeRestDao: run testUpdateEmployee()");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(emp1, headers);
    ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
    when(mockRestTemplate.exchange(urlWithParamUrl, HttpMethod.PUT, entity, Boolean.class, 1L))
        .thenReturn(responseEntity);
    assertTrue(employeeRestDao.updateEmployee(emp1));
    verify(mockRestTemplate).exchange(urlWithParamUrl, HttpMethod.PUT, entity, Boolean.class, 1L);
  }

}
