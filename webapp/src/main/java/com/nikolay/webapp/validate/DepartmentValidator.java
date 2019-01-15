package com.nikolay.webapp.validate;

import com.nikolay.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * The type Department validator.
 */
@Component
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
  public void validate(Object o, Errors errors) {
    LOGGER.debug("DepartmentValidator: validate()");
    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "departmentName", "department.name.empty");
  }
}