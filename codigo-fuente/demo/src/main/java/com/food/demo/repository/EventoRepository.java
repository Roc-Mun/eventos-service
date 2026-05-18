package com.food.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.food.demo.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByEstado(String estado);
    List<Evento> findByEstadoIn(List<String> estados);
}