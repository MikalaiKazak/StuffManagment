package com.nikolay.impl;

import com.nikolay.Department;
import com.nikolay.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/** @author Mikalai_Kazak@epam.com 10.12.2018 */
@Component
public class DepartmentDAOImpl implements DepartmentDAO {

    @Value("${department.DEPARTMENT_ID_COLUMN}")
    private String DEPARTMENT_ID_COLUMN;

    @Value("${department.DEPARTMENT_NAME_COLUMN}")
    private String DEPARTMENT_NAME_COLUMN;

    @Value("${parameters.department_id}")
    private String PARAMETER_DEPARTMENT_ID;

    @Value("${parameters.department_name}")
    private String PARAMETER_DEPARTMENT_NAME;

    @Value("${department.GET_ALL_DEPARTMENTS}")
    private String GET_ALL_DEPARTMENTS;

    @Value("${department.GET_DEPARTMENT_BY_ID}")
    private String GET_DEPARTMENT_BY_ID;

    @Value("${department.DELETE_DEPARTMENT}")
    private String DELETE_DEPARTMENT_BY_ID;

    @Value("${department.DELETE_EMPLOYEE_BY_DEPARTMENT_ID}")
    private String DELETE_EMPLOYEE_BY_DEPARTMENT_ID;

    @Value("${department.ADD_DEPARTMENT}")
    private String ADD_DEPARTMENT;

    @Value("${department.UPDATE_DEPARTMENT}")
    private String UPDATE_DEPARTMENT_BY_ID;

    @Value("${department.GET_DEPARTMENT_AVERAGE_SALARY}")
    private String GET_DEPARTMENT_AVERAGE_SALARY;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DepartmentDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getAllDepartments() {
        return this.namedParameterJdbcTemplate.query(GET_ALL_DEPARTMENTS, new DepartmentMapper());
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_DEPARTMENT_ID, departmentId);
        return this.namedParameterJdbcTemplate.queryForObject(GET_DEPARTMENT_BY_ID, namedParameters, new DepartmentMapper());
    }

    @Override
    public Long saveDepartment(Department department) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.getJdbcTemplate().update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(ADD_DEPARTMENT, new String[]{PARAMETER_DEPARTMENT_ID});
                    ps.setString(1, department.getDepartmentName());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateDepartment(Department department) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(PARAMETER_DEPARTMENT_ID, department.getId());
        namedParameters.addValue(PARAMETER_DEPARTMENT_NAME, department.getDepartmentName());
        this.namedParameterJdbcTemplate.update(UPDATE_DEPARTMENT_BY_ID, namedParameters);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_DEPARTMENT_ID, departmentId);
        this.namedParameterJdbcTemplate.update(DELETE_EMPLOYEE_BY_DEPARTMENT_ID, namedParameters);
        this.namedParameterJdbcTemplate.update(DELETE_DEPARTMENT_BY_ID, namedParameters);
    }

    @Override
    public BigDecimal getDepartmentAverageSalary(Long departmentId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(PARAMETER_DEPARTMENT_ID, departmentId);
        return this.namedParameterJdbcTemplate.queryForObject(GET_DEPARTMENT_AVERAGE_SALARY, namedParameters, BigDecimal.class);
    }

    /**
     * The type Department mapper.
     */
    final class DepartmentMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department = new Department();
            department.setId(rs.getLong(DEPARTMENT_ID_COLUMN));
            department.setDepartmentName(rs.getString(DEPARTMENT_NAME_COLUMN));
            return department;
        }
    }

}
