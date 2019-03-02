package com.nikolay.funtest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

@Transactional
@Rollback
public class ApplicationFunctionalTest {

  private static final Logger LOGGER = LogManager.getLogger();

  @BeforeClass
  public static void setup() {
    String port = System.getProperty("server.port");

    if (port == null) {
      RestAssured.port = 8081;
    } else {
      RestAssured.port = Integer.valueOf(port);
    }

    String basePath = System.getProperty("server.base");
    if (basePath == null) {
      basePath = "/rest/";
    }
    RestAssured.basePath = basePath;

    String baseHost = System.getProperty("server.host");
    if (baseHost == null) {
      baseHost = "http://127.0.0.1";
    }
    RestAssured.baseURI = baseHost;

  }

  @Test(dataProvider = "department", priority = 1)
  public void createDepartment_WithCorrectData_success(Long id, String departmentName) {
    LOGGER.debug("createDepartment_WithCorrectData_success()");
    Map<String, String> dep = new HashMap<>();
    dep.put("departmentName", departmentName);

    given().log().all()
        .contentType("application/json")
        .body(dep)
        .when()
        .post("department/")
        .then().assertThat()
        .body(containsString(id.toString()))
        .statusCode(201);
  }

  @Test(dataProvider = "department", priority = 2)
  public void getDepartment_WithCorrectId_success(Long id, String departmentName) {
    LOGGER.debug("getDepartment_WithCorrectId_success");
    given().log().all()
        .when()
        .get("department/{id}", id)
        .then().assertThat()
        .body("id", equalTo(id.intValue()))
        .body("departmentName", equalTo(departmentName))
        .body("averageSalary", equalTo(0))
        .statusCode(302);
  }

  @Test(priority = 2)
  public void getAllDepartment_success() {
    LOGGER.debug("getAllDepartment_success");
    given().log().all()
        .when()
        .get("department/")
        .then().assertThat()
        .body("size()", is(3))
        .statusCode(200);
  }

  @Test(dataProvider = "employee", priority = 3)
  public void createEmployee_WithCorrectData_success(Long empId, Long depId, String fullName,
      String birthday, String salary) {
    LOGGER.debug("createEmployee_WithCorrectData_success()");
    Map<String, String> employee = new HashMap<>();
    employee.put("departmentId", depId.toString());
    employee.put("fullName", fullName);
    employee.put("birthday", birthday);
    employee.put("salary", salary);

    given().log().all()
        .contentType("application/json")
        .body(employee)
        .when()
        .post("employee/")
        .then().assertThat()
        .body(containsString(empId.toString()))
        .statusCode(201);
  }

  @Test(dataProvider = "employee", priority = 4)
  public void getEmployee_WithCorrectId_success(Long empId, Long depId, String fullName,
      String birthday, String salary) {
    LOGGER.debug("getEmployee_WithCorrectId_success");
    given().log().all()
        .when()
        .get("employee/{id}", empId)
        .then().assertThat()
        .body("id", equalTo(empId.intValue()))
        .body("departmentId", equalTo(depId.intValue()))
        .body("fullName", equalTo(fullName))
        .body("birthday", equalTo(birthday))
        .body("salary", comparesEqualTo(Float.valueOf(salary)))
        .statusCode(302);
  }

  @Test(priority = 4)
  public void getAllEmployee_success() {
    LOGGER.debug("getAllEmployee_success");
    given().log().all()
        .when()
        .get("employee/")
        .then().assertThat()
        .body("size()", is(3))
        .statusCode(200);
  }

  @Test(priority = 5)
  public void updateDepartment_success() {
    LOGGER.debug("updateDepartment_success");
    Map<String, String> dep = new HashMap<>();
    dep.put("id", "0");
    dep.put("departmentName", "bigdata");

    given().log().all()
        .contentType("application/json")
        .body(dep)
        .when()
        .put("department/{id}", 0)
        .then().assertThat()
        .statusCode(202);
  }

  @Test(priority = 6)
  public void getUpdateDepartment_WithCorrectId_success() {
    LOGGER.debug("getUpdateDepartment_WithCorrectId_success");
    given().log().all()
        .when()
        .get("department/{id}", 0)
        .then().assertThat()
        .body("id", equalTo(0))
        .body("departmentName", equalTo("bigdata"))
        .statusCode(302);
  }


  @Test(priority = 5)
  public void updateEmployee_success() {
    LOGGER.debug("updateEmployee_success");
    Map<String, String> employee = new HashMap<>();
    employee.put("id", "0");
    employee.put("departmentId", "1");
    employee.put("fullName", "Nikolay");
    employee.put("birthday", "1999-02-02");
    employee.put("salary", "200");
    given().log().all()
        .contentType("application/json")
        .body(employee)
        .when()
        .put("employee/{id}", 0)
        .then().assertThat()
        .statusCode(202);
  }

  @Test(priority = 6)
  public void getUpdateEmployee_WithCorrectId_success() {
    LOGGER.debug("getUpdateEmployee_WithCorrectId_success");
    given().log().all()
        .when()
        .get("employee/{id}", 0)
        .then().assertThat()
        .body("id", equalTo(0))
        .body("departmentId", equalTo(1))
        .body("departmentName", equalTo("java"))
        .body("fullName", equalTo("Nikolay"))
        .body("birthday", equalTo("1999-02-02"))
        .body("salary", comparesEqualTo(200.0f))
        .statusCode(302);
  }

  @Test(dataProvider = "id", priority = 7)
  public void deleteEmployee_success(Long id) {
    LOGGER.debug("deleteEmployee_success");
    given().log().all()
        .when()
        .delete("employee/{id}", id)
        .then().assertThat()
        .statusCode(200);
  }


  @Test(dataProvider = "id", priority = 8)
  public void deleteDepartment_success(Long id) {
    LOGGER.debug("deleteDepartment_success");
    given().log().all()
        .when()
        .delete("department/{id}", id)
        .then().assertThat()
        .statusCode(200);
  }

  @DataProvider(name = "id")
  public Object[][] createTestDataRecords() {
    return new Object[][]{
        {0L},
        {1L},
        {2L}
    };
  }

  @DataProvider(name = "department")
  public Object[][] createTestDepartmentDataRecords() {
    return new Object[][]{
        {0L, "management"},
        {1L, "java"},
        {2L, "gameDev"}
    };
  }

  @DataProvider(name = "employee")
  public Object[][] createTestEmployeeDataRecords() {
    return new Object[][]{
        {0L, 0L, "Nikolay", "1999-02-02", "200.0"},
        {1L, 1L, "Jack", "1998-02-02", "200.0"},
        {2L, 2L, "Dmitry", "1998-02-02", "200.0"},
    };
  }

}

