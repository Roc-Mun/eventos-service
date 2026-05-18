package com.food.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    private String nombre;

    @Column(name = "organizacion_responsable")
    private String organizacionResponsable;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String ubicacion;
    private String estado;

    @Column(name = "id_organizador")
    private Long idOrganizador;
}
