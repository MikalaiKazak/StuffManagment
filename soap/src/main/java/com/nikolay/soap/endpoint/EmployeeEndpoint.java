package com.nikolay.soap.endpoint;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.nikolay.model.Employee;
import com.nikolay.model.dto.ResponseEmployeeDto;
import com.nikolay.model.xsds.AddEmployeeRequest;
import com.nikolay.model.xsds.AddEmployeeResponse;
import com.nikolay.model.xsds.DeleteEmployeeRequest;
import com.nikolay.model.xsds.DeleteEmployeeResponse;
import com.nikolay.model.xsds.EmployeeResponse;
import com.nikolay.model.xsds.GetAllEmployeesRequest;
import com.nikolay.model.xsds.GetAllEmployeesResponse;
import com.nikolay.model.xsds.GetEmployeeByIdRequest;
import com.nikolay.model.xsds.GetEmployeeByIdResponse;
import com.nikolay.model.xsds.GetEmployeesBetweenDatesOfBirthdayRequest;
import com.nikolay.model.xsds.GetEmployeesBetweenDatesOfBirthdayResponse;
import com.nikolay.model.xsds.GetEmployeesByDateOfBirthdayRequest;
import com.nikolay.model.xsds.GetEmployeesByDateOfBirthdayResponse;
import com.nikolay.model.xsds.UpdateEmployeeRequest;
import com.nikolay.model.xsds.UpdateEmployeeResponse;
import com.nikolay.service.EmployeeService;

@Endpoint
public class EmployeeEndpoint {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final String NAMESPACE_URI = "http://www.human-resources.com/definition";

  private final EmployeeService employeeService;


  @Autowired
  public EmployeeEndpoint(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeByIdRequest")
  @ResponsePayload
  public GetEmployeeByIdResponse getEmployeeById(
      @RequestPayload GetEmployeeByIdRequest request) {
    LOGGER.debug("getEmployeeById()");
    GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    ResponseEmployeeDto employeeDto = employeeService.getEmployeeById(request.getId());
    BeanUtils.copyProperties(employeeDto, employeeResponse);
    response.setEmployeeResponse(employeeResponse);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmployeesRequest")
  @ResponsePayload
  public GetAllEmployeesResponse getAllEmployees(
      @RequestPayload GetAllEmployeesRequest request) {
    LOGGER.debug("getAllEmployees()");
    GetAllEmployeesResponse response = new GetAllEmployeesResponse();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    List<ResponseEmployeeDto> employees = employeeService.getAllEmployees();
    for (ResponseEmployeeDto employee : employees) {
      BeanUtils.copyProperties(employee, employeeResponse);
      response.getEmployeeResponse().add(employeeResponse);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeesByDateOfBirthdayRequest")
  @ResponsePayload
  public GetEmployeesByDateOfBirthdayResponse getEmployeesByDateOfBirthdayResponse(
      @RequestPayload GetEmployeesByDateOfBirthdayRequest request) {
    LOGGER.debug("getEmployeesByDateOfBirthdayResponse()");
    GetEmployeesByDateOfBirthdayResponse response = new GetEmployeesByDateOfBirthdayResponse();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    List<ResponseEmployeeDto> employees = employeeService.getEmployeesByDateOfBirthday(request.getDate());
    for (ResponseEmployeeDto employee : employees) {
      BeanUtils.copyProperties(employee, employeeResponse);
      response.getEmployeeResponse().add(employeeResponse);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeesBetweenDatesOfBirthdayRequest")
  @ResponsePayload
  public GetEmployeesBetweenDatesOfBirthdayResponse getEmployeesBetweenDatesOfBirthdayResponse(
      @RequestPayload GetEmployeesBetweenDatesOfBirthdayRequest request) {
    LOGGER.debug("getEmployeesBetweenDatesOfBirthdayResponse()");
    GetEmployeesBetweenDatesOfBirthdayResponse response = new GetEmployeesBetweenDatesOfBirthdayResponse();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    List<ResponseEmployeeDto> employees = employeeService.getEmployeesBetweenDatesOfBirthday(request.getDateFrom(), request.getDateFrom());
    for (ResponseEmployeeDto employee : employees) {
      BeanUtils.copyProperties(employee, employeeResponse);
      response.getEmployeeResponse().add(employeeResponse);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
  @ResponsePayload
  public AddEmployeeResponse addEmployee(
      @RequestPayload AddEmployeeRequest request) {
    LOGGER.debug("addEmployee()");
    AddEmployeeResponse response = new AddEmployeeResponse();
    Employee employee = new Employee();
    BeanUtils.copyProperties(request, employee);
    Long employeeId = employeeService.saveEmployee(employee);
    response.setId(employeeId);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmployeeRequest")
  @ResponsePayload
  public UpdateEmployeeResponse updateEmployee(
      @RequestPayload UpdateEmployeeRequest request) {
    LOGGER.debug("updateEmployee()");
    UpdateEmployeeResponse response = new UpdateEmployeeResponse();
    Employee employee = new Employee();
    BeanUtils.copyProperties(request, employee);
    Boolean result = employeeService.updateEmployee(employee);
    response.setResult(result);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
  @ResponsePayload
  public DeleteEmployeeResponse deleteEmployee(
      @RequestPayload DeleteEmployeeRequest request) {
    LOGGER.debug("deleteEmployee()");
    DeleteEmployeeResponse response = new DeleteEmployeeResponse();
    Boolean result = employeeService.deleteEmployee(request.getId());
    response.setResult(result);
    return response;
  }

}
