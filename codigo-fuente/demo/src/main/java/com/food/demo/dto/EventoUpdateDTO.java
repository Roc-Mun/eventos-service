package com.food.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoUpdateDTO {
    private String nombre;
    private String organizacionResponsable;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String ubicacion;
    private Long idOrganizador;
}
