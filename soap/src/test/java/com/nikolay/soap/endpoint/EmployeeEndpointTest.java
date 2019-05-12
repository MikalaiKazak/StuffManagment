package com.nikolay.soap.endpoint;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-soap-mock.xml"})
public class EmployeeEndpointTest {

  private MockWebServiceClient mockClient;

  @Autowired
  private ApplicationContext applicationContext;

  @Before
  public void setUp() {
    mockClient = MockWebServiceClient.createClient(applicationContext);
  }

  @Test
  public void addEmployee_CorrectEmployee_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/addEmployeeRequest.xml");
    Resource response = new ClassPathResource("response/employee/addEmployeeResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void getEmployeeById_CorrectId_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/getEmployeeRequest.xml");
    Resource response = new ClassPathResource("response/employee/getEmployeeResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void getAllEmployees_CorrectRequest_success() throws IOException {
    Resource request = new ClassPathResource("request/department/getAllDepartmentsRequest.xml");
    Resource response = new ClassPathResource("response/department/getAllDepartmentsResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void getAllEmployeesByDateOfBirthday_CorrectRequest_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/getAllEmployeeByDateOfBirthdayRequest.xml");
    Resource response = new ClassPathResource("response/employee/getAllEmployeeByDateOfBirthdayResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void getAllEmployeesBetweenDateOfBirthday_CorrectRequest_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/getAllEmployeeBetweenDateOfBirthdayRequest.xml");
    Resource response = new ClassPathResource("response/employee/getAllEmployeeBetweenDateOfBirthdayResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void updateEmployee_CorrectEmployee_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/updateEmployeeRequest.xml");
    Resource response = new ClassPathResource("response/employee/updateEmployeeResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void deleteEmployee_CorrectId_success() throws IOException {
    Resource request = new ClassPathResource("request/employee/deleteEmployeeRequest.xml");
    Resource response = new ClassPathResource("response/employee/deleteEmployeeResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }
}
