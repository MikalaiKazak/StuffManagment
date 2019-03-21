package com.nikolay.dao;

import java.util.List;

import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;

/**
 * The interface Department dao.
 */
public interface DepartmentDao {

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
   */
  ResponseDepartmentDto getDepartmentById(final Long departmentId);

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
