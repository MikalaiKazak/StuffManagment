package com.nikolay.webapp.controller;

import com.nikolay.dao.DepartmentDAO;
import com.nikolay.model.Department;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The type Department controller.
 */
@Controller
public class DepartmentController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  private final DepartmentDAO departmentRestDao;

  private final Validator departmentValidator;

  /**
   * Instantiates a new Department controller.
   *
   * @param departmentRestDao the department rest dao
   * @param departmentValidator the department validator
   */
  @Autowired
  public DepartmentController(DepartmentDAO departmentRestDao,
      Validator departmentValidator) {
    this.departmentRestDao = departmentRestDao;
    this.departmentValidator = departmentValidator;
  }

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    LOGGER.debug("initBinder()");
    binder.setValidator(departmentValidator);
  }

  /**
   * Gets all department.
   *
   * @param model the model
   * @return the all department
   */
  @GetMapping("/departments")
  public String getAllDepartment(Model model) {
    LOGGER.debug("getAllDepartment()");
    List<Department> departments = departmentRestDao.getAllDepartments();
    model.addAttribute("departmentList", departments);
    return "departments";
  }

  /**
   * Add department page string.
   *
   * @param model the model
   * @return the string
   */
  @GetMapping("/department/add")
  public String addDepartmentPage(Model model) {
    LOGGER.debug("addDepartmentPage()");
    model.addAttribute("department", new Department());
    return "addDepartment";
  }


  /**
   * Gets department page.
   *
   * @param id the id
   * @param model the model
   * @return the department page
   */
  @GetMapping("/department/{id}")
  public String getDepartmentPage(@PathVariable("id") Long id, Model model) {
    LOGGER.debug("getDepartmentPage() id = {}", id);
    Department department = departmentRestDao.getDepartmentById(id);
    model.addAttribute("department", department);
    return "department";
  }

  /**
   * Add department string.
   *
   * @param department the department
   * @param br the br
   * @param redirectAttributes the redirect attributes
   * @return the string
   */
  @PostMapping("/department/add")
  public String addDepartment(@ModelAttribute @Validated Department department,
      BindingResult br, RedirectAttributes redirectAttributes) {
    LOGGER.debug("addDepartment()");
    if (br.hasErrors()) {
      return "addDepartment";
    }
    Long departmentId = departmentRestDao.saveDepartment(department);
    redirectAttributes.addFlashAttribute("message",
        "Department " + department.getDepartmentName() + " has been saved");
    return "redirect:/departments";
  }

  /**
   * Edit department page string.
   *
   * @param id the id
   * @param model the model
   * @return the string
   */
  @GetMapping("/department/{id}/edit")
  public String editDepartmentPage(@PathVariable("id") Long id, Model model) {
    LOGGER.debug("editDepartmentPage() id = {]", id);
    Department department = departmentRestDao.getDepartmentById(id);
    model.addAttribute("department", department);
    return "editDepartment";
  }

  /**
   * Edit department string.
   *
   * @param id the id
   * @param department the department
   * @param br the br
   * @param redirectAttributes the redirect attributes
   * @return the string
   */
  @PostMapping("/department/{id}/edit")
  public String editDepartment(@PathVariable("id") Long id,
      @ModelAttribute @Validated Department department,
      BindingResult br, RedirectAttributes redirectAttributes) {
    LOGGER.debug("editDepartment() id = {}", id);
    if (br.hasErrors()) {
      return "editDepartment";
    }
    departmentRestDao.updateDepartment(department);
    redirectAttributes.addFlashAttribute("message",
        "Department " + department.getDepartmentName() + " has been updated");
    return "redirect:/departments";
  }

  /**
   * Delete department page string.
   *
   * @param id the id
   * @param redirectAttributes the redirect attributes
   * @return the string
   */
  @GetMapping("/department/{id}/delete")
  public String deleteDepartmentPage(@PathVariable("id") Long id,
      RedirectAttributes redirectAttributes) {
    LOGGER.debug("deleteDepartmentPage() id = {}", id);
    departmentRestDao.deleteDepartment(id);
    redirectAttributes.addFlashAttribute("message", "Department has been removed");
    return "redirect:/departments";
  }
}