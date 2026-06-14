package com.food.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class EventoController {

    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventoDTO> listarEventos() {
        return service.listarEventos();
    }

    @GetMapping("/disponibles")
    public List<EventoDTO> listarDisponibles() {
        return service.listarEventosDisponibles();
    }

    @PostMapping
    public ResponseEntity<EventoDTO> crear(@Valid @RequestBody EventoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearEvento(dto));
    }

    @GetMapping("/{id}")
    public EventoDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerEventoPorId(id);
    }

    @PutMapping("/{id}")
    public EventoDTO actualizar(@PathVariable Long id, @RequestBody EventoUpdateDTO dto) {
        return service.actualizarEvento(id, dto);
    }

    @PutMapping("/{id}/publicar")
    public ResponseEntity<EventoDTO> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(service.publicarEvento(id));
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<EventoDTO> iniciarEvento(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciarEvento(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<EventoDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarEvento(id));
    }
}