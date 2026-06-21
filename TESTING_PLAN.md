
## `Entorno-Modulo2/TESTING_PLAN.md`

```markdown
# TESTING_PLAN.md — Microservicio Eventos

## Pruebas Unitarias y Cobertura de Reglas de Negocio

Este documento resume las reglas de negocio críticas del microservicio de Eventos y el estado actual de cobertura mediante pruebas unitarias.

El microservicio fue probado en cuatro capas principales: modelo, servicio, repositorio y controlador.

---

## Reglas de Negocio Críticas

1. Solo un usuario con rol `organizador` puede crear eventos.
2. Al crear un evento, este debe quedar inicialmente en estado `borrador`.
3. No se debe permitir operar sobre eventos inexistentes.
4. Un evento puede cambiar de estado durante su ciclo de vida: `borrador`, `publicado`, `iniciado` y `finalizado`.
5. Solo los eventos en estado `publicado` deben aparecer como disponibles para consulta pública.

---

## Cobertura Actual

| Regla / Capa | Estado | Casos Cubiertos |
|---|---|---|
| Modelo Evento | ✅ Cubierta | Constructor vacío, constructor completo, getters/setters, equals y hashCode |
| Crear evento correctamente | ✅ Cubierta | Creación exitosa con usuario organizador |
| Estado inicial del evento | ✅ Cubierta | Evento creado con estado `borrador` |
| Usuario sin rol organizador | ✅ Cubierta | Lanza excepción si el usuario no tiene permisos |
| Evento inexistente | ✅ Cubierta | Lanza excepción al buscar evento no existente |
| Publicar evento | ✅ Cubierta | Cambio de estado a `publicado` |
| Repositorio Evento | ✅ Cubierta | `save`, `findById`, `findAll`, `findByEstado` |
| Controlador Evento | ✅ Cubierta | Respuestas HTTP 200 y 201 mediante MockMvc |

---

## Clases de Test Implementadas

| Capa | Clase de Test | Herramientas |
|---|---|---|
| Modelo | `EventoTest` | JUnit 5 |
| Servicio | `EventoServiceTest` | JUnit 5, Mockito, `@Mock`, `@InjectMocks` |
| Repositorio | `EventoRepositoryTest` | `@DataJpaTest`, H2 en memoria |
| Controlador | `EventoControllerTest` | MockMvc |

---

## Reflexión y Deuda Técnica
Como mejora futura, se podrían agregar más pruebas para validar transiciones inválidas de estado (por ejemplo, iniciar o finalizar eventos en estados no permitidos) y ampliar los escenarios de integración con el microservicio de Usuarios.

---

## Ejecución de Pruebas

Comando utilizado:

```bash
mvn test

