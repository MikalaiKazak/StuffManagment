package com.nikolay.webapp.controller;

import com.nikolay.dao.DepartmentDAO;
import com.nikolay.dao.EmployeeDAO;
import com.nikolay.model.Employee;
import java.time.LocalDate;
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

  private final EmployeeDAO employeeRestDao;

  private final DepartmentDAO departmentRestDao;

  private final Validator employeeValidator;

  /**
   * Instantiates a new Employee controller.
   *
   * @param employeeRestDao the employee rest dao
   * @param departmentRestDao the department rest dao
   * @param employeeValidator the employee validator
   */
  @Autowired
  public EmployeeController(EmployeeDAO employeeRestDao, DepartmentDAO departmentRestDao,
      Validator employeeValidator) {
    this.employeeRestDao = employeeRestDao;
    this.departmentRestDao = departmentRestDao;
    this.employeeValidator = employeeValidator;
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
      employeeList = employeeRestDao.getEmployeeBetweenDatesOfBirthday(dateFrom, dateTo);
    } else if (date != null) {
      employeeList = employeeRestDao.getEmployeeByDateOfBirthday(date);
    } else {
      employeeList = employeeRestDao.getAllEmployees();
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
  public String getDepartmentPage(@PathVariable("id") Long id, Model model) {
    LOGGER.debug("getDepartmentPage() id = {}", id);
    Employee employee = employeeRestDao.getEmployeeById(id);
    model.addAttribute("employee", employee);
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
    model.addAttribute("departmentList", departmentRestDao.getAllDepartments());
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
      model.addAttribute("departmentList", departmentRestDao.getAllDepartments());
      return "addEmployee";
    }
    Long employeeId = employeeRestDao.saveEmployee(employee);
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
    Employee employee = employeeRestDao.getEmployeeById(id);
    model.addAttribute("employee", employee);
    model.addAttribute("departmentList", departmentRestDao.getAllDepartments());
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
      model.addAttribute("departmentList", departmentRestDao.getAllDepartments());
      return "editEmployee";
    }
    employeeRestDao.updateEmployee(employee);
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
    employeeRestDao.deleteEmployee(id);
    redirectAttributes.addFlashAttribute("message", "Employee has been removed");
    return "redirect:/employees";
  }
}