package com.food.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.demo.dto.EventoCreateDTO;
import com.food.demo.dto.EventoDTO;
import com.food.demo.service.EventoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe listar eventos")
    void debeListarEventos() throws Exception {

        EventoDTO evento = new EventoDTO(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                LocalDateTime.of(2026, 8, 15, 10, 0),
                LocalDateTime.of(2026, 8, 15, 20, 0),
                "Parque O'Higgins",
                "publicado",
                10L
        );

        when(service.listarEventos())
                .thenReturn(List.of(evento));

        mockMvc.perform(get("/api/v3/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEvento").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Festival Gastronómico"));
    }

    @Test
    @DisplayName("Debe listar eventos disponibles")
    void debeListarEventosDisponibles() throws Exception {

        EventoDTO evento = new EventoDTO(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                LocalDateTime.of(2026, 8, 15, 10, 0),
                LocalDateTime.of(2026, 8, 15, 20, 0),
                "Parque O'Higgins",
                "publicado",
                10L
        );

        when(service.listarEventosDisponibles())
                .thenReturn(List.of(evento));

        mockMvc.perform(get("/api/v3/eventos/disponibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("publicado"));
    }

    @Test
    @DisplayName("Debe crear evento")
    void debeCrearEvento() throws Exception {

        EventoCreateDTO dto = new EventoCreateDTO(
                "Festival Gastronómico",
                "Food Fest Chile",
                LocalDateTime.of(2026, 8, 15, 10, 0),
                LocalDateTime.of(2026, 8, 15, 20, 0),
                "Parque O'Higgins",
                10L
        );

        EventoDTO respuesta = new EventoDTO(
                1L,
                dto.getNombre(),
                dto.getOrganizacionResponsable(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getUbicacion(),
                "borrador",
                dto.getIdOrganizador()
        );

        when(service.crearEvento(any(EventoCreateDTO.class)))
                .thenReturn(respuesta);

        mockMvc.perform(post("/api/v3/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEvento").value(1))
                .andExpect(jsonPath("$.estado").value("borrador"));
    }

    @Test
    @DisplayName("Debe obtener evento por id")
    void debeObtenerEventoPorId() throws Exception {

        EventoDTO evento = new EventoDTO(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                LocalDateTime.of(2026, 8, 15, 10, 0),
                LocalDateTime.of(2026, 8, 15, 20, 0),
                "Parque O'Higgins",
                "publicado",
                10L
        );

        when(service.obtenerEventoPorId(1L))
                .thenReturn(evento);

        mockMvc.perform(get("/api/v3/eventos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEvento").value(1));
    }

    @Test
    @DisplayName("Debe publicar evento")
    void debePublicarEvento() throws Exception {

        EventoDTO evento = new EventoDTO(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                LocalDateTime.of(2026, 8, 15, 10, 0),
                LocalDateTime.of(2026, 8, 15, 20, 0),
                "Parque O'Higgins",
                "publicado",
                10L
        );

        when(service.publicarEvento(1L))
                .thenReturn(evento);

        mockMvc.perform(put("/api/v3/eventos/1/publicar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("publicado"));
    }
}