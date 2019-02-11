package com.nikolay.rest;

import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Department rest controller.
 */
@RestController
@RequestMapping("/department")
public class DepartmentRestController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();
  private final Validator departmentValidator;
  private DepartmentService departmentService;

  /**
   * Instantiates a new Department rest controller.
   *
   * @param departmentService the department service
   * @param departmentValidator the department validator
   */
  @Autowired
  public DepartmentRestController(DepartmentService departmentService,
      Validator departmentValidator) {
    this.departmentService = departmentService;
    this.departmentValidator = departmentValidator;
  }

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    LOGGER.debug("initBinder()");
    binder.setValidator(departmentValidator);
  }

  /**
   * Gets all departments.
   *
   * @return the all departments
   */
  @GetMapping("/")
  public ResponseEntity<List<Department>> getAllDepartments() {
    LOGGER.debug("getAllDepartments()");
    List<Department> departmentList = departmentService.getAllDepartments();
    return new ResponseEntity<>(departmentList, HttpStatus.OK);
  }

  /**
   * Gets department by id.
   *
   * @param id the id
   * @return the department by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
    LOGGER.debug("getDepartmentById(): id = {}", id);
    Department department = departmentService.getDepartmentById(id);
    return new ResponseEntity<>(department, HttpStatus.FOUND);
  }

  /**
   * Add department response entity.
   *
   * @param department the department
   * @return the response entity
   */
  @PostMapping("/")
  public ResponseEntity<Long> addDepartment(@RequestBody @Validated Department department) {
    LOGGER.debug("addDepartment(): departmentName = {}", department.getDepartmentName());
    Long id = departmentService.saveDepartment(department);
    department.setId(id);
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }

  /**
   * Remove department response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> removeDepartment(@PathVariable("id") Long id) {
    LOGGER.debug("removeDepartment(): id = {}", id);
    Boolean resultRemoveDepartment = departmentService.deleteDepartment(id);
    return new ResponseEntity<>(resultRemoveDepartment, HttpStatus.OK);
  }

  /**
   * Update department response entity.
   *
   * @param id the id
   * @param newDepartment the new department
   * @return the response entity
   */
  @PutMapping("/{id}")
  public ResponseEntity<Boolean> updateDepartment(@PathVariable Long id,
      @RequestBody @Validated Department newDepartment) {
    LOGGER.debug("updateDepartment(): id = {}, newDepartmentName = {}", id,
        newDepartment.getDepartmentName());
    Department department = departmentService.getDepartmentById(id);
    department.setDepartmentName(newDepartment.getDepartmentName());
    department.setAverageSalary(newDepartment.getAverageSalary());
    Boolean resultUpdateDepartment = departmentService.updateDepartment(department);
    return new ResponseEntity<>(resultUpdateDepartment, HttpStatus.ACCEPTED);
  }

}
