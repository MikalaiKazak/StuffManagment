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

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;
import com.nikolay.rest.controller.DepartmentRestController;
import com.nikolay.service.DepartmentService;
import com.nikolay.utility.validate.DepartmentValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-rest-mock.xml"})
public class TestDepartmentRestController {

  public static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  private DepartmentService mockDepartmentService;

  @Autowired
  private DepartmentValidator departmentValidator;

  private MockMvc mockMvc;
  private Department saveDepartment;
  private ResponseDepartmentDto responseDepartmentDto;
  private List<ResponseDepartmentDto> departments;

  @Before
  public void setUp() {
    LOGGER.error("execute: beforeTest()");
    saveDepartment = new Department(1L, "New Department");
    responseDepartmentDto = new ResponseDepartmentDto(14L, "Services", BigDecimal.valueOf(3249));
    departments = Arrays.asList(responseDepartmentDto, responseDepartmentDto);
    mockMvc = standaloneSetup(new DepartmentRestController(mockDepartmentService, departmentValidator))
        .setMessageConverters(createJacksonMessageConverter())
        .build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(mockDepartmentService);
    reset(mockDepartmentService);
    LOGGER.error("execute: afterTest()");
  }

  @Test
  public void testGetDepartmentById() throws Exception {
    LOGGER.debug("test TestDepartmentRestController: run testGetDepartmentById()");
    when(mockDepartmentService.getDepartmentById(14L)).thenReturn(responseDepartmentDto);
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/department/14")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(content().string(mapper.writeValueAsString(responseDepartmentDto)));
    verify(mockDepartmentService).getDepartmentById(14L);
  }

  @Test
  public void testGetAllDepartments() throws Exception {
    LOGGER.debug("test TestDepartmentRestController: run testGetAllDepartments()");
    when(mockDepartmentService.getAllDepartments()).thenReturn(departments);
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    mockMvc.perform(
        get("/department/")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(content().string(mapper.writeValueAsString(departments)))
        .andExpect(status().isOk());
    verify(mockDepartmentService).getAllDepartments();
  }

  @Test
  public void testAddDepartment() throws Exception {
    LOGGER.debug("test TestDepartmentRestController: run testAddDepartment()");
    when(mockDepartmentService.saveDepartment(any(Department.class))).thenReturn(1L);
    String departmentJson = createObjectMapperWithJacksonConverter().writeValueAsString(
        saveDepartment);
    mockMvc.perform(
        post("/department/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(departmentJson))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("1"));
    verify(mockDepartmentService).saveDepartment(any(Department.class));
  }

  @Test
  public void testRemoveDepartment() throws Exception {
    LOGGER.debug("test TestDepartmentRestController: run testRemoveDepartment()");
    when(mockDepartmentService.deleteDepartment(1L)).thenReturn(true);
    mockMvc.perform(
        delete("/department/1")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(true)));
    verify(mockDepartmentService).deleteDepartment(1L);

  }

  @Test
  public void testUpdateDepartment() throws Exception {
    LOGGER.debug("test TestDepartmentRestController: run testUpdateDepartment()");
    when(mockDepartmentService.updateDepartment(any(Department.class)))
        .thenReturn(true);
    String departmentJson = createObjectMapperWithJacksonConverter().writeValueAsString(
        saveDepartment);
    mockMvc.perform(
        put("/department/14")
            .accept(MediaType.APPLICATION_JSON)
            .content(departmentJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(content().string(String.valueOf(true)))
        .andExpect(status().isAccepted());
    verify(mockDepartmentService).updateDepartment(any(Department.class));
  }

}
