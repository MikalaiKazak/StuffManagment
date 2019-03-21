package com.nikolay.service;

import java.util.List;

import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;
import com.nikolay.service.exception.OperationFailedException;

/**
 * The interface Department service.
 */
public interface DepartmentService {

  /**
   * Gets all departments.
   *
   * @return the all departments
   */
  List<ResponseDepartmentDto> getAllDepartments();

  /**
   * Gets department by id.
   *
   * @param departmentId the department id
   * @return the department by id
   * @throws OperationFailedException the operation failed exception
   */
  ResponseDepartmentDto getDepartmentById(final Long departmentId) throws OperationFailedException;

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
  Boolean updateDepartment(final Department department)
      throws OperationFailedException;

  /**
   * Delete department boolean.
   *
   * @param departmentId the department id
   * @return the boolean
   * @throws OperationFailedException the operation failed exception
   */
  Boolean deleteDepartment(final Long departmentId) throws OperationFailedException;

}
