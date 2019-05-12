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

import com.nikolay.model.Department;
import com.nikolay.model.dto.ResponseDepartmentDto;
import com.nikolay.model.xsds.AddDepartmentRequest;
import com.nikolay.model.xsds.AddDepartmentResponse;
import com.nikolay.model.xsds.DeleteDepartmentRequest;
import com.nikolay.model.xsds.DeleteDepartmentResponse;
import com.nikolay.model.xsds.DepartmentResponse;
import com.nikolay.model.xsds.GetAllDepartmentsRequest;
import com.nikolay.model.xsds.GetAllDepartmentsResponse;
import com.nikolay.model.xsds.GetDepartmentByIdRequest;
import com.nikolay.model.xsds.GetDepartmentByIdResponse;
import com.nikolay.model.xsds.UpdateDepartmentRequest;
import com.nikolay.model.xsds.UpdateDepartmentResponse;
import com.nikolay.service.DepartmentService;

@Endpoint
public class DepartmentEndpoint {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final String NAMESPACE_URI = "http://www.human-resources.com/definition";

  private final DepartmentService departmentService;

  @Autowired
  public DepartmentEndpoint(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDepartmentByIdRequest")
  @ResponsePayload
  public GetDepartmentByIdResponse getDepartmentById(
      @RequestPayload GetDepartmentByIdRequest request) {
    LOGGER.debug("getDepartmentById()");
    GetDepartmentByIdResponse response = new GetDepartmentByIdResponse();
    DepartmentResponse departmentResponse = new DepartmentResponse();
    ResponseDepartmentDto departmentDto = departmentService.getDepartmentById(request.getId());
    BeanUtils.copyProperties(departmentDto, departmentResponse);
    response.setDepartmentResponse(departmentResponse);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllDepartmentsRequest")
  @ResponsePayload
  public GetAllDepartmentsResponse getAllDepartment(
      @RequestPayload GetAllDepartmentsRequest request) {
    LOGGER.debug("getAllDepartment()");
    GetAllDepartmentsResponse response = new GetAllDepartmentsResponse();
    List<ResponseDepartmentDto> departments = departmentService.getAllDepartments();
    for (ResponseDepartmentDto department : departments) {
      DepartmentResponse departmentResponse = new DepartmentResponse();
      BeanUtils.copyProperties(department, departmentResponse);
      response.getDepartmentResponse().add(departmentResponse);
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addDepartmentRequest")
  @ResponsePayload
  public AddDepartmentResponse addDepartment(
      @RequestPayload AddDepartmentRequest request) {
    LOGGER.debug("addDepartment()");
    AddDepartmentResponse response = new AddDepartmentResponse();
    Department department = new Department();
    BeanUtils.copyProperties(request, department);
    Long departmentId = departmentService.saveDepartment(department);
    response.setId(departmentId);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateDepartmentRequest")
  @ResponsePayload
  public UpdateDepartmentResponse updateDepartment(
      @RequestPayload UpdateDepartmentRequest request) {
    LOGGER.debug("updateDepartment()");
    UpdateDepartmentResponse response = new UpdateDepartmentResponse();
    Department department = new Department();
    BeanUtils.copyProperties(request, department);
    Boolean result = departmentService.updateDepartment(department);
    response.setResult(result);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDepartmentRequest")
  @ResponsePayload
  public DeleteDepartmentResponse deleteDepartment(
      @RequestPayload DeleteDepartmentRequest request) {
    LOGGER.debug("deleteDepartment()");
    DeleteDepartmentResponse response = new DeleteDepartmentResponse();
    Boolean result = departmentService.deleteDepartment(request.getId());
    response.setResult(result);
    return response;
  }

}
