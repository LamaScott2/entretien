package com.orange.demo.controller;

import com.google.gson.Gson;
import com.orange.demo.models.dtos.TokenDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Verify that Administrator can login")
    void testAdminLogin() throws Exception {
        this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"emailAdmin\",\"password\":\"passwordAdmin\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.isA(String.class)));
    }

    @Test
    @DisplayName("Verify that User can login")
    void testUserLogin() throws Exception {
        this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"emailUser\",\"password\":\"passwordUser\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.isA(String.class)));
    }

    @Test
    @DisplayName("Verify that unknown user cannot login and returned http code is 500")
    void testFailureLogin() throws Exception {
        //TODO
    }

    @Test
    @DisplayName("Verify that user connected can login and call restricted endpoint")
    void testAdminRestrictedEndpoint() throws Exception {
        MvcResult resultAdmin = this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"emailAdmin\",\"password\":\"passwordAdmin\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.isA(String.class)))
                .andReturn();

        String contentAsStringAdmin = resultAdmin.getResponse().getContentAsString();
        Gson g = new Gson();
        TokenDto tokenAdmin = g.fromJson(contentAsStringAdmin, TokenDto.class);

        this.mockMvc.perform(get("/hello").header("Authorization", tokenAdmin))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", Matchers.equalTo("Hello emailAdmin!")));

        MvcResult resultUser = this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"emailUser\",\"password\":\"passwordUser\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.isA(String.class)))
                .andReturn();

        String contentAsStringUser = resultUser.getResponse().getContentAsString();
        TokenDto tokenUser = g.fromJson(contentAsStringUser, TokenDto.class);

        this.mockMvc.perform(get("/hello").header("Authorization", tokenUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", Matchers.equalTo("Hello emailUser!")));
    }
}
