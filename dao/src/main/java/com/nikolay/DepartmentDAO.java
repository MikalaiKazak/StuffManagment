package com.nikolay;

import java.util.List;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 *
 * Defines DAO operations for the department model.
 */
public interface DepartmentDAO {

    /**
     *  Get the list of all departments
     *
     * @return the list of all departments
     */
    List<Department> getAllDepartments();

    /**
     * Get department by identifier
     *
     * @param departmentId the department identifier that you want get
     * @return the department
     */
    Department getDepartmentById(Long departmentId);

    /**
     * Save department
     *
     * @param department the department that oyu want save
     * @return the identifier of the department created
     */
    Long saveDepartment(Department department);

    /**
     * Update department
     *
     * @param department the department that you want edit
     * @return the identifier of the department updated
     */
    Long updateDepartment(Department department);

    /**
     * Delete department
     *
     * @param departmentId   the department identifier
     */
    void deleteDepartment(Long departmentId);

}
