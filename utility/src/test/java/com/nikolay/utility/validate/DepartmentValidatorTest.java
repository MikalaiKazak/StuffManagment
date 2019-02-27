package com.nikolay.utility.validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.nikolay.model.Department;

public class DepartmentValidatorTest {

  private DepartmentValidator validator;

  public DepartmentValidatorTest() {
    validator = new DepartmentValidator();
  }

  @Test
  public void supports() {
    assertTrue(validator.supports(Department.class));
    assertFalse(validator.supports(Object.class));
  }

  @Test
  public void validate_EmptyDepartmentName_success() {
    Department department = new Department();
    department.setDepartmentName(null);
    BindException errors = new BindException(department, "departmentName");
    ValidationUtils.invokeValidator(validator, department, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("departmentName"));
    assertEquals("department.name.empty", errors.getFieldError("departmentName").getCode());
  }

  @Test
  public void validate_CorrectDepartment_success() {
    Department department = new Department();
    department.setDepartmentName("JAVA");
    BindException errors = new BindException(department, "departmentName");
    ValidationUtils.invokeValidator(validator, department, errors);
    assertFalse(errors.hasErrors());
    assertEquals(0, errors.getFieldErrorCount("departmentName"));
  }

  @Test
  public void validate_DepartmentNameMore100_success() {
    Department department = new Department();
    department.setDepartmentName(String.join("", Collections.nCopies(101, "d")));
    BindException errors = new BindException(department, "departmentName");
    ValidationUtils.invokeValidator(validator, department, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("departmentName"));
    assertEquals("department.name.limitLength", errors.getFieldError("departmentName").getCode());
  }

}
