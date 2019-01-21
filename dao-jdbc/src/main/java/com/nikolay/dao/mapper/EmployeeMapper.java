package com.nikolay.dao.mapper;

import com.nikolay.model.Employee;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * The type Employee mapper.
 */
public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("EMPLOYEE_ID"));
        employee.setDepartmentId(rs.getLong("EMPLOYEE_DEPARTMENT_ID"));
        employee.setFullName(rs.getString("EMPLOYEE_FULL_NAME"));
        employee.setFullName(rs.getString("EMPLOYEE_FULL_NAME"));

        Date date = rs.getDate("EMPLOYEE_DATE_OF_BIRTHDAY");
        if (date != null) {
            employee.setBirthday(date.toLocalDate());
        }
        BigDecimal bigDecimal = new BigDecimal(
            rs.getBigDecimal("EMPLOYEE_SALARY").stripTrailingZeros().toPlainString());
        employee.setSalary(bigDecimal);
        return employee;
    }
}
