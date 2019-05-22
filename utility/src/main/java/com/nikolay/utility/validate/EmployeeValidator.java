package com.nikolay.utility.validate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nikolay.model.Employee;

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

    Employee employee = (Employee) obj;

    LocalDate dateOfBirthday = employee.getBirthday();
    LocalDate today = LocalDate.now();

    try {
      Period period = Period.between(dateOfBirthday, today);

      if (period.isNegative() || period.getYears() < 14 || period.getYears() > 100) {
        err.rejectValue("birthday", "employee.birthday.limitDate");
      }

      if (employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
        err.rejectValue("salary", "employee.salary.negativeValue");
      }

      if (employee.getFullName().length() > 100) {
        err.rejectValue("fullName", "employee.name.limitLength");
      }
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage());
    }

  }

}