package com.nikolay.soap.endpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.nikolay.model.DepartmentDtoResponse;
import com.nikolay.model.GetDepartmentByIdRequest;
import com.nikolay.model.GetDepartmentByIdResponse;
import com.nikolay.service.DepartmentService;

@Endpoint
public class DepartmentEndpoint {

  public static final Logger LOGGER = LogManager.getLogger();

  private static final String NAMESPACE_URI = "http://www.javaspringclub.com/deparmtent";

  private final DepartmentService departmentService;

  @Autowired
  public DepartmentEndpoint(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDepartmentByIdRequest")
  public GetDepartmentByIdResponse getDepartmentById(
      @RequestPayload GetDepartmentByIdRequest request) {
    LOGGER.debug("getDepartmentById()");
    GetDepartmentByIdResponse response = new GetDepartmentByIdResponse();
    DepartmentDtoResponse departmentDtoResponse = new DepartmentDtoResponse();
    BeanUtils.copyProperties(departmentService.getDepartmentById(request.getDepartmentId()),
        departmentDtoResponse);
    response.setDepartmentDtoResponse(departmentDtoResponse);
    return response;
  }

}
