package com.nikolay.client;

import com.nikolay.client.exception.ServerDataAccessException;
import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
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
public class DepartmentRestDaoImpl implements DepartmentService {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    @Value("${app.url}")
    private String url;

    @Value("${point.departments}")
    private String departmentsPoint;

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
                .getForObject(url + departmentsPoint, Department[].class);
        if (departmentsArray == null) {
            throw new ServerDataAccessException("Departments not found");
        }
        return Arrays.asList(departmentsArray);
    }

    @Override
    public Department getDepartmentById(Long departmentId) throws ServerDataAccessException {
        LOGGER.debug("getDepartmentById(departmentId): departmentId = {}", departmentId);
        Department department = restTemplate
                .getForObject(url + departmentsPoint + departmentId, Department.class);
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
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + departmentsPoint)
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
                .postForEntity(url + departmentsPoint, department, Long.class);
        Long departmentId = responseEntity.getBody();
        if (departmentId == null) {
            throw new ServerDataAccessException("The employee was not saved");
        }
        LOGGER.debug("departmentId = {}", departmentId);
        return departmentId;
    }

    @Override
    public void updateDepartment(Department department) throws ServerDataAccessException {
        LOGGER.debug("updateDepartment(department): departmentId = {}", department.getId());
        restTemplate.put(url + departmentsPoint + department.getId(), department);
    }

    @Override
    public void deleteDepartment(Long departmentId) throws ServerDataAccessException {
        LOGGER.debug("deleteDepartment(departmentId): departmentId = {}", departmentId);
        restTemplate.delete(url + departmentsPoint + departmentId);
    }

}