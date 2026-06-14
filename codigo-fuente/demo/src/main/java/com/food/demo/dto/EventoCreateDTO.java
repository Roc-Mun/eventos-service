package com.food.demo.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear un evento")
public class EventoCreateDTO {

    @Schema(description = "Nombre del evento", example = "Festival Gastronómico 2026")
    @NotBlank(message = "El nombre del evento es obligatorio")
    private String nombre;

    @Schema(description = "Organización responsable", example = "Food Fest Chile")
    @NotBlank(message = "La organización responsable es obligatoria")
    private String organizacionResponsable;

    @Schema(description = "Fecha de inicio del evento", example = "2026-08-15T10:00:00")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha de finalización del evento", example = "2026-08-15T20:00:00")
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @Schema(description = "Ubicación del evento", example = "Parque O'Higgins")
    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @Schema(description = "ID del usuario organizador", example = "1")
    @NotNull(message = "El id del organizador es obligatorio")
    @Positive(message = "El id del organizador debe ser mayor a cero")
    private Long idOrganizador;
}