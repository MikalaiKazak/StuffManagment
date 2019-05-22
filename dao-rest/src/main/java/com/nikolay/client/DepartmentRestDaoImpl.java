package com.nikolay.client;

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

import com.nikolay.client.exception.ServerDataAccessException;
import com.nikolay.dao.DepartmentDao;
import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;

/**
 * The type Department rest dao.
 */
@Component
public class DepartmentRestDaoImpl implements DepartmentDao {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();
  private final RestTemplate restTemplate;
  @Value("${department.endpoint}")
  private String url;
  @Value("${department.endpoint.with.id}")
  private String urlWithIdParam;

  /**
   * Instantiates a new Department rest dao.
   *
   * @param restTemplate the rest template
   */
  public DepartmentRestDaoImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  @Override
  public List<ResponseDepartmentDto> getAllDepartments() throws ServerDataAccessException {
    LOGGER.debug("getAllDepartments()");
    ResponseDepartmentDto[] departmentsArray = restTemplate
        .getForObject(url, ResponseDepartmentDto[].class);
    if (departmentsArray == null) {
      throw new ServerDataAccessException("Departments not found");
    }
    return Arrays.asList(departmentsArray);
  }

  @Override
  public ResponseDepartmentDto getDepartmentById(final Long departmentId)
      throws ServerDataAccessException {
    LOGGER.debug("getDepartmentById(departmentId): departmentId = {}", departmentId);
    ResponseDepartmentDto department = restTemplate
        .getForObject(urlWithIdParam, ResponseDepartmentDto.class, departmentId);
    if (department == null) {
      throw new ServerDataAccessException(
          "Department by identifier " + departmentId + " not found");
    }
    LOGGER.debug("DepartmentName = {}", department.getDepartmentName());
    return department;
  }

  @Override
  public Long saveDepartment(final Department department) throws ServerDataAccessException {
    LOGGER.debug("saveDepartment(department): departmentName = {}",
        department.getDepartmentName());
    ResponseEntity<Long> responseEntity = restTemplate
        .postForEntity(url, department, Long.class);
    Long departmentId = responseEntity.getBody();
    if (departmentId == null) {
      throw new ServerDataAccessException("The employee was not saved");
    }
    LOGGER.debug("departmentId = {}", departmentId);
    return departmentId;
  }

  @Override
  public Boolean updateDepartment(final Department department)
      throws ServerDataAccessException {
    LOGGER.debug("updateDepartment(department)");
    HttpEntity<?> entity = new HttpEntity<>(department, createHeaders());
    ResponseEntity<Boolean> response = restTemplate
        .exchange(urlWithIdParam, HttpMethod.PUT, entity, Boolean.class, department.getId());
    Boolean resultOperation = response.getBody();
    if (resultOperation == null || !resultOperation) {
      throw new ServerDataAccessException("The employee was not updated");
    } else {
      return resultOperation;
    }
  }

  @Override
  public Boolean deleteDepartment(final Long departmentId) throws ServerDataAccessException {
    LOGGER.debug("deleteDepartment(departmentId): departmentId = {}", departmentId);
    HttpEntity<?> entity = new HttpEntity<>(createHeaders());
    ResponseEntity<Boolean> response = restTemplate
        .exchange(urlWithIdParam, HttpMethod.DELETE, entity, Boolean.class, departmentId);
    Boolean resultOperation = response.getBody();
    if (resultOperation == null || !resultOperation) {
      throw new ServerDataAccessException("The employee was not deleted");
    } else {
      return resultOperation;
    }
  }

  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

}