package us.stad.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "user1", password = "password1", roles = "USER")
  public void testCRUD() throws Exception {

    String original = IOUtils.toString(this.getClass().getResourceAsStream("/randy-original.json"));
    String update = IOUtils.toString(this.getClass().getResourceAsStream("/randy-update.json"));

    // create

    this.mockMvc.perform(post("/employees")
      .content(original)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value("Randy"));

    // get

    this.mockMvc.perform(get("/employees/1"))
      .andExpect(jsonPath("$.firstName").value("Randy"))
      .andExpect(status().isOk());

    // update randy

    this.mockMvc.perform(put("/employees/1")
      .content(update)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.middleInitial").value("M"));

    // delete randy

    this.mockMvc.perform(delete("/employees/1"))
      .andExpect(status().isOk());
  
    // gone

    this.mockMvc.perform(get("/employees/1"))
      .andExpect(status().isNotFound());

  }

  @Test
  public void testGetWithInvalidReturnsNotFound() throws Exception {
    this.mockMvc.perform(get("/employees/99999999999"))
      .andExpect(status().isNotFound());
  }

  @Test
  
  public void testGetAll() throws Exception {

    String mark = IOUtils.toString(this.getClass().getResourceAsStream("/mark.json"));
    String randy = IOUtils.toString(this.getClass().getResourceAsStream("/randy-update.json"));

    // create mark

    this.mockMvc.perform(post("/employees")
      .content(mark)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value("Mark"));

    // create randy

    this.mockMvc.perform(post("/employees")
      .content(randy)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value("Randy"));

    // count

    this.mockMvc.perform(get("/employees"))
      .andExpect(jsonPath("$", hasSize(2)));

  }

  @Test
  @WithMockUser(username = "user1", password = "password1", roles = "USER")
  public void testDeleteWithInvalidReturnsNotFound() throws Exception {
    this.mockMvc.perform(delete("/employees/99999999999"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteWithoutAuth() throws Exception {
    this.mockMvc.perform(delete("/employees/99999999999"))
      .andExpect(status().isUnauthorized());
  }

}
