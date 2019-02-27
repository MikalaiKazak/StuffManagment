package com.nikolay.utility.validate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nikolay.model.Department;

/**
 * The type Department validator.
 */
public class DepartmentValidator implements Validator {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  @Override
  public boolean supports(Class<?> depClass) {
    return Department.class.equals(depClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    LOGGER.debug("DepartmentValidator: validate()");
    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "departmentName", "department.name.empty");

    Department department = (Department) object;
    if (department.getDepartmentName() != null && department.getDepartmentName().length() > 100) {
      errors.rejectValue("departmentName", "department.name.limitLength");
    }
  }
}