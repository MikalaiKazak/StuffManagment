package com.nikolay.dao;

import com.nikolay.dao.mapper.EmployeeMapper;
import com.nikolay.model.Employee;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * The type Employee dao.
 *
 * @author Mikalai_Kazak @epam.com 10.12.2018
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
  public Employee getEmployeeById(Long employeeId) {
    LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterEmployeeId,
        employeeId);
    return this.namedParameterJdbcTemplate
        .queryForObject(getEmployeeById, namedParameters, employeeMapper);
  }

  @Override
  public Long saveEmployee(Employee employee) {
    LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue(parameterDepartmentId, employee.getDepartmentId());
    namedParameters.addValue(parameterFullName, employee.getFullName());
    namedParameters.addValue(parameterEmployeeBirthday, employee.getBirthday().toString());
    namedParameters.addValue(parameterEmployeeSalary, employee.getSalary());
    this.namedParameterJdbcTemplate.update(addEmployee, namedParameters, keyHolder);
    Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
    LOGGER.debug("saveEmployee(employee): id = {}", id);
    return id;
  }

  @Override
  public void updateEmployee(Employee employee) {
    LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue(parameterEmployeeId, employee.getId());
    namedParameters.addValue(parameterDepartmentId, employee.getDepartmentId());
    namedParameters.addValue(parameterFullName, employee.getFullName());
    namedParameters.addValue(parameterEmployeeBirthday, employee.getBirthday().toString());
    namedParameters.addValue(parameterEmployeeSalary, employee.getSalary());
    this.namedParameterJdbcTemplate.update(updateEmployee, namedParameters);
  }

  @Override
  public void deleteEmployee(Long employeeId) {
    LOGGER.debug("deleteEmployee(employeeId): id = {}", employeeId);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterEmployeeId,
        employeeId);
    this.namedParameterJdbcTemplate.update(deleteEmployee, namedParameters);
  }

  @Override
  public List<Employee> getAllEmployees() {
    LOGGER.debug("getAllEmployees()");
    return this.namedParameterJdbcTemplate.query(getAllEmployee, employeeMapper);
  }

  @Override
  public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
    LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterEmployeeBirthday,
        date.toString());
    return this.namedParameterJdbcTemplate
        .query(getEmployeeByDateOfBirthday, namedParameters, employeeMapper);
  }

  @Override
  public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
    LOGGER.debug(
        "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
        dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue(parameterEmployeeBirthdayFrom, dateFrom.toString());
    namedParameters.addValue(parameterEmployeeBirthdayTo, dateTo.toString());
    return this.namedParameterJdbcTemplate
        .query(getEmployeeBetweenDatesOfBirthday, namedParameters, employeeMapper);
  }

}
