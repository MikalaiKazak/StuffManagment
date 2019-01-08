package com.nikolay.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The type Test department rest dao.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-dao-rest.xml"})
public class TestDepartmentRestDao {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${app.url}")
    private String url;

    @Value("${point.departments}")
    private String departmentsPoint;

    @Autowired
    private DepartmentService departmentRestDao;

    @Autowired
    private RestTemplate mockRestTemplate;

    private Department dep1;
    private Department dep2;
    private Department dep3;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        dep1 = new Department("New Department", BigDecimal.valueOf(500));
        dep2 = new Department(1L, "Services", BigDecimal.valueOf(3249));
        dep3 = new Department(2L, "New Department", BigDecimal.valueOf(500));
        LOGGER.error("execute: beforeTest()");
    }

    @Test
    public void testGetDepartmentById() {
        LOGGER.debug("test TestDepartmentRestDao: run testGetDepartmentById()");
        when(mockRestTemplate.getForObject(url + departmentsPoint + 1, Department.class))
                .thenReturn(dep2);
        Department department = departmentRestDao.getDepartmentById(1L);
        assertNotNull(department);
        assertEquals(1L, department.getId().longValue());
        verify(mockRestTemplate, times(1))
                .getForObject(url + departmentsPoint + 1, Department.class);
    }

    @Test
    public void testGetDepartmentByName() {
        LOGGER.debug("test TestDepartmentRestDao run: testGetDepartmentByName()");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + departmentsPoint)
                .queryParam("name", "Services");
        when(mockRestTemplate.getForObject(builder.toUriString(), Department.class))
                .thenReturn(dep2);
        Department department = departmentRestDao.getDepartmentByName("Services");
        assertNotNull(department);
        assertEquals("Services", department.getDepartmentName());
        verify(mockRestTemplate, times(1)).getForObject(builder.toUriString(), Department.class);
    }

    @Test
    public void testGetAllDepartment() {
        LOGGER.debug("test TestDepartmentRestDao: run testGetAllDepartment()");
        when(mockRestTemplate.getForObject(url + departmentsPoint, Department[].class)).thenReturn(
                new Department[]{dep1, dep2});
        List<Department> departmentList = departmentRestDao.getAllDepartments();
        assertNotNull(departmentList);
        assertEquals(2, departmentList.size());
        verify(mockRestTemplate, times(1)).getForObject(url + departmentsPoint, Department[].class);
    }

    @Test
    public void testDeleteDepartment() {
        LOGGER.debug("test TestDepartmentRestDao: run testDeleteDepartment()");
        doNothing().when(mockRestTemplate).delete(url + departmentsPoint + 1);
        departmentRestDao.deleteDepartment(1L);
        verify(mockRestTemplate, times(1)).delete(url + departmentsPoint + 1);
    }

    @Test
    public void testUpdateDepartment() {
        LOGGER.debug("test TestDepartmentRestDao: run testUpdateDepartment()");
        doNothing().when(mockRestTemplate).put(url + departmentsPoint + 1, dep2);
        departmentRestDao.updateDepartment(dep2);
        verify(mockRestTemplate, times(1)).put(url + departmentsPoint + 1, dep2);
    }

    @Test
    public void testSaveDepartment() {
        LOGGER.debug("test TestDepartmentRestDao: run testSaveDepartment()");
        when(mockRestTemplate.postForEntity(url + departmentsPoint, dep1, Long.class))
                .thenReturn(new ResponseEntity<>(1L, HttpStatus.FOUND));
        Long departmentId = departmentRestDao.saveDepartment(dep1);
        assertNotNull(departmentId);
        assertEquals(1L, departmentId.longValue());
        verify(mockRestTemplate, times(1)).postForEntity(url + departmentsPoint, dep1, Long.class);
    }

}
