package com.nikolay.client;

import com.nikolay.client.exception.ServerDataAccessException;
import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * The type Employee rest dao.
 */
@Component
public class EmployeeRestDaoImpl implements EmployeeService {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    @Value("${app.url}")
    private String url;

    @Value("${point.employees}")
    private String employeesPoint;

    private RestTemplate restTemplate;

    /**
     * Instantiates a new Employee rest dao.
     *
     * @param restTemplate the rest template
     */
    public EmployeeRestDaoImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws ServerDataAccessException {
        LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
        Employee employee = restTemplate
                .getForObject(url + employeesPoint + employeeId, Employee.class);
        if (employee == null) {
            throw new ServerDataAccessException(
                    "Employee by identifier " + employeeId + " not found");
        }
        LOGGER.debug("EmployeeFullName = {}", employee.getFullName());
        return employee;
    }

    @Override
    public Long saveEmployee(Employee employee) throws ServerDataAccessException {
        LOGGER.debug("saveEmployee(employee): employeeFullName = {}", employee.getFullName());
        ResponseEntity<Long> responseEntity = restTemplate
                .postForEntity(url + employeesPoint, employee, Long.class);
        Long employeeId = responseEntity.getBody();
        LOGGER.debug("employeeId = {}", employeeId);
        if (employeeId == null) {
            throw new ServerDataAccessException("The employee was not saved");
        }

        return employeeId;
    }

    @Override
    public void updateEmployee(Employee employee) throws ServerDataAccessException {
        LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
        restTemplate.put(url + employeesPoint + employee.getId(), employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws ServerDataAccessException {
        LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
        restTemplate.delete(url + employeesPoint + employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() throws ServerDataAccessException {
        LOGGER.debug("getAllEmployees()");
        Employee[] employeesArray = restTemplate
                .getForObject(url + employeesPoint, Employee[].class);
        if (employeesArray == null) {
            throw new ServerDataAccessException("Employees not found");
        }
        return Arrays.asList(employeesArray);
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date)
            throws ServerDataAccessException {
        LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + employeesPoint)
                .queryParam("date", date);
        Employee[] employeesArray = restTemplate
                .getForObject(builder.toUriString(), Employee[].class);
        if (employeesArray == null) {
            throw new ServerDataAccessException("Employees not found");
        }
        return Arrays.asList(employeesArray);
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo)
            throws ServerDataAccessException {
        LOGGER.debug(
                "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
                dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + employeesPoint)
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo);
        Employee[] employeesArray = restTemplate
                .getForObject(builder.toUriString(), Employee[].class);
        if (employeesArray == null) {
            throw new ServerDataAccessException("Employees not found");
        }
        return Arrays.asList(employeesArray);
    }

}
