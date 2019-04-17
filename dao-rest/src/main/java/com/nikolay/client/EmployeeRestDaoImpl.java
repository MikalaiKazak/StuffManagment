package com.nikolay.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nikolay.client.exception.ServerDataAccessException;
import com.nikolay.dao.EmployeeDao;
import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;

/**
 * The type Employee rest dao.
 */
@Component
public class EmployeeRestDaoImpl implements EmployeeDao {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  @Value("${employee.endpoint}")
  private String url;

  @Value("${employee.endpoint.with.id}")
  private String urlWithParamUrl;

  private final RestTemplate restTemplate;

  /**
   * Instantiates a new Employee rest dao.
   *
   * @param restTemplate the rest template
   */
  public EmployeeRestDaoImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEmployeeDto getEmployeeById(final Long employeeId) throws ServerDataAccessException {
    LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
    ResponseEmployeeDto employee = restTemplate
        .getForObject(urlWithParamUrl, ResponseEmployeeDto.class, employeeId);
    if (employee == null) {
      throw new ServerDataAccessException(
          "Employee by identifier " + employeeId + " not found");
    }
    LOGGER.debug("EmployeeFullName = {}", employee.getFullName());
    return employee;
  }

  @Override
  public Long saveEmployee(final Employee employee) throws ServerDataAccessException {
    LOGGER.debug("saveEmployee(employee): employeeFullName = {}", employee.getFullName());
    ResponseEntity<Long> responseEntity = restTemplate
        .postForEntity(url, employee, Long.class);
    Long employeeId = responseEntity.getBody();
    LOGGER.debug("employeeId = {}", employeeId);
    if (employeeId == null) {
      throw new ServerDataAccessException("The employee was not saved");
    }
    return employeeId;
  }

  @Override
  public Boolean updateEmployee(final Employee employee)
      throws ServerDataAccessException {
    LOGGER.debug("updateEmployee(employee)");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(employee, headers);
    ResponseEntity<Boolean> response = restTemplate
        .exchange(urlWithParamUrl, HttpMethod.PUT, entity, Boolean.class, employee.getId());
    return response.getBody();
  }

  @Override
  public Boolean deleteEmployee(final Long employeeId) throws ServerDataAccessException {
    LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<Boolean> response = restTemplate
        .exchange(urlWithParamUrl, HttpMethod.DELETE, entity, Boolean.class, employeeId);
    return response.getBody();
  }

  @Override
  public List<ResponseEmployeeDto> getAllEmployees() throws ServerDataAccessException {
    LOGGER.debug("getAllEmployees()");
    ResponseEmployeeDto[] employeesArray = restTemplate
        .getForObject(url, ResponseEmployeeDto[].class);
    if (employeesArray == null) {
      throw new ServerDataAccessException("Employees not found");
    }
    return Arrays.asList(employeesArray);
  }

  @Override
  public List<ResponseEmployeeDto> getEmployeesByDateOfBirthday(final LocalDate date)
      throws ServerDataAccessException {
    LOGGER.debug("getEmployeesByDateOfBirthday(date): date = {}",
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("date", date);
    ResponseEmployeeDto[] employeesArray = restTemplate
        .getForObject(builder.toUriString(), ResponseEmployeeDto[].class);
    if (employeesArray == null) {
      throw new ServerDataAccessException("Employees not found");
    }
    return Arrays.asList(employeesArray);
  }

  @Override
  public List<ResponseEmployeeDto> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo)
      throws ServerDataAccessException {
    LOGGER.debug(
        "getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
        dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("dateFrom", dateFrom)
        .queryParam("dateTo", dateTo);
    ResponseEmployeeDto[] employeesArray = restTemplate
        .getForObject(builder.toUriString(), ResponseEmployeeDto[].class);
    if (employeesArray == null) {
      throw new ServerDataAccessException("Employees not found");
    }
    return Arrays.asList(employeesArray);
  }

}
