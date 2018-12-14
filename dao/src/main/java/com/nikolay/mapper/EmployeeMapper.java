package com.nikolay.mapper;

import com.nikolay.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("EMPLOYEE_ID"));
        employee.setDepartmentId(rs.getLong("EMPLOYEE_DEPARTMENT_ID"));
        employee.setFullName(rs.getString("EMPLOYEE_FULL_NAME"));
        employee.setBirthday(rs.getDate("EMPLOYEE_DATE_OF_BIRTHDAY").toLocalDate());
        employee.setSalary(rs.getBigDecimal("EMPLOYEE_SALARY"));
        return employee;
    }
}
