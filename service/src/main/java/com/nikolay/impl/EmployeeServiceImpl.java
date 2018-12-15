package com.nikolay.impl;

import com.nikolay.Employee;
import com.nikolay.EmployeeDAO;
import com.nikolay.EmployeeService;
import com.nikolay.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId == null) {
            throw new EmployeeNotFoundException("Employee identifier not found");
        }
        return employeeDAO.getEmployeeById(employeeId);
    }

    @Override
    public Long saveEmployee(Employee employee) throws EmployeeNotFoundException {
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee identifier not found");
        }
        if (employeeDAO.getEmployeeById(employee.getId()) != null) {
            throw new EmployeeNotFoundException("Employee " + employee.getFullName() + " is exists");
        }
        return employeeDAO.saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (employee == null || employeeDAO.getEmployeeById(employee.getDepartmentId()) == null) {
            throw new EmployeeNotFoundException("Employee identifier not found");
        }
        employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId == null || employeeDAO.getEmployeeById(employeeId) == null) {
            throw new EmployeeNotFoundException("Employee identifier not found");
        }
        employeeDAO.deleteEmployee(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        return employeeDAO.getEmployeeByDateOfBirthday(date);
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
        return employeeDAO.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
    }


}
