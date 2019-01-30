package com.nikolay.dao;

import com.nikolay.dao.mapper.DepartmentMapper;
import com.nikolay.model.Department;
import java.sql.PreparedStatement;
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
 * The type Department dao.
 */
@Component
public class DepartmentDaoImpl implements DepartmentDao {

  private static final Logger LOGGER = LogManager.getLogger();

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final DepartmentMapper departmentMapper = new DepartmentMapper();

  @Value("${parameters.department_id}")
  private String parameterDepartmentId;

  @Value("${parameters.department_name}")
  private String parameterDepartmentName;

  @Value("${department.GET_ALL_DEPARTMENTS}")
  private String getAllDepartments;

  @Value("${department.GET_DEPARTMENT_BY_ID}")
  private String getDepartmentById;

  @Value("${department.GET_DEPARTMENT_BY_NAME}")
  private String getDepartmentByName;

  @Value("${department.DELETE_DEPARTMENT}")
  private String deleteDepartmentById;

  @Value("${department.DELETE_EMPLOYEE_BY_DEPARTMENT_ID}")
  private String deleteEmployeeByDepartmentId;

  @Value("${department.ADD_DEPARTMENT}")
  private String addDepartment;

  @Value("${department.UPDATE_DEPARTMENT}")
  private String updateDepartmentById;

  /**
   * Instantiates a new Department dao.
   *
   * @param dataSource the data source
   */
  @Autowired
  public DepartmentDaoImpl(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<Department> getAllDepartments() {
    LOGGER.debug("getAllDepartments()");
    return this.namedParameterJdbcTemplate.query(getAllDepartments, departmentMapper);
  }

  @Override
  public Department getDepartmentById(Long departmentId) {
    LOGGER.debug("getDepartmentById(id): id = {}", departmentId);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterDepartmentId,
        departmentId);
    return this.namedParameterJdbcTemplate
        .queryForObject(getDepartmentById, namedParameters, departmentMapper);
  }

  @Override
  public Department getDepartmentByName(String departmentName) {
    LOGGER.debug("getDepartmentByName(departmentName): departmentName = {}", departmentName);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterDepartmentName,
        departmentName);
    return this.namedParameterJdbcTemplate
        .queryForObject(getDepartmentByName, namedParameters, departmentMapper);
  }

  @Override
  public Long saveDepartment(Department department) {
    LOGGER.debug("saveDepartment(department): departmentName = {}",
        department.getDepartmentName());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    this.namedParameterJdbcTemplate.getJdbcTemplate().update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(addDepartment,
              new String[]{parameterDepartmentId});
          ps.setString(1, department.getDepartmentName());
          return ps;
        },
        keyHolder);
    Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
    LOGGER.debug("saveDepartment(department): id = {}", id);
    return id;
  }

  @Override
  public void updateDepartment(Department department) {
    LOGGER.debug("updateDepartment(department): departmentId = {}", department.getId());
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue(parameterDepartmentId, department.getId());
    namedParameters.addValue(parameterDepartmentName, department.getDepartmentName());
    this.namedParameterJdbcTemplate.update(updateDepartmentById, namedParameters);

  }

  @Override
  public void deleteDepartment(Long departmentId) {
    LOGGER.debug("deleteDepartment(id): id = {}", departmentId);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterDepartmentId,
        departmentId);
    this.namedParameterJdbcTemplate.update(deleteEmployeeByDepartmentId, namedParameters);
    this.namedParameterJdbcTemplate.update(deleteDepartmentById, namedParameters);
  }

}
