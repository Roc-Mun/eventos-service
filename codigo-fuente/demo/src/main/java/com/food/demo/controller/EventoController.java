package com.food.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @GetMapping
    public List<String> listar() {
        return new ArrayList<>();
    }
}