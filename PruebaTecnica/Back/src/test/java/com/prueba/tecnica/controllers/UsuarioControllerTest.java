package com.prueba.tecnica.controllers;

import com.google.gson.Gson;
import com.prueba.tecnica.models.dto.RequestLoginDTO;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.util.DataSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    DataSetup dataSetup;

    @BeforeEach
    void setUp() {
        dataSetup = new DataSetup();
    }

    @Test
    @DisplayName("Guardar usuario exitosamente")
    void guardarUsuario() {
        User usuario = dataSetup.getUsuario();

        Gson json = new Gson();
        String request = json.toJson(usuario);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/usuario/createUser", request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
            log.info("Test: guardarUsuario exitoso");
        } catch (Exception exception) {
            log.error("Error test guardarUsuario: ", exception);
            Assert.fail();
        }
    }

    @Test
    @DisplayName("Login exitoso")
    void loginUsuario() {
        RequestLoginDTO loginUsuarioDTO = new RequestLoginDTO();
        loginUsuarioDTO.setUserName("test");
        loginUsuarioDTO.setPassword("test123");

        Gson json = new Gson();
        String request = json.toJson(loginUsuarioDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/usuario/loginUser?grant_type=client_credentials", request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
            log.info("Test: loginUser exitoso");
        } catch (Exception exception) {
            log.error("Error test loginUsuario: ", exception);
            Assert.fail();
        }
    }

    @Test
    @DisplayName("Login falla")
    void loginUsuarioFailed() {
        RequestLoginDTO loginUsuarioDTO = new RequestLoginDTO();
        loginUsuarioDTO.setUserName("test");
        loginUsuarioDTO.setPassword("test12345");

        Gson json = new Gson();
        String request = json.toJson(loginUsuarioDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/usuario/loginUser?grant_type=client_credentials", request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.NOT_ACCEPTABLE.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }

    /** @Test
    @DisplayName("Guardar usuario repetido")
    void saveUsuarioRepetido() {
        User usuario = dataSetup.getUsuario();

        Gson json = new Gson();
        String request = json.toJson(usuario);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/usuario/createUser", request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.NOT_ACCEPTABLE.value()));
            log.info("Test: guardarUsuarioRepetido exitoso");
        } catch (Exception exception) {
            log.error("Error test guardarUsuarioRepetido: ", exception);
            Assert.fail();
        }
    } */
}