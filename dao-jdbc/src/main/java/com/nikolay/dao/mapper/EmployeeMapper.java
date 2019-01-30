package com.nikolay.dao.mapper;

import com.nikolay.model.Employee;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * The type Employee mapper.
 */
public class EmployeeMapper implements RowMapper<Employee> {

  private static final String EMPLOYEE_ID = "EMPLOYEE_ID";

  private static final String EMPLOYEE_DEPARTMENT_ID = "EMPLOYEE_DEPARTMENT_ID";

  private static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";

  private static final String EMPLOYEE_FULL_NAME = "EMPLOYEE_FULL_NAME";

  private static final String EMPLOYEE_DATE_OF_BIRTHDAY = "EMPLOYEE_DATE_OF_BIRTHDAY";

  private static final String EMPLOYEE_SALARY = "EMPLOYEE_SALARY";

  @Override
  public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
    Employee employee = new Employee();
    employee.setId(rs.getLong(EMPLOYEE_ID));
    employee.setDepartmentId(rs.getLong(EMPLOYEE_DEPARTMENT_ID));
    employee.setDepartmentName(rs.getString(DEPARTMENT_NAME));
    employee.setFullName(rs.getString(EMPLOYEE_FULL_NAME));
    Date date = rs.getDate(EMPLOYEE_DATE_OF_BIRTHDAY);
    if (date != null) {
      employee.setBirthday(date.toLocalDate());
    }
    BigDecimal bigDecimal = new BigDecimal(
        rs.getBigDecimal(EMPLOYEE_SALARY).stripTrailingZeros().toPlainString());
    employee.setSalary(bigDecimal);
    return employee;
  }
}
