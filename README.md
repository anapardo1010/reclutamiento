# Reclutamiento - Spring Boot

Proyecto de reclutamiento desarrollado con Spring Boot.

## Estructura del Proyecto

```
src/main/java/com/reclutamiento/
├── ReclutamientoApplication.java       # Clase principal
├── config/                             # Configuración general
│   └── OpenApiConfig.java
├── entity/                             # Entidades JPA
│   └── Candidato.java
├── repositorio/                        # Repositorios (acceso a datos)
│   └── CandidatoRepositorio.java
├── servicio/                           # Lógica de negocio
│   └── CandidatoServicio.java
├── controlador/                        # Controladores REST
│   └── CandidatoControlador.java
├── modelo/                             # Modelos de request y respuesta
│   ├── CandidatoModel.java
│   ├── CandidatoCreateModel.java
│   └── ResponseModel.java
└── exception/                          # Manejo de errores
    ├── ReclutamientoNotFoundException.java
    ├── ReclutamientoBusinessException.java
    └── GlobalExceptionHandler.java
```

## Requisitos

- Java 17+
- Maven 3.8+

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

## Endpoints

| Método | URL                          | Descripción                      |
|--------|------------------------------|----------------------------------|
| GET    | /api/v1/candidatos           | Obtener todos los candidatos     |
| GET    | /api/v1/candidatos/{id}      | Buscar candidato por ID          |
| POST   | /api/v1/candidatos           | Crear un nuevo candidato         |

## Base de datos

Por defecto usa **H2** en memoria. Los datos **se borran cada vez que se reinicia la aplicación**.

La consola H2 está disponible en: `http://localhost:8080/h2-console`

| Campo        | Valor              |
|--------------|--------------------|
| JDBC URL     | `jdbc:h2:mem:testdb` |
| Usuario      | `sa`               |
| Contraseña   | *(vacía)*          |

## Swagger UI

La documentación interactiva de la API está disponible una vez que la aplicación esté en ejecución:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

> Ajusta el puerto si tu aplicación corre en uno diferente (por ejemplo, `8081`).

## Manejo de Errores

El proyecto cuenta con un sistema centralizado de excepciones ubicado en la carpeta `exception/`. Su objetivo es que **ningun stack trace ni mensaje interno llegue al consumidor** y que todas las respuestas de error sigan el formato estandar `ResponseModel`.

### Clases

#### `ReclutamientoNotFoundException`
Excepcion de tipo **negocio** que se lanza cuando no se encuentra un recurso en la base de datos.
- Extiende `RuntimeException`
- El `GlobalExceptionHandler` la intercepta y retorna **HTTP 404**
- **Como usarla en el servicio:**
```java
.orElseThrow(() -> new ReclutamientoNotFoundException(
    "No se encontro ningun candidato con el id " + id));
```

#### `ReclutamientoBusinessException`
Excepcion de tipo **negocio** que se lanza cuando se viola una regla de negocio, por ejemplo intentar registrar un recurso duplicado.
- Extiende `RuntimeException`
- El `GlobalExceptionHandler` la intercepta y retorna **HTTP 400**
- **Como usarla en el servicio:**
```java
throw new ReclutamientoBusinessException(
    "Ya existe un candidato registrado con el correo " + email);
```

#### `GlobalExceptionHandler`
Clase anotada con `@RestControllerAdvice` que intercepta de forma global todas las excepciones lanzadas en cualquier parte del proyecto. Evita que Spring retorne su respuesta de error por defecto (con stack trace expuesto).

Maneja los siguientes casos:

| Excepcion | HTTP Status | Cuando ocurre |
|-----------|-------------|---------------|
| `ReclutamientoNotFoundException` | `404 Not Found` | Recurso no encontrado |
| `ReclutamientoBusinessException` | `400 Bad Request` | Regla de negocio violada |
| `MethodArgumentNotValidException` | `400 Bad Request` | Falla una validacion de campo (`@NotBlank`, `@Email`, etc.) |
| `Exception` (generica) | `500 Internal Server Error` | Cualquier error no controlado |

### Flujo de un error

```
Controlador → Servicio → lanza excepcion
                              ↓
                   GlobalExceptionHandler
                              ↓
                   ResponseModel con mensaje claro
                              ↓
                      Consumidor de la API
```

### Ejemplo de respuesta de error

```json
{
  "message": "No se encontro ningun candidato con el id 99",
  "traceId": "a3807e9f2ec0d8ad",
  "data": null
}
```
