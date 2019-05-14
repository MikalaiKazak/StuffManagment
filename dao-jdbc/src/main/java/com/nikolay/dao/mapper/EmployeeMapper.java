package com.nikolay.dao.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nikolay.model.dto.ResponseEmployeeDto;

public class EmployeeMapper implements RowMapper<ResponseEmployeeDto> {

  public static final String EMPLOYEE_ID = "employee_id";
  public static final String EMPLOYEE_DEPARTMENT_ID = "employee_department_id";
  public static final String DEPARTMENT_NAME = "department_name";
  public static final String EMPLOYEE_FULL_NAME = "employee_full_name";
  public static final String EMPLOYEE_DATE_OF_BIRTHDAY = "employee_date_of_birthday";
  public static final String EMPLOYEE_SALARY = "employee_salary";

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
