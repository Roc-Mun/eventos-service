package com.food.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    @Test
    @DisplayName("Debe crear evento con constructor vacío y setters")
    void debeCrearEventoConConstructorVacioYSetters() {

        Evento evento = new Evento();

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        evento.setIdEvento(1L);
        evento.setNombre("Festival Gastronómico");
        evento.setOrganizacionResponsable("Food Fest Chile");
        evento.setFechaInicio(inicio);
        evento.setFechaFin(fin);
        evento.setUbicacion("Parque O'Higgins");
        evento.setEstado("publicado");
        evento.setIdOrganizador(10L);

        assertEquals(1L, evento.getIdEvento());
        assertEquals("Festival Gastronómico", evento.getNombre());
        assertEquals("Food Fest Chile", evento.getOrganizacionResponsable());
        assertEquals(inicio, evento.getFechaInicio());
        assertEquals(fin, evento.getFechaFin());
        assertEquals("Parque O'Higgins", evento.getUbicacion());
        assertEquals("publicado", evento.getEstado());
        assertEquals(10L, evento.getIdOrganizador());
    }

    @Test
    @DisplayName("Debe crear evento con constructor completo")
    void debeCrearEventoConConstructorCompleto() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento = new Evento(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                inicio,
                fin,
                "Parque O'Higgins",
                "publicado",
                10L
        );

        assertEquals(1L, evento.getIdEvento());
        assertEquals("Festival Gastronómico", evento.getNombre());
        assertEquals("Food Fest Chile", evento.getOrganizacionResponsable());
        assertEquals(inicio, evento.getFechaInicio());
        assertEquals(fin, evento.getFechaFin());
        assertEquals("Parque O'Higgins", evento.getUbicacion());
        assertEquals("publicado", evento.getEstado());
        assertEquals(10L, evento.getIdOrganizador());
    }

    @Test
    @DisplayName("Debe validar equals y hashCode")
    void debeValidarEqualsYHashCode() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        Evento evento1 = new Evento(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                inicio,
                fin,
                "Parque O'Higgins",
                "publicado",
                10L
        );

        Evento evento2 = new Evento(
                1L,
                "Festival Gastronómico",
                "Food Fest Chile",
                inicio,
                fin,
                "Parque O'Higgins",
                "publicado",
                10L
        );

        assertEquals(evento1, evento2);
        assertEquals(evento1.hashCode(), evento2.hashCode());
    }
}