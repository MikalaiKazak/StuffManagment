package com.nikolay.dao.mapper;

import com.nikolay.model.Department;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * The type Department mapper.
 */
public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("DEPARTMENT_ID"));
        department.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
        BigDecimal avgSalary = rs.getBigDecimal("AVG_SALARY");
        if(avgSalary == null) {
            department.setAverageSalary(BigDecimal.ZERO);
        } else {
            department
                .setAverageSalary(new BigDecimal(avgSalary.stripTrailingZeros().toPlainString()));
        }
        return department;
    }

}
