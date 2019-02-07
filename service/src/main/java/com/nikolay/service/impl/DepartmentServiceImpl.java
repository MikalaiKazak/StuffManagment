package com.nikolay.service.impl;

import com.nikolay.dao.DepartmentDao;
import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import com.nikolay.service.exception.OperationFailedException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Department service.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  @Value("${departmentService.incorrectId}")
  private String incorrectId;

  @Value("${departmentService.incorrectDepartment}")
  private String incorrectDepartment;

  @Value("${departmentService.incorrectDepartmentName}")
  private String incorrectDepartmentName;

  @Value("${departmentService.notUpdated}")
  private String departmentNotUpdated;

  @Value("${departmentService.notDeleted}")
  private String departmentNotDeleted;

  @Value("${departmentService.notSaved}")
  private String departmentNotSave;

  private DepartmentDao departmentDao;

  /**
   * Sets department dao.
   *
   * @param departmentDao the department dao
   */
  public void setDepartmentDao(DepartmentDao departmentDao) {
    LOGGER.debug("getAllDepartments()");
    this.departmentDao = departmentDao;
  }

  @Override
  public List<Department> getAllDepartments() {
    LOGGER.debug("getAllDepartments()");
    return departmentDao.getAllDepartments();
  }

  @Override
  public Department getDepartmentById(Long departmentId) {
    LOGGER.debug("getDepartmentById(id): id = {}", departmentId);
    if (departmentId == null || departmentId < 0) {
      throw new OperationFailedException(incorrectId);
    }
    return departmentDao.getDepartmentById(departmentId);
  }

  @Override
  public Department getDepartmentByName(String departmentName) {
    LOGGER.debug("getDepartmentByName(departmentName): departmentName = {}", departmentName);
    if (departmentName == null) {
      throw new OperationFailedException(incorrectDepartmentName);
    }
    return departmentDao.getDepartmentByName(departmentName);
  }

  @Override
  public Long saveDepartment(Department department) {
    LOGGER.debug("saveDepartment(department): departmentName = {}",
        department.getDepartmentName());
    checkDepartment(department);
    if (department.getId() != null) {
      throw new OperationFailedException((incorrectId));
    }
    return departmentDao.saveDepartment(department);
  }

  @Override
  public Boolean updateDepartment(Department department) {
    LOGGER.debug("updateDepartment(department): departmentId = {}", department.getId());
    checkDepartment(department);
    Boolean resultUpdateDepartment = departmentDao.updateDepartment(department);
    if (!resultUpdateDepartment) {
      throw new OperationFailedException(departmentNotUpdated);
    }
    return resultUpdateDepartment;
  }

  @Override
  public Boolean deleteDepartment(Long departmentId) {
    LOGGER.debug("deleteDepartment(id) id = {}", departmentId);
    Boolean resultDeleteDepartment = departmentDao.deleteDepartment(departmentId);
    if (!resultDeleteDepartment) {
      throw new OperationFailedException(departmentNotDeleted);
    }
    return resultDeleteDepartment;
  }

  private void checkDepartment(Department department) throws OperationFailedException {
    if (department == null) {
      throw new OperationFailedException(incorrectDepartment);
    }
    if (department.getDepartmentName() == null) {
      throw new OperationFailedException(incorrectDepartmentName);
    }
  }
}
