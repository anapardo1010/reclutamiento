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
└── modelo/                             # Modelos de request y respuesta
    ├── CandidatoModel.java
    ├── CandidatoCreateModel.java
    └── ResponseModel.java
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
