package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new User(null, "Albert", "albert@example.com"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Albert")).andExpect(jsonPath("$.email").value("albert@example.com"));
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        String response = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new User(null, "Albert", "albert@example.com"))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(response, User.class);

        mockMvc.perform(get("/users/{id}", user.id())).andExpect(jsonPath("$.id").value(notNullValue())).andExpect(jsonPath("$.name").value("Albert")).andExpect(jsonPath("$.email").value("albert@example.com"));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        mockMvc.perform(get("/users/{id}", UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new User(null, "albert", "albert@example.com")))).andExpect(status().isOk());
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new User(null, "ALBERT12345", "albert2@example.com")))).andExpect(status().isOk());
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new User(null, "John", "john@example.com")))).andExpect(status().isOk());

        mockMvc.perform(get("/users").param("name", "Albert"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("albert"))
                .andExpect(jsonPath("$[1].name").value("ALBERT12345"));
    }
}
