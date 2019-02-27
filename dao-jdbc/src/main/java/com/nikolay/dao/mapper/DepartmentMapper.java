package com.nikolay.dao.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nikolay.model.Department;

public class DepartmentMapper implements RowMapper<Department> {

  public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
  public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
  public static final String AVG_SALARY = "AVG_SALARY";

  @Override
  public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
    Department department = new Department();
    department.setId(rs.getLong(DEPARTMENT_ID));
    department.setDepartmentName(rs.getString(DEPARTMENT_NAME));
    BigDecimal avgSalary = rs.getBigDecimal(AVG_SALARY);

    if (avgSalary == null) {
      department.setAverageSalary(BigDecimal.ZERO);
    } else {
      department.setAverageSalary(avgSalary);
    }
    return department;
  }

}
