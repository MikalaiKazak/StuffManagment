package com.nikolay.impl;

import com.nikolay.Employee;
import com.nikolay.EmployeeDAO;
import com.nikolay.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 */
@Component
public class EmployeeDAOImpl implements EmployeeDAO {

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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeDAOImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_ID, employeeId);
        return this.namedParameterJdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID, namedParameters, employeeMapper);
    }

    @Override
    public Long saveEmployee(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.getJdbcTemplate().update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(ADD_EMPLOYEE, new String[]{PARAMETER_EMPLOYEE_ID});
                    ps.setLong(1, employee.getDepartmentId());
                    ps.setString(2, employee.getFullName());
                    ps.setDate(3, Date.valueOf(employee.getBirthday()));
                    ps.setBigDecimal(4, employee.getSalary());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateEmployee(Employee employee) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_EMPLOYEE_ID, employee.getId());
        namedParameters.addValue(PARAMETER_DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(PARAMETER_FULL_NAME, employee.getFullName());
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY, employee.getBirthday());
        namedParameters.addValue(PARAMETER_EMPLOYEE_SALARY, employee.getSalary());
        namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE, namedParameters);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_ID, employeeId);
        this.namedParameterJdbcTemplate.update(DELETE_EMPLOYEE, namedParameters);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.namedParameterJdbcTemplate.query(GET_ALL_EMPLOYEE, employeeMapper);
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_EMPLOYEE_BIRTHDAY, date.toString());
        return this.namedParameterJdbcTemplate.query(GET_EMPLOYEE_BY_DATE_OF_BIRTHDAY, namedParameters, employeeMapper);
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY_FROM, dateFrom.toString());
        namedParameters.addValue(PARAMETER_EMPLOYEE_BIRTHDAY_TO, dateTo.toString());
        return this.namedParameterJdbcTemplate.query(GET_EMPLOYEE_BETWEEN_DATES_OF_BIRTHDAY, namedParameters, employeeMapper);
    }

}
