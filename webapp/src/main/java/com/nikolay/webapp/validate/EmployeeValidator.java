package com.nikolay.webapp.validate;

import com.nikolay.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * The type Employee validator.
 */
@Component
public class EmployeeValidator implements Validator {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  @Override
  public boolean supports(Class<?> empClass) {
    return Employee.class.equals(empClass);
  }

  @Override
  public void validate(Object obj, Errors err) {
    LOGGER.debug("EmployeeValidator: validate()");
    ValidationUtils
        .rejectIfEmptyOrWhitespace(err, "departmentId", "employee.departmentId.empty");
    ValidationUtils.rejectIfEmptyOrWhitespace(err, "fullName", "employee.name.empty");
    ValidationUtils.rejectIfEmptyOrWhitespace(err, "birthday", "employee.birthday.empty");
    ValidationUtils.rejectIfEmptyOrWhitespace(err, "salary", "employee.salary.empty");
  }
}