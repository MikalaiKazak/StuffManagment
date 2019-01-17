package com.nikolay.service.impl;

import com.nikolay.dao.EmployeeDAO;
import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;
import com.nikolay.service.exception.OperationFailedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * The type Employee service.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    private EmployeeDAO employeeDAO;

    /**
     * Sets employee dao.
     *
     * @param employeeDAO the employee dao
     */
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        LOGGER.debug("setEmployeeDAO");
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        LOGGER.debug("getEmployeeById(employeeId): employeeId = {}", employeeId);
        if (employeeId == null || employeeId < 0) {
            throw new OperationFailedException("Employee identifier shouldn't be null");
        }
        return employeeDAO.getEmployeeById(employeeId);
    }

    @Override
    public Long saveEmployee(Employee employee) {
        LOGGER.debug("saveEmployee(employee): employeeName = {}", employee.getFullName());
        checkEmployee(employee);
        if (employee.getId() != null) {
            throw new OperationFailedException("Employee identifier should be null");
        }
        return employeeDAO.saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        LOGGER.debug("updateEmployee(employee): employeeId = {}", employee.getId());
        checkEmployee(employee);
        if (employee.getId() == null) {
            throw new OperationFailedException("Employee identifier shouldn't be null");
        }
        employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        LOGGER.debug("deleteEmployee(employeeId): employeeId = {}", employeeId);
        if (employeeId == null || employeeId < 0) {
            throw new OperationFailedException("Employee identifier shouldn't be null");
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
        LOGGER.debug(
                "getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
                dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return employeeDAO.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
    }

    /**
     * Check object employee for null
     */
    private void checkEmployee(Employee employee) throws OperationFailedException {
        if(employee == null) {
            throw new OperationFailedException("Operation fails");
        }
        if (employee.getDepartmentId() == null) {
            throw new OperationFailedException("Employee identifier shouldn't be null");
        }
        if (employee.getFullName() == null) {
            throw new OperationFailedException("Employee full name shouldn't be null");
        }
        if (employee.getBirthday() == null) {
            throw new OperationFailedException("Employee date of birthday shouldn't be null");
        }
        if (employee.getSalary() == null) {
            throw new OperationFailedException("Employee salary shouldn't be null");
        }
    }
}
