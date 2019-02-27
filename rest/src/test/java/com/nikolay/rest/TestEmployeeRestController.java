package com.nikolay.rest;

import static com.nikolay.rest.config.JacksonConverter.createJacksonMessageConverter;
import static com.nikolay.rest.config.JacksonConverter.createObjectMapperWithJacksonConverter;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.model.Employee;
import com.nikolay.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-rest-mock.xml"})
public class TestEmployeeRestController {

  public static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  EmployeeService mockEmployeeService;

  @Resource
  private EmployeeRestController employeeRestController;

  private MockMvc mockMvc;

  private Employee correctEmployee;
  private Employee saveEmployee;
  private List<Employee> employees;

  @Before
  public void setUp() {
    LOGGER.error("execute: beforeTest()");
    saveEmployee = new Employee(3L, "Services", "Nikolay Kozak", LocalDate.of(1999, 2, 28),
        BigDecimal.valueOf(350));
    correctEmployee = new Employee(1L, 1L, "Services", "Nikolay Kozak", LocalDate.of(1999, 2, 28),
        BigDecimal.valueOf(350));
    employees = Arrays.asList(correctEmployee, correctEmployee);
    mockMvc = standaloneSetup(employeeRestController)
        .setMessageConverters(createJacksonMessageConverter())
        .build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(mockEmployeeService);
    reset(mockEmployeeService);
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetAllEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetAllEmployee()");
    when(mockEmployeeService.getAllEmployees()).thenReturn(employees);
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/employee/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(employees)));
    verify(mockEmployeeService).getAllEmployees();
  }

  @Test
  public void testAddEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testAddEmployee()");
    when(mockEmployeeService.saveEmployee(any(Employee.class))).thenReturn(1L);
    String employeeJson = createObjectMapperWithJacksonConverter().writeValueAsString(saveEmployee);
    mockMvc.perform(
        post("/employee/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(employeeJson))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("1"));
    verify(mockEmployeeService).saveEmployee(any(Employee.class));
  }

  @Test
  public void testUpdateDepartment() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testUpdateEmployee()");
    when((mockEmployeeService.getEmployeeById(1L))).thenReturn(correctEmployee);
    when(mockEmployeeService.updateEmployee(correctEmployee)).thenReturn(true);
    String employeeJson = createObjectMapperWithJacksonConverter().writeValueAsString(
        correctEmployee);
    mockMvc.perform(
        put("/employee/1")
            .accept(MediaType.APPLICATION_JSON)
            .content(employeeJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(content().string(String.valueOf(true)))
        .andExpect(status().isAccepted());
    verify(mockEmployeeService).getEmployeeById(1L);
    verify(mockEmployeeService).updateEmployee(correctEmployee);
  }

  @Test
  public void testGetEmployeeById() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetEmployeeById()");
    when(mockEmployeeService.getEmployeeById(1L)).thenReturn(correctEmployee);
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/employee/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(content().string(mapper.writeValueAsString(correctEmployee)))
        .andExpect(status().isFound());
    verify(mockEmployeeService).getEmployeeById(1L);
  }

  @Test
  public void testGetEmployeeByDateOfBirthday() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetEmployeeByDateOfBirthday()");
    LocalDate date = LocalDate.of(1999, 2, 28);
    when(mockEmployeeService.getEmployeesByDateOfBirthday(date))
        .thenReturn(Collections.singletonList(correctEmployee));
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/employee/?date={date}", date)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(
            content().string(mapper.writeValueAsString(Collections.singleton(correctEmployee))));
    verify(mockEmployeeService).getEmployeesByDateOfBirthday(date);
  }

  @Test
  public void testGetEmployeeBetweenDatesOfBirthday() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testGetEmployeeBetweenDatesOfBirthday()");
    LocalDate dateFrom = LocalDate.of(1999, 2, 28);
    LocalDate dateTo = LocalDate.of(2000, 12, 5);
    when(mockEmployeeService.getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo))
        .thenReturn(employees);
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/employee/?dateFrom={dateFrom}&dateTo={dateTo}", dateFrom, dateTo)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(content().string(mapper.writeValueAsString(employees)));
    verify(mockEmployeeService).getEmployeesBetweenDatesOfBirthday(dateFrom, dateTo);
  }


  @Test
  public void testRemoveEmployee() throws Exception {
    LOGGER.debug("test TestEmployeeRestController: run testRemoveEmployee()");
    when(mockEmployeeService.deleteEmployee(1L)).thenReturn(true);
    mockMvc.perform(
        delete("/employee/1")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(content().string(String.valueOf(true)))
        .andExpect(status().isOk());
    verify(mockEmployeeService).deleteEmployee(1L);
  }
}