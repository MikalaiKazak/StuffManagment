package com.nikolay.dao;

import static com.nikolay.dao.mapper.DepartmentMapper.DEPARTMENT_ID;

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

import com.nikolay.dao.mapper.DepartmentMapper;
import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;

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
  public List<ResponseDepartmentDto> getAllDepartments() {
    LOGGER.debug("getAllDepartments()");
    return this.namedParameterJdbcTemplate.query(getAllDepartments, departmentMapper);
  }

  @Override
  public ResponseDepartmentDto getDepartmentById(final Long departmentId) {
    LOGGER.debug("getDepartmentById(id): id = {}", departmentId);
    return this.namedParameterJdbcTemplate
        .queryForObject(getDepartmentById, new MapSqlParameterSource(parameterDepartmentId,
            departmentId), departmentMapper);
  }

  @Override
  public Long saveDepartment(final Department department) {
    LOGGER.debug("saveDepartment(department): departmentName = {}",
        department.getDepartmentName());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    this.namedParameterJdbcTemplate
        .update(addDepartment, new MapSqlParameterSource(parameterDepartmentName,
            department.getDepartmentName()), keyHolder, new String[]{"department_id"});
    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }

  @Override
  public Boolean updateDepartment(final Department department) {
    LOGGER.debug("updateDepartment(department)");
    MapSqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue(parameterDepartmentId, department.getId())
        .addValue(parameterDepartmentName, department.getDepartmentName());
    return this.namedParameterJdbcTemplate.update(updateDepartmentById, namedParameters) == 1;

  }

  @Override
  public Boolean deleteDepartment(final Long departmentId) {
    LOGGER.debug("deleteDepartment(id): id = {}", departmentId);
    SqlParameterSource namedParameters = new MapSqlParameterSource(parameterDepartmentId,
        departmentId);
    this.namedParameterJdbcTemplate.update(deleteEmployeeByDepartmentId, namedParameters);
    return this.namedParameterJdbcTemplate.update(deleteDepartmentById, namedParameters) == 1;
  }

}
