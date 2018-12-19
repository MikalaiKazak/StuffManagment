package com.nikolay.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-rest-mock.xml"})
public class TestDepartmentRestController {

    @Resource
    private DepartmentRestController departmentRestController;

    private MockMvc mockMvc;

    private Department dep1;
    private Department dep2;
    private List<Department> departments;

    @Autowired
    DepartmentService mockDepartmentService;

    @Before
    public void setUp() {
        dep1 = new Department("New Department", BigDecimal.valueOf(500));
        dep2 = new Department(14L, "Services", BigDecimal.valueOf(3249));
        departments = Arrays.asList(dep1, dep2);
        mockMvc = standaloneSetup(departmentRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        reset(mockDepartmentService);
        verifyNoMoreInteractions(mockDepartmentService);
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        when(mockDepartmentService.getDepartmentById(14L)).thenReturn(dep2);
        mockMvc.perform(
                get("/department/14")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.departmentName").value("Services"));
        verify(mockDepartmentService).getDepartmentById(14L);
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        when(mockDepartmentService.getAllDepartments()).thenReturn(departments);
        mockMvc.perform(
                get("/department/")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(mockDepartmentService).getAllDepartments();
    }

    @Test
    public void testAddDepartment() throws Exception {
        when(mockDepartmentService.saveDepartment(any(Department.class))).thenReturn(1L);
        String department = new ObjectMapper().writeValueAsString(dep1);
        mockMvc.perform(
                post("/department/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(department))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        verify(mockDepartmentService).saveDepartment(any(Department.class));
    }

    @Test
    public void testRemoveDepartment() throws Exception {
        doNothing().when(mockDepartmentService).deleteDepartment(1L);
        mockMvc.perform(
                delete("/department/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(mockDepartmentService).deleteDepartment(1L);

    }

    @Test
    public void testUpdateDepartment() throws Exception {
        when((mockDepartmentService.getDepartmentById(14L))).thenReturn(dep2);
        doNothing().when(mockDepartmentService).updateDepartment(dep2);
        String department = new ObjectMapper().writeValueAsString(dep2);
        mockMvc.perform(
                put("/department/14")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(department)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        verify(mockDepartmentService).getDepartmentById(14L);
        verify(mockDepartmentService).updateDepartment(dep2);
    }

}
