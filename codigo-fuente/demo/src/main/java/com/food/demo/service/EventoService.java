package com.food.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import feign.FeignException;

import com.food.demo.client.UsuarioClient;
import com.food.demo.dto.EventoCreateDTO;
import com.food.demo.dto.EventoDTO;
import com.food.demo.dto.EventoUpdateDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.exception.ServicioNoDisponibleException;
import com.food.demo.model.Evento;
import com.food.demo.repository.EventoRepository;

@Service
public class EventoService {

    private static final Logger log = LoggerFactory.getLogger(EventoService.class);

    private final EventoRepository repository;
    private final UsuarioClient usuarioClient;

    public EventoService(EventoRepository repository, UsuarioClient usuarioClient) {
        this.repository = repository;
        this.usuarioClient = usuarioClient;
    }

    public EventoDTO crearEvento(EventoCreateDTO dto) {

        UsuarioDTO usuario;

        try {

            log.info("Consultando usuario id={}", dto.getIdOrganizador());

            usuario = usuarioClient.obtenerUsuarioPorId(dto.getIdOrganizador());

            log.info("Usuario encontrado: {}", usuario.getNombre());

        } catch (FeignException.NotFound e) {

            log.warn("Usuario id={} no existe", dto.getIdOrganizador());

            throw new RecursoNoEncontradoException("Usuario no encontrado");

        } catch (FeignException e) {

            log.error("Error al consultar servicio Usuarios: {}", e.getMessage());

            throw new ServicioNoDisponibleException("Servicio de usuarios no disponible");
        }

        if (!usuario.getRol().equalsIgnoreCase("organizador")) {
            throw new EstadoInvalidoException("El usuario no tiene permisos");
        }

        Evento evento = new Evento();

        evento.setNombre(dto.getNombre());
        evento.setOrganizacionResponsable(dto.getOrganizacionResponsable());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setUbicacion(dto.getUbicacion());
        evento.setIdOrganizador(dto.getIdOrganizador());
        evento.setEstado("borrador");

        Evento guardado = repository.save(evento);

        log.info("Evento creado exitosamente id={}", guardado.getIdEvento());

        return toDto(guardado);
    }

    public EventoDTO obtenerEventoPorId(Long id) {
        return toDto(obtenerEntidadPorId(id));
    }

    public List<EventoDTO> listarEventos() {

        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<EventoDTO> listarEventosDisponibles() {

        return repository.findAll()
                .stream()
                .filter(e -> "publicado".equalsIgnoreCase(e.getEstado()))
                .map(this::toDto)
                .toList();
    }

    public EventoDTO actualizarEvento(Long id, EventoUpdateDTO dto) {

        Evento datos = new Evento();

        datos.setNombre(dto.getNombre());
        datos.setOrganizacionResponsable(dto.getOrganizacionResponsable());
        datos.setFechaInicio(dto.getFechaInicio());
        datos.setFechaFin(dto.getFechaFin());
        datos.setUbicacion(dto.getUbicacion());
        datos.setIdOrganizador(dto.getIdOrganizador());

        return toDto(actualizarEntidad(id, datos));
    }

    public EventoDTO publicarEvento(Long id) {

        Evento evento = obtenerEntidadPorId(id);

        if (evento.getEstado().equalsIgnoreCase("publicado")) {
            throw new EstadoInvalidoException("El evento ya está publicado");
        }

        log.info("Publicando evento id={}", id);

        evento.setEstado("publicado");

        Evento actualizado = repository.save(evento);

        log.info("Evento publicado id={}", actualizado.getIdEvento());

        return toDto(actualizado);
    }

    public EventoDTO iniciarEvento(Long id) {

        Evento evento = obtenerEntidadPorId(id);

        log.info("Iniciando evento id={}", id);

        if (!evento.getEstado().equalsIgnoreCase("publicado")) {
            throw new EstadoInvalidoException("Solo eventos publicados pueden iniciarse");
        }

        evento.setEstado("iniciado");

        Evento actualizado = repository.save(evento);

        log.info("Evento iniciado id={}", actualizado.getIdEvento());

        return toDto(actualizado);
    }

    public EventoDTO finalizarEvento(Long id) {

        Evento evento = obtenerEntidadPorId(id);

        log.info("Finalizando evento id={}", id);

        if (!evento.getEstado().equalsIgnoreCase("iniciado")) {
            throw new EstadoInvalidoException("Solo eventos iniciados pueden finalizarse");
        }

        evento.setEstado("finalizado");

        Evento actualizado = repository.save(evento);

        log.info("Evento finalizado id={}", actualizado.getIdEvento());

        return toDto(actualizado);
    }

    public EventoDTO cancelarEvento(Long id) {

        Evento evento = obtenerEntidadPorId(id);

        if (evento.getEstado().equalsIgnoreCase("cancelado")) {
            throw new EstadoInvalidoException("El evento ya está cancelado");
        }

        log.info("Cancelando evento id={}", id);

        evento.setEstado("cancelado");

        Evento actualizado = repository.save(evento);

        log.info("Evento cancelado id={}", actualizado.getIdEvento());

        return toDto(actualizado);
    }

    private Evento obtenerEntidadPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Evento no encontrado"));
    }

    private Evento actualizarEntidad(Long id, Evento datos) {

        Evento existente = obtenerEntidadPorId(id);

        if (existente.getEstado().equalsIgnoreCase("publicado")) {
            throw new EstadoInvalidoException("No se puede modificar un evento publicado");
        }

        if (datos.getNombre() != null && !datos.getNombre().isBlank()) {
            existente.setNombre(datos.getNombre());
        }

        if (datos.getOrganizacionResponsable() != null &&
                !datos.getOrganizacionResponsable().isBlank()) {

            existente.setOrganizacionResponsable(
                    datos.getOrganizacionResponsable());
        }

        if (datos.getFechaInicio() != null) {
            existente.setFechaInicio(datos.getFechaInicio());
        }

        if (datos.getFechaFin() != null) {
            existente.setFechaFin(datos.getFechaFin());
        }

        if (datos.getUbicacion() != null &&
                !datos.getUbicacion().isBlank()) {

            existente.setUbicacion(datos.getUbicacion());
        }

        if (datos.getIdOrganizador() != null) {
            existente.setIdOrganizador(datos.getIdOrganizador());
        }

        return repository.save(existente);
    }

    private EventoDTO toDto(Evento e) {

        return new EventoDTO(
                e.getIdEvento(),
                e.getNombre(),
                e.getOrganizacionResponsable(),
                e.getFechaInicio(),
                e.getFechaFin(),
                e.getUbicacion(),
                e.getEstado(),
                e.getIdOrganizador()
        );
    }
}