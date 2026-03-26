# Reclutamiento - Spring Boot

Proyecto de reclutamiento desarrollado con Spring Boot.

## Estructura del Proyecto

```
src/main/java/com/reclutamiento/
├── ReclutamientoApplication.java       # Clase principal
├── entity/                             # Entidades JPA
│   └── Candidato.java
├── repositorio/                        # Repositorios (acceso a datos)
│   └── CandidatoRepositorio.java
├── servicio/                           # Lógica de negocio
│   └── CandidatoServicio.java
├── controlador/                        # Controladores REST
│   └── CandidatoControlador.java
└── modelo/                             # DTOs y modelos de respuesta
    ├── CandidatoDTO.java
    └── ApiRespuesta.java
```

## Requisitos

- Java 17+
- Maven 3.8+

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

## Endpoints

| Método | URL                              | Descripción               |
|--------|----------------------------------|---------------------------|
| GET    | /api/candidatos                  | Obtener todos             |
| GET    | /api/candidatos/{id}             | Obtener por ID            |
| POST   | /api/candidatos                  | Crear candidato           |
| PUT    | /api/candidatos/{id}             | Actualizar candidato      |
| DELETE | /api/candidatos/{id}             | Eliminar candidato        |
| GET    | /api/candidatos/estado/{estado}  | Filtrar por estado        |
| GET    | /api/candidatos/puesto/{puesto}  | Filtrar por puesto        |

## Base de datos

Por defecto usa **H2** en memoria. La consola H2 está disponible en `/h2-console`.

## Swagger UI

Si agregaste la dependencia de OpenAPI (springdoc) y ejecutas la aplicación, la documentación Swagger estará disponible en:

- Interfaz web (Swagger UI): http://localhost:8080/swagger-ui/index.html  (también suele funcionar `/swagger-ui.html`)
- Documentación OpenAPI (JSON): http://localhost:8080/v3/api-docs

Nota: ajusta el puerto si tu aplicación corre en otro (por ejemplo, `8081`). Asegúrate de que la dependencia `org.springdoc:springdoc-openapi-starter-webmvc-ui` esté en el `pom.xml` y que la aplicación esté en ejecución para ver la UI.
