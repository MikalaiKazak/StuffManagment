package com.nikolay.webapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseDepartmentDto;
import com.nikolay.model.dto.ResponseEmployeeDto;
import com.nikolay.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/mock-test-webapp.xml"})
public class TestEmployeeController {

  public static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  private EmployeeController employeeController;

  @Autowired
  private EmployeeService mockEmployeeService;

  private ResponseDepartmentDto dep1;
  private Employee emp1;
  private ResponseEmployeeDto emp2;
  private List<ResponseEmployeeDto> employees;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    LOGGER.error("execute: beforeTest()");
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setSuffix(".html");
    viewResolver.setPrefix("/WEB-INF/templates/");
    dep1 = new ResponseDepartmentDto(1L, "Services", BigDecimal.valueOf(200));
    emp1 = new Employee(1L, 1L, "Nikolay Kozak", LocalDate.of(1999, 2, 28),
        BigDecimal.valueOf(350));
    emp2 = new ResponseEmployeeDto(2L, 1L, "Services", "Dmitry Kozak", LocalDate.of(2000, 12, 5),
        BigDecimal.valueOf(300));
    employees = Arrays.asList(emp2, emp2);
    mockMvc = MockMvcBuilders.standaloneSetup(new ErrorController(), employeeController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @After
  public void tearDown() {
    reset(mockEmployeeService);
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetEmployeeById() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testGetEmployeeById()");
    when(mockEmployeeService.getEmployeeById(2L)).thenReturn(emp2);
    mockMvc.perform(
        get("/employee/2")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("employee"))
        .andExpect(model().attributeExists("employee"))
        .andExpect(model().attributeExists("departmentName"))
        .andExpect(model().attribute("employee", emp2));
    verify(mockEmployeeService).getEmployeeById(2L);
  }

  @Test
  public void testGetAllEmployees() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testGetAllEmployees()");
    when(mockEmployeeService.getAllEmployees()).thenReturn(employees);
    mockMvc.perform(
        get("/employees/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("employees"))
        .andExpect(model().attributeExists("employeeList"));
    verify(mockEmployeeService).getAllEmployees();
  }

  @Test
  public void testGetAddEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testGetAddEmployee()");
    mockMvc.perform(get("/employee/add")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("addEmployee"))
        .andExpect(model().attributeExists("employee"));
  }

  @Test
  public void testGetUpdateEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testGetUpdateEmployee()");
    when(mockEmployeeService.getEmployeeById(2L)).thenReturn(emp2);
    mockMvc.perform(get("/employee/2/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("editEmployee"))
        .andExpect(model().attributeExists("employee"));
    verify(mockEmployeeService).getEmployeeById(2L);
  }

  @Test
  public void testPostAddEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testPostAddEmployee()");
    when(mockEmployeeService.saveEmployee(any(Employee.class))).thenReturn(1L);
    mockMvc.perform(post("/employee/add")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("departmentId", emp1.getDepartmentId().toString())
        .param("fullName", emp1.getFullName())
        .param("birthday", emp1.getBirthday().toString())
        .param("salary", emp1.getSalary().toString())
        .requestAttr("employee", new Employee()))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/employees"));
    verify(mockEmployeeService).saveEmployee(any(Employee.class));
  }

  @Test
  public void testPostUpdateEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testPostUpdateDepartment()");
    when(mockEmployeeService.updateEmployee(any(Employee.class)))
        .thenReturn(true);
    mockMvc.perform(post("/employee/2/edit")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("departmentId", emp1.getDepartmentId().toString())
        .param("fullName", emp1.getFullName())
        .param("birthday", emp1.getBirthday().toString())
        .param("salary", emp1.getSalary().toString())
        .requestAttr("employee", emp2))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/employees"));
    verify(mockEmployeeService).updateEmployee(any(Employee.class));
  }


  @Test
  public void testRemoveEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeController: run testRemoveEmployee()");
    when(mockEmployeeService.deleteEmployee(1L)).thenReturn(true);
    mockMvc.perform(
        get("/employee/1/delete")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(view().name("redirect:/employees"))
        .andExpect(status().isFound());
    verify(mockEmployeeService).deleteEmployee(1L);
  }

  @Test
  public void testGetEmployeeByDateOfBirthday() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetEmployeeByDateOfBirthday()");
    LocalDate date = LocalDate.of(1999, 2, 28);
    when(mockEmployeeService.getEmployeesByDateOfBirthday(date))
        .thenReturn(Collections.singletonList(emp2));
    mockMvc.perform(
        get("/employees/?date={date}", date)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("employeeFilter"));
    verify(mockEmployeeService).getEmployeesByDateOfBirthday(date);
  }

  @Test
  public void testGetEmployeeBetweenDatesOfBirthday() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetEmployeeBetweenDatesOfBirthday()");
    LocalDate dateFrom = LocalDate.of(1999, 2, 28);
    LocalDate dateTo = LocalDate.of(2000, 12, 5);
    when(mockEmployeeService.getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo))
        .thenReturn(employees);
    mockMvc.perform(
        get("/employees/?dateFrom={dateFrom}&dateTo={dateTo}", dateFrom, dateTo)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("employeeFilter"));
    verify(mockEmployeeService).getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
  }

}
