package com.nikolay.rest;

import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test-rest-mock.xml"})
public class TestMainRestController {

    public static final Logger LOGGER = LogManager.getLogger();

    @Resource
    private MainRestController mainRestController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        LOGGER.error("execute: beforeTest()");
        mockMvc = standaloneSetup(mainRestController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();
    }

    /**
     * After method.
     */
    @After
    public void afterMethod() {
        LOGGER.error("execute: afterTest()");
    }

    @Test
    public void testMainController() throws Exception {
        LOGGER.debug("test TestMainRestController: run testMainController()");
        mockMvc.perform(
            get("/").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

}
