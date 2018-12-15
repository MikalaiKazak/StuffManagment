package com.nikolay.impl;

import com.nikolay.Department;
import com.nikolay.DepartmentDAO;
import com.nikolay.DepartmentService;
import com.nikolay.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentDAO departmentDAO;

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }

    @Override
    public Department getDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier not found");
        }
        return departmentDAO.getDepartmentById(departmentId);
    }

    @Override
    public Long saveDepartment(Department department) throws DepartmentNotFoundException {
        if (department == null) {
            throw new DepartmentNotFoundException("Department not found");
        }
        if (departmentDAO.getDepartmentById(department.getId()) != null) {
            throw new DepartmentNotFoundException("Department " + department.getDepartmentName() + " is exists");
        }
        return departmentDAO.saveDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) throws DepartmentNotFoundException {
        if (department == null || departmentDAO.getDepartmentById(department.getId()) == null) {
            throw new DepartmentNotFoundException("Department not found");
        }
        departmentDAO.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) throws DepartmentNotFoundException {
        if (departmentId == null || departmentDAO.getDepartmentById(departmentId) == null) {
            throw new DepartmentNotFoundException("Department not found");
        }
        departmentDAO.deleteDepartment(departmentId);
    }

    @Override
    public BigDecimal getDepartmentAverageSalary(Long departmentId) throws DepartmentNotFoundException {
        if (departmentId == null || departmentDAO.getDepartmentById(departmentId) == null) {
            throw new DepartmentNotFoundException("Department not found");
        }
        return departmentDAO.getDepartmentAverageSalary(departmentId);
    }
}
