package com.nikolay.soap.endpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nikolay.service.DepartmentService;

public class DepartmentEndpointTest {

  public static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  private DepartmentService departmentService;

  @Test
  public void getDepartmentById_WithCorrectId_success() {

  }
}
