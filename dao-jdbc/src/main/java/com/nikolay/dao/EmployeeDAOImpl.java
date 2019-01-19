package com.nikolay.dao;

import com.nikolay.dao.mapper.EmployeeMapper;
import com.nikolay.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 */
@Component
public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${parameters.employee_id}")
    private String PARAMETER_EMPLOYEE_ID;

    @Value("${parameters.fullName}")
    private String PARAMETER_FULL_NAME;

    @Value("${parameters.birthday}")
    private String PARAMETER_EMPLOYEE_BIRTHDAY;

    @Value("${parameters.birthday_to}")
    private String PARAMETER_EMPLOYEE_BIRTHDAY_TO;

    @Value("${parameters.birthday_from}")
    private String PARAMETER_EMPLOYEE_BIRTHDAY_FROM;

    @Value("${parameters.department_id}")
    private String PARAMETER_DEPARTMENT_ID;

    @Value("${parameters.salary}")
    private String PARAMETER_EMPLOYEE_SALARY;

    @Value("${employee.GET_ALL_EMPLOYEE}")
    private String GET_ALL_EMPLOYEE;

    @Value("${employee.GET_EMPLOYEE_BY_ID}")
    private String GET_EMPLOYEE_BY_ID;

    @Value("${employee.GET_EMPLOYEE_BY_DEPARTMENT_ID}")
    private String GET_EMPLOYEE_BY_DEPARTMENT_ID;

    @Value("${employee.GET_EMPLOYEE_BY_DATE_OF_BIRTHDAY}")
    private String GET_EMPLOYEE_BY_DATE_OF_BIRTHDAY;

    @Value("${employee.GET_EMPLOYEE_BETWEEN_DATES_OF_BIRTHDAY}")
    private String GET_EMPLOYEE_BETWEEN_DATES_OF_BIRTHDAY;

    @Value("${employee.ADD_EMPLOYEE}")
    private String ADD_EMPLOYEE;

    @Value("${employee.UPDATE_EMPLOYEE}")
    private String UPDATE_EMPLOYEE;

    @Value("${employee.DELETE_EMPLOYEE}")
    private String DELETE_EMPLOYEE;

    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeDAOImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        LOGGER.debug("getEmployeeById(employeeId): id = {}", employeeId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_ID,
                employeeId);
        return this.namedParameterJdbcTemplate
                .queryForObject(GET_EMPLOYEE_BY_ID, namedParameters, employeeMapper);
    }

    @Override
    public Long saveEmployee(Employee employee) {
        LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(PARAMETER_FULL_NAME, employee.getFullName());
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY, employee.getBirthday().toString());
        namedParameters.addValue(PARAMETER_EMPLOYEE_SALARY, employee.getSalary());
        this.namedParameterJdbcTemplate.update(ADD_EMPLOYEE, namedParameters, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        LOGGER.debug("saveEmployee(employee): id = {}", id);
        return id;
    }

    @Override
    public void updateEmployee(Employee employee) {
        LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_EMPLOYEE_ID, employee.getId());
        namedParameters.addValue(PARAMETER_DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(PARAMETER_FULL_NAME, employee.getFullName());
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY, employee.getBirthday().toString());
        namedParameters.addValue(PARAMETER_EMPLOYEE_SALARY, employee.getSalary());
        long numberOfRowsAffected = (long) this.namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE, namedParameters);
        if (numberOfRowsAffected <= 0) {
          throw new IllegalArgumentException(String.format(
              "The employee with ID=%d does not exist in the database.",
              employee.getId()));
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        LOGGER.debug("deleteEmployee(employeeId): id = {}", employeeId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_ID,
                employeeId);
        long numberOfRowsAffected = (long) this.namedParameterJdbcTemplate.update(DELETE_EMPLOYEE, namedParameters);
        if (numberOfRowsAffected <= 0) {
          throw new IllegalArgumentException(String.format(
              "The employee with ID=%d does not exist in the database.",
              employeeId));
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.debug("getAllEmployees()");
        return this.namedParameterJdbcTemplate.query(GET_ALL_EMPLOYEE, employeeMapper);
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_BIRTHDAY,
                date.toString());
        return this.namedParameterJdbcTemplate
                .query(GET_EMPLOYEE_BY_DATE_OF_BIRTHDAY, namedParameters, employeeMapper);
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
        LOGGER.debug(
                "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
                dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY_FROM, dateFrom.toString());
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY_TO, dateTo.toString());
        return this.namedParameterJdbcTemplate
                .query(GET_EMPLOYEE_BETWEEN_DATES_OF_BIRTHDAY, namedParameters, employeeMapper);
    }

}
