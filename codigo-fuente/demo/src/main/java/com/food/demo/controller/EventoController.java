package com.food.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.demo.dto.EventoCreateDTO;
import com.food.demo.dto.EventoDTO;
import com.food.demo.dto.EventoUpdateDTO;
import com.food.demo.service.EventoService;

@RestController
@RequestMapping("/api/v3/eventos")
@Tag(name = "Eventos", description = "Operaciones relacionadas con eventos")
public class EventoController {

    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar eventos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<EventoDTO> listarEventos() {
        return service.listarEventos();
    }

    @Operation(summary = "Listar eventos disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping("/disponibles")
    public List<EventoDTO> listarDisponibles() {
        return service.listarEventosDisponibles();
    }

    @Operation(summary = "Crear evento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Estado inválido")
    })
    @PostMapping
    public ResponseEntity<EventoDTO> crear(@Valid @RequestBody EventoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearEvento(dto));
    }

    @Operation(summary = "Obtener evento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @GetMapping("/{id}")
    public EventoDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerEventoPorId(id);
    }

    @Operation(summary = "Actualizar evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "Estado inválido")
    })
    @PutMapping("/{id}")
    public EventoDTO actualizar(@PathVariable Long id, @RequestBody EventoUpdateDTO dto) {
        return service.actualizarEvento(id, dto);
    }

    @Operation(summary = "Publicar evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento publicado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "Estado inválido")
    })
    @PutMapping("/{id}/publicar")
    public ResponseEntity<EventoDTO> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(service.publicarEvento(id));
    }

    @Operation(summary = "Iniciar evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento iniciado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "Estado inválido")
    })
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<EventoDTO> iniciarEvento(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciarEvento(id));
    }

    @Operation(summary = "Finalizar evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento finalizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "Estado inválido")
    })
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<EventoDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarEvento(id));
    }
}