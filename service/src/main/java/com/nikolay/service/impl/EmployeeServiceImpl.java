package com.nikolay.service.impl;

import com.nikolay.model.Employee;
import com.nikolay.dao.EmployeeDAO;
import com.nikolay.service.EmployeeService;
import com.nikolay.service.exception.EmployeeNotFoundException;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public static final Logger LOGGER = LogManager.getLogger();

    private EmployeeDAO employeeDAO;

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        LOGGER.debug("setEmployeeDAO");
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
        if (employeeId == null) {
            throw new EmployeeNotFoundException("Employee identifier shouldn't be null");
        }
        return employeeDAO.getEmployeeById(employeeId);
    }

    @Override
    public Long saveEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
        if (employee.getId() != null) {
            throw new EmployeeNotFoundException("Employee identifier should be null");
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
        return employeeDAO.saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
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
        LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
        if (employeeId == null) {
            throw new EmployeeNotFoundException("Employee identifier shouldn't be null");
        }
        employeeDAO.deleteEmployee(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.debug("getAllEmployees()");
        return employeeDAO.getAllEmployees();
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        LOGGER.debug("getEmployeeByDateOfBirthday(date): date = {}",
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return employeeDAO.getEmployeeByDateOfBirthday(date);
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate dateFrom, LocalDate dateTo) {
        LOGGER.debug("getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
            dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return employeeDAO.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
    }
}
