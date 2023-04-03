package com.example.vitasoft_task.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void userTryCallOpMethod_accessDenied() throws Exception {
        mockMvc.perform(get("/op/get").with(httpBasic("федор","1234"))).andExpectAll(status().is4xxClientError());
    }


}
