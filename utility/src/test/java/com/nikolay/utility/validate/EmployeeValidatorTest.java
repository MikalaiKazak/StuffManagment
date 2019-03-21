package com.nikolay.utility.validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.nikolay.model.Employee;

public class EmployeeValidatorTest {

  private EmployeeValidator validator;

  public EmployeeValidatorTest() {
    validator = new EmployeeValidator();
  }

  @Test
  public void supports() {
    assertTrue(validator.supports(Employee.class));
    assertFalse(validator.supports(Object.class));
  }

  @Test
  public void validate_EmptyDepartmentId_success() {
    Employee employee = createEmployee();
    employee.setDepartmentId(null);
    BindException errors = new BindException(employee, "departmentId");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("departmentId"));
    assertEquals("employee.departmentId.empty", errors.getFieldError("departmentId").getCode());
  }

  @Test
  public void validate_EmptyFullName_success() {
    Employee employee = createEmployee();
    employee.setFullName(null);
    BindException errors = new BindException(employee, "fullName");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("fullName"));
    assertEquals("employee.name.empty", errors.getFieldError("fullName").getCode());
  }


  @Test
  public void validate_FullNameMore100_success() {
    Employee employee = createEmployee();
    employee.setFullName(String.join("", Collections.nCopies(101, "d")));
    BindException errors = new BindException(employee, "fullName");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("fullName"));
    assertEquals("employee.name.limitLength", errors.getFieldError("fullName").getCode());
  }

  @Test
  public void validate_EmptyBirthday_success() {
    Employee employee = createEmployee();
    employee.setBirthday(null);
    BindException errors = new BindException(employee, "birthday");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("birthday"));
    assertEquals("employee.birthday.empty", errors.getFieldError("birthday").getCode());
  }

  @Test
  public void validate_IncorrectBirthday_success() {
    Employee employee = createEmployee();
    employee.setBirthday(LocalDate.now());
    BindException errors = new BindException(employee, "birthday");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("birthday"));
    assertEquals("employee.birthday.limitDate", errors.getFieldError("birthday").getCode());
  }

  @Test
  public void validate_EmptySalary_success() {
    Employee employee = createEmployee();
    employee.setSalary(null);
    BindException errors = new BindException(employee, "salary");
    ValidationUtils.invokeValidator(validator, employee, errors);
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getFieldErrorCount("salary"));
    assertEquals("employee.salary.empty", errors.getFieldError("salary").getCode());
  }

  private Employee createEmployee() {
    return new Employee(
        1L,
        1L,
        "jack",
        LocalDate.of(1999, 2, 2),
        BigDecimal.ONE);
  }
}
