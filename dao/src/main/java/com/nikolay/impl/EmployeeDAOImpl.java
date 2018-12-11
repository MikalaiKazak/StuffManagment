package com.nikolay.impl;

import com.nikolay.Employee;
import com.nikolay.EmployeeDAO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public Employee getEmployeeById(Long id) {
        return null;
    }

    @Override
    public Long saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public Long updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public Long deleteEmployee(Long employeeId) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByDepartmentName(String departmentName) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        return null;
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirtday(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
