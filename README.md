# Evento-Service

## Descripción

Microservicio encargado de administrar eventos gastronómicos dentro de ReadyStand, incluyendo creación, publicación, inicio y finalización de eventos.

## Funcionalidades

- Crear eventos

- Listar eventos

- Buscar evento por ID

- Actualizar evento

- Publicar evento

- Iniciar evento

- Finalizar evento

- Consultar eventos disponibles

- Comunicación con microservicio de usuarios

## Tecnologías utilizadas

- Java 21

- Spring Boot

- Spring Data JPA

- Spring Validation

- MySQL

- Maven

- Docker

- Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Endpoints principales

-Obtener eventos

GET /api/v2/eventos

-Obtener evento por ID

GET /api/v2/eventos/{id}

-Crear evento

POST /api/v2/eventos

-Actualizar evento

PUT /api/v2/eventos/{id}

-Publicar evento

PATCH /api/v2/eventos/{id}/publicar

-Iniciar evento

PATCH /api/v2/eventos/{id}/iniciar

-Finalizar evento

PATCH /api/v2/eventos/{id}/finalizar

## Validaciones

-Validación de estados del evento

-Validación de fechas

-Validación de organizador existente

-Manejo global de errores con Bean Validation
