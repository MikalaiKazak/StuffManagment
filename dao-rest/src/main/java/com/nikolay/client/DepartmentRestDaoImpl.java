package com.nikolay.client;

import com.nikolay.client.exception.ServerDataAccessException;
import com.nikolay.dao.DepartmentDAO;
import com.nikolay.model.Department;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The type Department rest dao.
 */
@Component
public class DepartmentRestDaoImpl implements DepartmentDAO {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    @Value("${department.endpoint}")
    private String url;

    @Value("${department.endpoint.with.id}")
    private String urlWithIdParam;

    private RestTemplate restTemplate;

    /**
     * Instantiates a new Department rest dao.
     *
     * @param restTemplate the rest template
     */
    public DepartmentRestDaoImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Department> getAllDepartments() throws ServerDataAccessException {
        LOGGER.debug("getAllDepartments()");
        Department[] departmentsArray = restTemplate
                .getForObject(url, Department[].class);
        if (departmentsArray == null) {
            throw new ServerDataAccessException("Departments not found");
        }
        return Arrays.asList(departmentsArray);
    }

    @Override
    public Department getDepartmentById(Long departmentId) throws ServerDataAccessException {
        LOGGER.debug("getDepartmentById(departmentId): departmentId = {}", departmentId);
        Department department = restTemplate
                .getForObject(urlWithIdParam, Department.class, departmentId);
        if (department == null) {
            throw new ServerDataAccessException(
                    "Department by identifier " + departmentId + " not found");
        }
        LOGGER.debug("DepartmentName = {}", department.getDepartmentName());
        return department;
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws ServerDataAccessException {
        LOGGER.debug("getDepartmentByName(departmentName): departmentName = {}", departmentName);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", departmentName);
        Department department = restTemplate
                .getForObject(builder.toUriString(), Department.class);
        if (department == null) {
            throw new ServerDataAccessException(
                    "Department by name: " + departmentName + " not found");
        }
        return department;
    }

    @Override
    public Long saveDepartment(Department department) throws ServerDataAccessException {
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
    public Long updateDepartment(Department department) throws ServerDataAccessException {
        LOGGER.debug("updateDepartment(department): departmentId = {}", department.getId());
        Long departmentId = department.getId();
        restTemplate.put(urlWithIdParam, department, departmentId);
        return departmentId;
    }

    @Override
    public Long deleteDepartment(Long departmentId) throws ServerDataAccessException {
        LOGGER.debug("deleteDepartment(departmentId): departmentId = {}", departmentId);
        restTemplate.delete(urlWithIdParam, departmentId);
        return departmentId;
    }

}