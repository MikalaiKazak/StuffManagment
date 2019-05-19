package com.nikolay.dao.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nikolay.model.dto.ResponseDepartmentDto;

public class DepartmentMapper implements RowMapper<ResponseDepartmentDto> {

  public static final String DEPARTMENT_ID = "department_id";
  public static final String DEPARTMENT_NAME = "department_name";
  public static final String AVG_SALARY = "avg_salary";

  @Override
  public ResponseDepartmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    ResponseDepartmentDto department = new ResponseDepartmentDto();
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
