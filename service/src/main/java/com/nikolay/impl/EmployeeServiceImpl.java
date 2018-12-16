package com.nikolay.impl;

import com.nikolay.Employee;
import com.nikolay.EmployeeDAO;
import com.nikolay.EmployeeService;
import com.nikolay.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId == null) {
            throw new EmployeeNotFoundException("Employee identifier shouldn't be null");
        }
        return employeeDAO.getEmployeeById(employeeId);
    }

    @Override
    public Long saveEmployee(Employee employee) throws EmployeeNotFoundException {
        if (employee.getDepartmentId() != null) {
            throw new EmployeeNotFoundException("Department identifier should be null");
        }
        if (employee.getFullName() == null) {
            throw new EmployeeNotFoundException("Employee full name shouldn't be null");
        }
        if (employee.getBirthday() == null) {
            throw new EmployeeNotFoundException("Employee date of birthday shouldn't be null");
        }
        if (employee.getSalary() == null) {
            throw new EmployeeNotFoundException("Employee salary shouldn't be null");
        }
        return employeeDAO.saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (employee.getId() == null) {
            throw new EmployeeNotFoundException("Employee identifier shouldn't be null");
        }
        if (employee.getDepartmentId() == null) {
            throw new EmployeeNotFoundException("Department identifier shouldn't be null");
        }
        if (employee.getFullName() == null) {
            throw new EmployeeNotFoundException("Employee full name shouldn't be null");
        }
        if (employee.getBirthday() == null) {
            throw new EmployeeNotFoundException("Employee date of birthday shouldn't be null");
        }
        if (employee.getSalary() == null) {
            throw new EmployeeNotFoundException("Employee salary shouldn't be null");
        }
        employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId == null) {
            throw new EmployeeNotFoundException("Employee identifier shouldn't be null");
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
