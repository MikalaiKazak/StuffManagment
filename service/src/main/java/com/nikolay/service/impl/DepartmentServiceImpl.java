package com.nikolay.service.impl;

import com.nikolay.dao.DepartmentDAO;
import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import com.nikolay.service.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    public static final Logger LOGGER = LogManager.getLogger();

    private DepartmentDAO departmentDAO;

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        LOGGER.debug("getAllDepartments()");
        this.departmentDAO = departmentDAO;
    }

    @Override
    public List<Department> getAllDepartments() {
        LOGGER.debug("getAllDepartments()");
        return departmentDAO.getAllDepartments();
    }

    @Override
    public Department getDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        LOGGER.debug("getDepartmentById(id): id = {}", departmentId);
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        return departmentDAO.getDepartmentById(departmentId);
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws DepartmentNotFoundException {
        LOGGER.debug("getDepartmentByName(departmentName): departmentName = {}", departmentName);
        if (departmentName == null) {
            throw new DepartmentNotFoundException("Department name shouldn't be null");
        }
        return departmentDAO.getDepartmentByName(departmentName);
    }

    @Override
    public Long saveDepartment(Department department) throws DepartmentNotFoundException {
        LOGGER.debug("saveDepartment(department): departmentName = {}", department.getDepartmentName());
        if (department.getId() != null) {
            throw new DepartmentNotFoundException(("Department identifier should be null"));
        }
        if (department.getDepartmentName() == null) {
            throw new DepartmentNotFoundException("Department name shouldn't be null");
        }
        if (department.getAverageSalary() == null) {
            throw new DepartmentNotFoundException("Department average salary shouldn't be null");
        }
        return departmentDAO.saveDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) throws DepartmentNotFoundException {
        LOGGER.debug("updateDepartment(department): departmentId = {}", department.getId());
        if (department.getId() == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        if (department.getDepartmentName() == null) {
            throw new DepartmentNotFoundException("Department name shouldn't be null");
        }
        if (department.getAverageSalary() == null) {
            throw new DepartmentNotFoundException("Department average salary shouldn't be null");
        }
        departmentDAO.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) throws DepartmentNotFoundException {
        LOGGER.debug("deleteDepartment(id) id = {}", departmentId);
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        departmentDAO.deleteDepartment(departmentId);
    }

    @Override
    public BigDecimal getDepartmentAverageSalary(Long departmentId) throws DepartmentNotFoundException {
        LOGGER.debug("getDepartmentAverageSalary(departmentId) departmentId = {}", departmentId);
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        return departmentDAO.getDepartmentAverageSalary(departmentId);
    }

}
