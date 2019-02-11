package com.nikolay.service;

import com.nikolay.model.Department;
import com.nikolay.service.exception.OperationFailedException;
import java.util.List;

/**
 * The interface Department service.
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
   * @throws OperationFailedException the operation failed exception
   */
  Department getDepartmentById(final Long departmentId) throws OperationFailedException;

  /**
   * Gets department by name.
   *
   * @param departmentName the department name
   * @return the department by name
   * @throws OperationFailedException the operation failed exception
   */
  Department getDepartmentByName(final String departmentName) throws OperationFailedException;

  /**
   * Save department long.
   *
   * @param department the department
   * @return the long
   * @throws OperationFailedException the operation failed exception
   */
  Long saveDepartment(final Department department) throws OperationFailedException;

  /**
   * Update department boolean.
   *
   * @param department the department
   * @return the boolean
   * @throws OperationFailedException the operation failed exception
   */
  Boolean updateDepartment(final Department department) throws OperationFailedException;

  /**
   * Delete department boolean.
   *
   * @param departmentId the department id
   * @return the boolean
   * @throws OperationFailedException the operation failed exception
   */
  Boolean deleteDepartment(final Long departmentId) throws OperationFailedException;

}
