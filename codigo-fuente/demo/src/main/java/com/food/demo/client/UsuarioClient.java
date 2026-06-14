package com.food.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.food.demo.dto.UsuarioDTO;

@FeignClient(
        name = "usuario-client",
        url = "${usuario.service.url}"
)
public interface UsuarioClient {

    @GetMapping("/api/v3/usuarios/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}