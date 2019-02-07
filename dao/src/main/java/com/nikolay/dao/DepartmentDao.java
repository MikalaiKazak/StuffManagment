package com.nikolay.dao;

import com.nikolay.model.Department;
import java.util.List;

/**
 * This interface defines the various operations to be performed on a Department object.
 *
 * @author Mikalai Kazak
 * @version 1.0
 * @see Department
 */
public interface DepartmentDao {

  /**
   * Gets all departments.
   *
   * @return the all departments
   */
  List<Department> getAllDepartments();

  /**
   * Gets the department using department identifier.
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
   * Update department.
   *
   * @param department the department
   * @return the boolean
   */
  Boolean updateDepartment(final Department department);

  /**
   * Delete department.
   *
   * @param departmentId the department id
   * @return the boolean
   */
  Boolean deleteDepartment(final Long departmentId);
}
