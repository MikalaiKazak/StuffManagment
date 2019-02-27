package com.nikolay.webapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/mock-test-webapp.xml"})
public class TestMainController {

  public static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  private MainController mainController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    LOGGER.debug("execute before test method");
    mockMvc = MockMvcBuilders.standaloneSetup(mainController)
        .setMessageConverters(new MappingJackson2HttpMessageConverter())
        .build();
  }

  @Test
  public void defaultPageTest() throws Exception {
    LOGGER.debug("defaultPageTest()");
    mockMvc.perform(get("/"))
        .andExpect(view().name("index"))
        .andExpect(status().isOk());
  }
}
