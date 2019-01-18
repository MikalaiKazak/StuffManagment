package com.nikolay.webapp.controller;

import com.nikolay.model.Department;
import com.nikolay.model.Employee;
import com.nikolay.service.DepartmentService;
import com.nikolay.service.EmployeeService;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The type Employee controller.
 */
@Controller
public class EmployeeController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  private final EmployeeService employeeRestService;

  private final DepartmentService departmentRestService;

  private final Validator employeeValidator;

  /**
   * Instantiates a new Employee controller.
   *
   * @param employeeRestService the employee rest dao
   * @param departmentRestService the department rest dao
   * @param employeeValidator the employee validator
   */
  @Autowired
  public EmployeeController(EmployeeService employeeRestService, DepartmentService departmentRestService,
      Validator employeeValidator) {
    this.employeeRestService = employeeRestService;
    this.departmentRestService = departmentRestService;
    this.employeeValidator = employeeValidator;
  }

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    LOGGER.debug("initBinder()");
    binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      }
    });
    binder.setValidator(employeeValidator);
  }

  /**
   * Gets all employees.
   *
   * @param date the date
   * @param dateFrom the date from
   * @param dateTo the date to
   * @param model the model
   * @return the all employees
   */
  @GetMapping("/employees")
  public String getAllEmployees(
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
      Model model) {
    LOGGER.debug("getAllEmployees()");
    List<Employee> employeeList;
    if (dateFrom != null && dateTo != null) {
      employeeList = employeeRestService.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
    } else if (date != null) {
      employeeList = employeeRestService.getEmployeeByDateOfBirthday(date);
    } else {
      employeeList = employeeRestService.getAllEmployees();
    }
    model.addAttribute("employeeList", employeeList);
    return "employees";
  }

  /**
   * Gets department page.
   *
   * @param id the id
   * @param model the model
   * @return the department page
   */
  @GetMapping("/employee/{id}")
  public String getEmployeePage(@PathVariable("id") Long id, Model model) {
    LOGGER.debug("getEmployeePage() id = {}", id);
    Employee employee = employeeRestService.getEmployeeById(id);
    Department department = departmentRestService.getDepartmentById(employee.getDepartmentId());
    model.addAttribute("employee", employee);
    model.addAttribute("departmentName", department.getDepartmentName());
    return "employee";
  }

  /**
   * Add employee page string.
   *
   * @param model the model
   * @return the string
   */
  @GetMapping("/employee/add")
  public String addEmployeePage(Model model) {
    LOGGER.debug("addEmployeePage()");
    model.addAttribute("employee", new Employee());
    model.addAttribute("departmentList", departmentRestService.getAllDepartments());
    return "addEmployee";
  }

  /**
   * Add employee string.
   *
   * @param employee the employee
   * @param br the br
   * @param redirectAttributes the redirect attributes
   * @param model the model
   * @return the string
   */
  @PostMapping("/employee/add")
  public String addEmployee(@ModelAttribute @Validated Employee employee,
      BindingResult br, RedirectAttributes redirectAttributes, Model model) {
    LOGGER.debug("addEmployee()");
    if (br.hasErrors()) {
      model.addAttribute("departmentList", departmentRestService.getAllDepartments());
      return "addEmployee";
    }
    Long employeeId = employeeRestService.saveEmployee(employee);
    redirectAttributes.addFlashAttribute("message",
        "Employee " + employee.getFullName() + " has been saved");
    return "redirect:/employees";
  }

  /**
   * Edit employee page string.
   *
   * @param id the id
   * @param model the model
   * @return the string
   */
  @GetMapping("/employee/{id}/edit")
  public String editEmployeePage(@PathVariable("id") Long id, Model model) {
    LOGGER.debug("editEmployeePage() id = {}", id);
    Employee employee = employeeRestService.getEmployeeById(id);
    model.addAttribute("employee", employee);
    model.addAttribute("departmentList", departmentRestService.getAllDepartments());
    return "editEmployee";
  }

  /**
   * Edit employee string.
   *
   * @param id the id
   * @param employee the employee
   * @param br the br
   * @param redirectAttributes the redirect attributes
   * @param model the model
   * @return the string
   */
  @PostMapping("/employee/{id}/edit")
  public String editEmployee(@PathVariable("id") Long id,
      @ModelAttribute @Validated Employee employee,
      BindingResult br, RedirectAttributes redirectAttributes, Model model) {
    LOGGER.debug("editEmployee() id = {]", id);
    if (br.hasErrors()) {
      model.addAttribute("departmentList", departmentRestService.getAllDepartments());
      return "editEmployee";
    }
    employeeRestService.updateEmployee(employee);
    redirectAttributes.addFlashAttribute("message",
        "Employee " + employee.getFullName() + " has been updated");
    return "redirect:/employees";
  }

  /**
   * Delete department page string.
   *
   * @param id the id
   * @param redirectAttributes the redirect attributes
   * @return the string
   */
  @GetMapping("/employee/{id}/delete")
  public String deleteDepartmentPage(@PathVariable("id") Long id,
      RedirectAttributes redirectAttributes) {
    LOGGER.debug("deleteDepartmentPage() id = {]", id);
    employeeRestService.deleteEmployee(id);
    redirectAttributes.addFlashAttribute("message", "Employee has been removed");
    return "redirect:/employees";
  }
}