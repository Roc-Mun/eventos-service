package com.food.demo.repository;

import com.food.demo.model.Evento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventoRepositoryTest {

    @Autowired
    private EventoRepository repository;

    @Test
    @DisplayName("Debe guardar evento correctamente")
    void debeGuardarEventoCorrectamente() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento = Evento.builder()
                .nombre("Festival Gastronómico")
                .organizacionResponsable("Food Fest Chile")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion("Parque O'Higgins")
                .estado("borrador")
                .idOrganizador(1L)
                .build();

        Evento guardado = repository.save(evento);

        assertNotNull(guardado.getIdEvento());
        assertEquals("Festival Gastronómico", guardado.getNombre());
    }

    @Test
    @DisplayName("Debe buscar evento por id")
    void debeBuscarEventoPorId() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento = Evento.builder()
                .nombre("Expo Food")
                .organizacionResponsable("Food Fest Chile")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion("Santiago")
                .estado("publicado")
                .idOrganizador(1L)
                .build();

        Evento guardado = repository.save(evento);

        Optional<Evento> encontrado =
                repository.findById(guardado.getIdEvento());

        assertTrue(encontrado.isPresent());
        assertEquals("Expo Food", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar todos los eventos")
    void debeListarTodosLosEventos() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento1 = Evento.builder()
                .nombre("Evento 1")
                .organizacionResponsable("Org 1")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion("Lugar 1")
                .estado("borrador")
                .idOrganizador(1L)
                .build();

        Evento evento2 = Evento.builder()
                .nombre("Evento 2")
                .organizacionResponsable("Org 2")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion("Lugar 2")
                .estado("publicado")
                .idOrganizador(2L)
                .build();

        repository.save(evento1);
        repository.save(evento2);

        List<Evento> eventos = repository.findAll();

        assertEquals(2, eventos.size());
    }

    @Test
    @DisplayName("Debe buscar eventos por estado")
    void debeBuscarEventosPorEstado() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento = Evento.builder()
                .nombre("Evento Publicado")
                .organizacionResponsable("Food Fest Chile")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion("Santiago")
                .estado("publicado")
                .idOrganizador(1L)
                .build();

        repository.save(evento);

        List<Evento> eventos =
                repository.findByEstado("publicado");

        assertEquals(1, eventos.size());
        assertEquals("publicado", eventos.get(0).getEstado());
    }
}