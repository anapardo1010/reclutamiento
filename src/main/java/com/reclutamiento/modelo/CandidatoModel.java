package com.reclutamiento.modelo;

import com.reclutamiento.entity.Candidato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

/**
 * Modelo de datos que representa la vista pública de un Candidato expuesta por la API.
 *
 * Propósito:
 * - Actuar como contrato (DTO) entre el backend y los consumidores (frontends, clientes HTTP).
 * - Contener únicamente los campos que deben exponerse en las respuestas y en la documentación (OpenAPI/Swagger).
 * - Separar la representación de la API de la entidad JPA (`com.reclutamiento.entity.Candidato`) para evitar
 *   exponer detalles de persistencia, relaciones perezosas u otros metadatos.
 *
 * Relación con Service y Controller:
 * - El `Controller` recibe/entrega `CandidatoModel` y `CandidatoCreateModel` (en las entradas y salidas) y
 *   delega la lógica al `Service`.
 * - El `Service` trabaja con estos modelos (y con las entidades internamente) para validar reglas de negocio,
 *   construir respuestas (`ResponseModel`) y llamar a la `Facade` para persistencia.
 * - Evitar el uso directo de entidades en la capa web evita efectos inesperados del EntityManager y protege la API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo para representar un candidato")
public class CandidatoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "Estado del candidato", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de última modificación")
    private LocalDateTime updatedAt;

    @Schema(description = "Identificador único del candidato", example = "1")
    private Long id;

    @Schema(description = "Nombre del candidato", example = "Juan")
    private String name;

    @Schema(description = "Apellido del candidato", example = "Pérez")
    private String lastName;

    @Schema(description = "Correo electrónico del candidato", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Teléfono del candidato", example = "5512345678")
    private String phone;

    @Schema(description = "Fecha de creación del candidato")
    private LocalDateTime createdAt;

    /**
     * Función de mapeo: convierte una entidad `Candidato` (persistencia) a `CandidatoModel` (representación de API).
     *
     * Ventajas de usar esta función estática:
     * - Centraliza la lógica de mapeo en un solo lugar y evita duplicación en controllers/servicios.
     * - Facilita pruebas unitarias y mantenimiento; si cambia la entidad, actualizas aquí el mapeo.
     * - Evita exponer la entidad directamente en la API (mejor control sobre los campos publicados).
     */
    public static final Function<Candidato, CandidatoModel> FN_ENTITY_TO_MODEL =
            entity -> Objects.isNull(entity) ? null : CandidatoModel.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .lastName(entity.getLastName())
                    .email(entity.getEmail())
                    .phone(entity.getPhone())
                    .createdAt(entity.getCreatedAt())
                    .activo(entity.getActivo())        // ← nuevo
                    .updatedAt(entity.getUpdatedAt())  // ← nuevo
                    .build();
}
