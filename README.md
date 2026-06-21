# Evento-Service

## Descripción

Microservicio encargado de administrar eventos gastronómicos dentro de ReadyStand, incluyendo creación, publicación, inicio y finalización de eventos.

## Funcionalidades

* Crear eventos
* Listar eventos
* Buscar evento por ID
* Actualizar evento
* Publicar evento
* Iniciar evento
* Finalizar evento
* Consultar eventos disponibles
* Comunicación con microservicio de usuarios

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Validation
* Spring Cloud OpenFeign
* Springdoc OpenAPI (Swagger)
* MySQL
* H2 Database (Testing)
* JUnit 5
* Mockito
* Maven
* Docker
* Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Ejecución de pruebas

```bash
mvn test
```

## Swagger

Disponible en:

```text
http://localhost:8082/doc/swagger-ui.html
```

## Endpoints principales

### Obtener eventos

GET /api/v3/eventos

### Obtener evento por ID

GET /api/v3/eventos/{id}

### Crear evento

POST /api/v3/eventos

### Actualizar evento

PUT /api/v3/eventos/{id}

### Publicar evento

PUT /api/v3/eventos/{id}/publicar

### Iniciar evento

PUT /api/v3/eventos/{id}/iniciar

### Finalizar evento

PUT /api/v3/eventos/{id}/finalizar

### Eventos disponibles

GET /api/v3/eventos/disponibles

## Testing

El proyecto incluye pruebas unitarias para las capas:

* Modelo
* Servicio
* Repositorio
* Controlador

Todas las pruebas deben finalizar con BUILD SUCCESS.

## Validaciones

* Validación de estados del evento
* Validación de fechas
* Validación de organizador existente
* Manejo global de errores con Bean Validation
