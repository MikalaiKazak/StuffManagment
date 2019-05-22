package com.nikolay.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nikolay.dao.DepartmentDao;
import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;
import com.nikolay.service.DepartmentService;
import com.nikolay.service.exception.OperationFailedException;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

  public static final Logger LOGGER = LogManager.getLogger();
  private final DepartmentDao departmentDao;
  @Value("${departmentService.notUpdated}")
  private String departmentNotUpdated;
  @Value("${departmentService.notDeleted}")
  private String departmentNotDeleted;
  @Value("${departmentService.notSaved}")
  private String departmentNotSave;
  @Value("${department.incorrectDepartmentId}")
  private String incorrectDepartmentId;
  @Value("${department.incorrectDepartmentName}")
  private String incorrectDepartmentName;

  public DepartmentServiceImpl(DepartmentDao departmentDao) {
    LOGGER.debug("getAllDepartments()");
    this.departmentDao = departmentDao;
  }

  @Override
  public List<ResponseDepartmentDto> getAllDepartments() {
    LOGGER.debug("getAllDepartments()");
    return departmentDao.getAllDepartments();
  }

  @Override
  public ResponseDepartmentDto getDepartmentById(final Long departmentId) {
    LOGGER.debug("getDepartmentById(id): id = {}", departmentId);
    if (departmentId == null || departmentId < 0) {
      throw new OperationFailedException(incorrectDepartmentId);
    }
    return departmentDao.getDepartmentById(departmentId);
  }

  @Override
  public Long saveDepartment(final Department department) {
    LOGGER.debug("saveDepartment(department): departmentName = {}",
        department.getDepartmentName());
    if (department.getDepartmentName() == null) {
      throw new OperationFailedException(incorrectDepartmentName);
    }
    return departmentDao.saveDepartment(department);
  }

  @Override
  public Boolean updateDepartment(final Department department) {
    LOGGER.debug("updateDepartment(department)");
    if (department.getId() == null || department.getId() < 0) {
      throw new OperationFailedException(incorrectDepartmentId);
    }
    if (department.getDepartmentName() == null) {
      throw new OperationFailedException(incorrectDepartmentName);
    }
    Boolean resultUpdateDepartment = departmentDao.updateDepartment(department);
    if (!resultUpdateDepartment) {
      throw new OperationFailedException(departmentNotUpdated);
    }
    return resultUpdateDepartment;
  }

  @Override
  public Boolean deleteDepartment(final Long departmentId) {
    LOGGER.debug("deleteDepartment(id) id = {}", departmentId);
    Boolean resultDeleteDepartment = departmentDao.deleteDepartment(departmentId);
    if (departmentId == null || departmentId < 0) {
      throw new OperationFailedException(incorrectDepartmentId);
    }
    if (!resultDeleteDepartment) {
      throw new OperationFailedException(departmentNotDeleted);
    }
    return resultDeleteDepartment;
  }


}
