package com.food.demo.service;

import com.food.demo.client.UsuarioClient;
import com.food.demo.dto.EventoCreateDTO;
import com.food.demo.dto.EventoDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.model.Evento;
import com.food.demo.repository.EventoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private EventoService service;

    @Test
    @DisplayName("Debe crear evento correctamente")
    void debeCrearEventoCorrectamente() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        EventoCreateDTO dto = new EventoCreateDTO(
                "Festival Gastronómico",
                "Food Fest Chile",
                inicio,
                fin,
                "Parque O'Higgins",
                1L
        );

        UsuarioDTO usuario = new UsuarioDTO(
                1L,
                "Juan",
                "juan@correo.com",
                "organizador",
                "activo"
        );

        Evento eventoGuardado = Evento.builder()
                .idEvento(1L)
                .nombre(dto.getNombre())
                .organizacionResponsable(dto.getOrganizacionResponsable())
                .fechaInicio(inicio)
                .fechaFin(fin)
                .ubicacion(dto.getUbicacion())
                .estado("borrador")
                .idOrganizador(1L)
                .build();

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuario);
        when(repository.save(any(Evento.class))).thenReturn(eventoGuardado);

        EventoDTO resultado = service.crearEvento(dto);

        assertNotNull(resultado);
        assertEquals("Festival Gastronómico", resultado.getNombre());
        assertEquals("borrador", resultado.getEstado());

        verify(repository, times(1)).save(any(Evento.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando usuario no es organizador")
    void debeLanzarExcepcionCuandoUsuarioNoEsOrganizador() {

        LocalDateTime inicio = LocalDateTime.of(2026, 8, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 8, 15, 20, 0);

        EventoCreateDTO dto = new EventoCreateDTO(
                "Festival Gastronómico",
                "Food Fest Chile",
                inicio,
                fin,
                "Parque O'Higgins",
                1L
        );

        UsuarioDTO usuario = new UsuarioDTO(
                1L,
                "Pedro",
                "pedro@correo.com",
                "cliente",
                "activo"
        );

        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        assertThrows(
                EstadoInvalidoException.class,
                () -> service.crearEvento(dto)
        );
    }

    @Test
    @DisplayName("Debe publicar evento correctamente")
    void debePublicarEventoCorrectamente() {

        Evento evento = Evento.builder()
                .idEvento(1L)
                .nombre("Festival")
                .estado("borrador")
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(evento));

        when(repository.save(any(Evento.class)))
                .thenReturn(evento);

        EventoDTO resultado = service.publicarEvento(1L);

        assertEquals("publicado", resultado.getEstado());

        verify(repository).save(evento);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando evento no existe")
    void debeLanzarExcepcionCuandoEventoNoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerEventoPorId(99L)
        );
    }
}