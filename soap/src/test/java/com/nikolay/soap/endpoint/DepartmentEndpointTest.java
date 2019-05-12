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
public class DepartmentEndpointTest {

  private MockWebServiceClient mockClient;

  @Autowired
  private ApplicationContext applicationContext;

  @Before
  public void setUp() {
    mockClient = MockWebServiceClient.createClient(applicationContext);
  }

  @Test
  public void addDepartment_CorrectDepartment_success() throws IOException {
    Resource request = new ClassPathResource("request/department/addDepartmentRequest.xml");
    Resource response = new ClassPathResource("response/department/addDepartmentResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));

  }

  @Test
  public void getDepartmentById_CorrectId_success() throws IOException {
    Resource request = new ClassPathResource("request/department/getDepartmentRequest.xml");
    Resource response = new ClassPathResource("response/department/getDepartmentResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void getAllDepartments_CorrectRequest_success() throws IOException {
    Resource request = new ClassPathResource("request/department/getAllDepartmentsRequest.xml");
    Resource response = new ClassPathResource("response/department/getAllDepartmentsResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void updateDepartment_CorrectDepartment_success() throws IOException {
    Resource request = new ClassPathResource("request/department/updateDepartmentRequest.xml");
    Resource response = new ClassPathResource("response/department/updateDepartmentResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }

  @Test
  public void deleteDepartment_CorrectId_success() throws IOException {
    Resource request = new ClassPathResource("request/department/deleteDepartmentRequest.xml");
    Resource response = new ClassPathResource("response/department/deleteDepartmentResponse.xml");
    mockClient.sendRequest(withPayload(request)).andExpect(payload(response));
  }
}
