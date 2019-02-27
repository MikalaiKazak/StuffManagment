package com.nikolay.dao;

import static com.nikolay.dao.mapper.EmployeeMapper.EMPLOYEE_ID;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.nikolay.dao.mapper.EmployeeMapper;
import com.nikolay.model.Employee;

/**
 * The type Employee dao.
 */
@Component
public class EmployeeDaoImpl implements EmployeeDao {

  private static final Logger LOGGER = LogManager.getLogger();

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final EmployeeMapper employeeMapper = new EmployeeMapper();

  @Value("${parameters.employee_id}")
  private String parameterEmployeeId;

  @Value("${parameters.fullName}")
  private String parameterFullName;

  @Value("${parameters.birthday}")
  private String parameterEmployeeBirthday;

  @Value("${parameters.birthday_to}")
  private String parameterEmployeeBirthdayTo;

  @Value("${parameters.birthday_from}")
  private String parameterEmployeeBirthdayFrom;

  @Value("${parameters.department_id}")
  private String parameterDepartmentId;

  @Value("${parameters.salary}")
  private String parameterEmployeeSalary;

  @Value("${employee.GET_ALL_EMPLOYEE}")
  private String getAllEmployee;

  @Value("${employee.GET_EMPLOYEE_BY_ID}")
  private String getEmployeeById;

  @Value("${employee.GET_EMPLOYEE_BY_DATE_OF_BIRTHDAY}")
  private String getEmployeeByDateOfBirthday;

  @Value("${employee.GET_EMPLOYEE_BETWEEN_DATES_OF_BIRTHDAY}")
  private String getEmployeeBetweenDatesOfBirthday;

  @Value("${employee.ADD_EMPLOYEE}")
  private String addEmployee;

  @Value("${employee.UPDATE_EMPLOYEE}")
  private String updateEmployee;

  @Value("${employee.DELETE_EMPLOYEE}")
  private String deleteEmployee;

  /**
   * Instantiates a new Employee dao.
   *
   * @param dataSource the data source
   */
  @Autowired
  public EmployeeDaoImpl(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public Employee getEmployeeById(final Long employeeId) {
    LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
    return this.namedParameterJdbcTemplate
        .queryForObject(getEmployeeById, new MapSqlParameterSource(parameterEmployeeId, employeeId),
            employeeMapper);
  }

  @Override
  public Long saveEmployee(final Employee employee) {
    LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    this.namedParameterJdbcTemplate
        .update(addEmployee, getParameterSourceEmployee(employee), keyHolder,
            new String[]{EMPLOYEE_ID});
    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }

  @Override
  public Boolean updateEmployee(final Employee employee) {
    LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
    MapSqlParameterSource namedParameters = getParameterSourceEmployee(employee)
        .addValue(parameterEmployeeId, employee.getId());
    return this.namedParameterJdbcTemplate.update(updateEmployee, namedParameters) == 1;
  }

  @Override
  public Boolean deleteEmployee(final Long employeeId) {
    LOGGER.debug("deleteEmployee(employeeId): id = {}", employeeId);
    return this.namedParameterJdbcTemplate
        .update(deleteEmployee, new MapSqlParameterSource(parameterEmployeeId, employeeId)) == 1;
  }

  @Override
  public List<Employee> getAllEmployees() {
    LOGGER.debug("getAllEmployees()");
    return this.namedParameterJdbcTemplate.query(getAllEmployee, employeeMapper);
  }

  @Override
  public List<Employee> getEmployeesByDateOfBirthday(final LocalDate date) {
    LOGGER.debug("getEmployeeByDateOfBirthday(date)");
    return this.namedParameterJdbcTemplate
        .query(getEmployeeByDateOfBirthday, new MapSqlParameterSource(parameterEmployeeBirthday,
            date.toString()), employeeMapper);
  }

  @Override
  public List<Employee> getEmployeesBetweenDatesOfBirthday(final LocalDate dateFrom,
      final LocalDate dateTo) {
    LOGGER.debug("getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo)");
    MapSqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue(parameterEmployeeBirthdayFrom, dateFrom.toString())
        .addValue(parameterEmployeeBirthdayTo, dateTo.toString());
    return this.namedParameterJdbcTemplate
        .query(getEmployeeBetweenDatesOfBirthday, namedParameters, employeeMapper);
  }

  private MapSqlParameterSource getParameterSourceEmployee(Employee employee) {
    return new MapSqlParameterSource()
        .addValue(parameterDepartmentId, employee.getDepartmentId())
        .addValue(parameterFullName, employee.getFullName())
        .addValue(parameterEmployeeBirthday, employee.getBirthday().toString())
        .addValue(parameterEmployeeSalary, employee.getSalary());
  }
}
