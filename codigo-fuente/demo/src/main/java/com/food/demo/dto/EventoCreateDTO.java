package com.food.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoCreateDTO {

    @NotBlank(message = "El nombre del evento es obligatorio")
    private String nombre;

    @NotBlank(message = "La organización responsable es obligatoria")
    private String organizacionResponsable;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotNull(message = "El id del organizador es obligatorio")
    @Positive(message = "El id del organizador debe ser mayor a cero")
    private Long idOrganizador;
}