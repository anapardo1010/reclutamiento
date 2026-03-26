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

