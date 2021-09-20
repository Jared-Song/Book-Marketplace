package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.Repositories.UserRepositoryTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepositoryTest userRepository;

// @Test
// public void givenEmployees_whenGetEmployees_thenStatus200()
//   throws Exception {

//     createTestEmployee("bob");

//     mvc.perform(get("/api/employees")
//       .contentType(MediaType.APPLICATION_JSON))
//       .andExpect(status().isOk())
//       .andExpect(content()
//       .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//       .andExpect(jsonPath("$[0].name", is("bob")));
// }
}