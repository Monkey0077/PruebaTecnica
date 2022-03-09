package com.prueba.tecnica.controllers;

import com.google.gson.Gson;
import com.prueba.tecnica.models.dao.ITaskDao;
import com.prueba.tecnica.models.dto.TaskDTO;
import com.prueba.tecnica.models.entity.Task;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.util.DataSetup;
import com.prueba.tecnica.util.TokenJwt;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@Slf4j
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ITaskDao taskDao;

    DataSetup dataSetup;

    @Autowired
    private TokenJwt tokenJwt;

    @BeforeEach
    void setUp() {
        dataSetup = new DataSetup();
    }

    private static final String AUTHORIZATION = "Authorization";

    @Test
    @DisplayName("Guardar tarea")
    void guardarTarea() {
        User usuario = dataSetup.getUsuario();
        TaskDTO tareaDTO = dataSetup.getTarea();
        Gson json = new Gson();
        String request = json.toJson(tareaDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/tasks/createTask", request)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
        } catch (Exception exception) {
            log.error("Error test guardarTarea: ", exception);
            Assert.fail();
        }
    }

    @Test
    @DisplayName("Guardar tarea error")
    void guardarTareaConError() {
        User usuario = dataSetup.getUsuario();
        TaskDTO tareaDTO = dataSetup.getTarea();
        tareaDTO.setIdUsuario(123456l);
        Gson json = new Gson();
        String request = json.toJson(tareaDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/tasks/createTask", request)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
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

    @Test
    @DisplayName("Consultar tarea")
    void consultarTareasUsuario() {
        User usuario = dataSetup.getUsuario();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/tasks/listTasks")
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .param("idUsuario", "1") //Consulta para usuario con id 1
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }

    @Test
    void consultarTarea() {
        User usuario = dataSetup.getUsuario();
        List<Task> tareaList = (List<Task>) taskDao.findAll();
        Long idTarea = tareaList.get(0).getIdTarea();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/tasks/"+ idTarea)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }

    @Test
    void actualizarTarea() {
        User usuario = dataSetup.getUsuario();
        List<Task> tareaList = (List<Task>) taskDao.findAll();
        TaskDTO tareaDTO = dataSetup.getTareaUpdate(tareaList.get(0));
        tareaDTO.setEstado((short) 2); // Se cambia de estado
        tareaDTO.setDescripcion("Se actualiza tarea");

        Gson json = new Gson();
        String request = json.toJson(tareaDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/tasks/updateTask", request)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }

    @Test
    @DisplayName("Borrar tarea")
    void borrarTarea() {
        User usuario = dataSetup.getUsuario();
        List<Task> tareaList = (List<Task>) taskDao.findAll();
        Long idTarea = tareaList.get(0).getIdTarea();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/tasks/"+ idTarea)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.OK.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }

    @Test
    @DisplayName("Borrar tarea no existente")
    void borrarTareaNoExiste() {
        User usuario = dataSetup.getUsuario();
        String idTarea = "1234561";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/tasks/"+ idTarea)
                .header(AUTHORIZATION, tokenJwt.generateToken(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.codeStatus").value(HttpStatus.NOT_ACCEPTABLE.value()));
        } catch (Exception exception) {
            log.error("Error test ", exception);
            Assert.fail();
        }
    }
}