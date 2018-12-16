package com.nikolay.impl;

import com.nikolay.Department;
import com.nikolay.DepartmentDAO;
import com.nikolay.DepartmentService;
import com.nikolay.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
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
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        return departmentDAO.getDepartmentById(departmentId);
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws DepartmentNotFoundException {
        if (departmentName == null) {
            throw new DepartmentNotFoundException("Department name shouldn't be null");
        }
        return departmentDAO.getDepartmentByName(departmentName);
    }

    @Override
    public Long saveDepartment(Department department) throws DepartmentNotFoundException {
        if (department.getId() != null) {
            throw new DepartmentNotFoundException(("Department identifier should be null"));
        }
        if (department.getDepartmentName() == null) {
            throw new DepartmentNotFoundException("Department name shouldn't be null");
        }
        if (department.getAverageSalary() == null) {
            throw new DepartmentNotFoundException("Department average salary shouldn't be null");
        }
        if (departmentDAO.getDepartmentByName(department.getDepartmentName()) != null) {
            throw new DepartmentNotFoundException("Department " + department.getDepartmentName() + " is exists");
        }
        return departmentDAO.saveDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) throws DepartmentNotFoundException {
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
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        departmentDAO.deleteDepartment(departmentId);
    }

    @Override
    public BigDecimal getDepartmentAverageSalary(Long departmentId) throws DepartmentNotFoundException {
        if (departmentId == null) {
            throw new DepartmentNotFoundException("Department identifier shouldn't be null");
        }
        return departmentDAO.getDepartmentAverageSalary(departmentId);
    }

}
