package com.nikolay.dao.mapper;

import com.nikolay.model.Employee;
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

        employee.setSalary(rs.getBigDecimal("EMPLOYEE_SALARY").stripTrailingZeros());
        return employee;
    }
}
