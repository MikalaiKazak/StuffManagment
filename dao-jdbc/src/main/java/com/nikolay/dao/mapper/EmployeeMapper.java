package com.nikolay.dao.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nikolay.model.dto.ResponseEmployeeDto;

public class EmployeeMapper implements RowMapper<ResponseEmployeeDto> {

  public static final String EMPLOYEE_ID = "EMPLOYEE_ID";
  public static final String EMPLOYEE_DEPARTMENT_ID = "EMPLOYEE_DEPARTMENT_ID";
  public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
  public static final String EMPLOYEE_FULL_NAME = "EMPLOYEE_FULL_NAME";
  public static final String EMPLOYEE_DATE_OF_BIRTHDAY = "EMPLOYEE_DATE_OF_BIRTHDAY";
  public static final String EMPLOYEE_SALARY = "EMPLOYEE_SALARY";

  @Override
  public ResponseEmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    ResponseEmployeeDto employee = new ResponseEmployeeDto();
    employee.setId(rs.getLong(EMPLOYEE_ID));
    employee.setDepartmentId(rs.getLong(EMPLOYEE_DEPARTMENT_ID));
    employee.setDepartmentName(rs.getString(DEPARTMENT_NAME));
    employee.setFullName(rs.getString(EMPLOYEE_FULL_NAME));

    employee.setSalary(rs.getBigDecimal(EMPLOYEE_SALARY));

    Date date = rs.getDate(EMPLOYEE_DATE_OF_BIRTHDAY);
    if (date != null) {
      employee.setBirthday(date.toLocalDate());
    }
    return employee;
  }
}
