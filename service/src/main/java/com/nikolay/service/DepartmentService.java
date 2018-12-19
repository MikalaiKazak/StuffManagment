package com.nikolay.service;

import com.nikolay.service.exception.DepartmentNotFoundException;
import com.nikolay.model.Department;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Department service.
 *
 * @author Mikalai_Kazak 14.12.2012
 */
public interface DepartmentService {


    /**
     * Gets all departments.
     *
     * @return the all departments
     */
    List<Department> getAllDepartments();

    /**
     * Gets department by id.
     *
     * @param departmentId the department id
     * @return the department by id
     * @throws DepartmentNotFoundException the department not found exception
     */
    Department getDepartmentById(Long departmentId) throws DepartmentNotFoundException;

    /**
     * Gets department by name.
     *
     * @param departmentName the department name
     * @return the department by name
     * @throws DepartmentNotFoundException the department not found exception
     */
    Department getDepartmentByName(String departmentName) throws DepartmentNotFoundException;

    /**
     * Save department long.
     *
     * @param department the department
     * @return the long
     * @throws DepartmentNotFoundException the department not found exception
     */
    Long saveDepartment(Department department) throws DepartmentNotFoundException;

    /**
     * Update department.
     *
     * @param department the department
     * @throws DepartmentNotFoundException the department not found exception
     */
    void updateDepartment(Department department) throws DepartmentNotFoundException;

    /**
     * Delete department.
     *
     * @param departmentId the department id
     * @throws DepartmentNotFoundException the department not found exception
     */
    void deleteDepartment(Long departmentId) throws DepartmentNotFoundException;

    /**
     * Gets department average salary.
     *
     * @param departmentId the department id
     * @return the department average salary
     * @throws DepartmentNotFoundException the department not found exception
     */
    BigDecimal getDepartmentAverageSalary(Long departmentId) throws DepartmentNotFoundException;
}
