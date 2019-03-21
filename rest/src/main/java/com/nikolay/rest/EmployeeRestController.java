package com.nikolay.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;
import com.nikolay.service.EmployeeService;

/**
 * The type Employee rest controller.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  private EmployeeService employeeService;

  private Validator employeeValidator;

  /**
   * Instantiates a new Employee rest controller.
   *
   * @param employeeService the employee service
   * @param employeeValidator the employee validator
   */
  @Autowired
  public EmployeeRestController(EmployeeService employeeService, Validator employeeValidator) {
    this.employeeValidator = employeeValidator;
    this.employeeService = employeeService;
  }

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    LOGGER.debug("initBinder()");
    binder.setValidator(employeeValidator);
  }

  /**
   * Gets all employees.
   *
   * @param date the date
   * @param dateFrom the date from
   * @param dateTo the date to
   * @return the all employees
   */
  @GetMapping("/")
  public ResponseEntity<List<ResponseEmployeeDto>> getAllEmployees(
      @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
      @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
      @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {
    LOGGER.debug("getAllEmployees()");
    if (dateFrom != null && dateTo != null) {
      LOGGER.debug("getAllEmployees(dateFrom, dateTo): dateFrom = {}, dateTo = {}",
          dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
          dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      List<ResponseEmployeeDto> employees = employeeService
          .getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
      return new ResponseEntity<>(employees, HttpStatus.FOUND);
    } else if (date != null) {
      LOGGER.debug("getAllEmployees(date): date = {}",
          date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      List<ResponseEmployeeDto> employees = employeeService.getEmployeesByDateOfBirthday(date);
      return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }
    List<ResponseEmployeeDto> employees = employeeService.getAllEmployees();
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResponseEmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
    LOGGER.debug("getEmployeeById(id): id = {}", id);
    ResponseEmployeeDto employee = employeeService.getEmployeeById(id);
    return new ResponseEntity<>(employee, HttpStatus.FOUND);
  }

  /**
   * Add employee response entity.
   *
   * @param employee the employee
   * @return the response entity
   */
  @PostMapping("/")
  public ResponseEntity<Long> addEmployee(@RequestBody @Validated Employee employee) {
    LOGGER.debug("addEmployee(employee): employeeName = {}", employee.getFullName());
    Long id = employeeService.saveEmployee(employee);
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }

  /**
   * Remove employee response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> removeEmployee(@PathVariable("id") Long id) {
    LOGGER.debug("removeEmployee(id): id = {}", id);
    Boolean resultRemoveEmployee = employeeService.deleteEmployee(id);
    return new ResponseEntity<>(resultRemoveEmployee, HttpStatus.OK);
  }

  /**
   * Update employee response entity.
   *
   * @param newEmployee the new employee
   * @param id the id
   * @return the response entity
   */
  @PutMapping("/{id}")
  public ResponseEntity<Boolean> updateEmployee(@RequestBody @Validated Employee newEmployee,
      @PathVariable Long id) {
    LOGGER.debug("updateEmployee(id, employee): id = {}, employeeName = {}", id,
        newEmployee.getFullName());
    Boolean resultUpdateEmployee = employeeService.updateEmployee(newEmployee);
    return new ResponseEntity<>(resultUpdateEmployee, HttpStatus.ACCEPTED);
  }
}
