package com.nikolay.dao;

import com.nikolay.model.Department;
import java.util.List;

/**
 * The interface Department dao.
 */
public interface DepartmentDao {

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
   */
  Department getDepartmentById(final Long departmentId);

  /**
   * Gets department by name.
   *
   * @param departmentName the department name
   * @return the department by name
   */
  Department getDepartmentByName(final String departmentName);

  /**
   * Save department long.
   *
   * @param department the department
   * @return the long
   */
  Long saveDepartment(final Department department);

  /**
   * Update department boolean.
   *
   * @param department the department
   * @return the boolean
   */
  Boolean updateDepartment(final Department department);

  /**
   * Delete department boolean.
   *
   * @param departmentId the department id
   * @return the boolean
   */
  Boolean deleteDepartment(final Long departmentId);
}
