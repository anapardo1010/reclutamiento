package com.reclutamiento.modelo;

import com.reclutamiento.entity.Candidato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * Modelo de entrada para la creación de un nuevo Candidato.
 *
 * Propósito:
 * - Definir el contrato mínimo de datos que el cliente debe enviar para crear un candidato.
 * - Aplicar validaciones de entrada (ej. @NotBlank, @Email) antes de mapear a la entidad y persistir.
 * - Evitar que campos de la entidad controlados por la persistencia (id, createdAt, updatedAt, activo)
 *   sean recibidos o manipulados directamente por el cliente.
 *
 * Uso de la función de mapeo `FN_MODEL_TO_ENTITY`:
 * - Convierte un `CandidatoCreateModel` (datos de entrada validados) en una entidad `Candidato`
 *   lista para persistir. Centralizar este mapeo evita duplicación en servicios y controladores.
 * - El `Service` invoca esta función para obtener la entidad, y luego llama a la `Facade` para guardar.
 *
 * Ventajas de separar CreateModel y entidad:
 * - Seguridad: el cliente no puede establecer campos de auditoría o identificadores.
 * - Claridad: se documentan claramente los campos obligatorios y su formato.
 * - Validación temprana: errores de formato se detectan antes de llegar a la capa de persistencia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo para crear un nuevo candidato")
public class CandidatoCreateModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre del candidato", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juan")
    @NotBlank(message = "El nombre es requerido")
    private String name;

    @Schema(description = "Apellido del candidato", requiredMode = Schema.RequiredMode.REQUIRED, example = "Pérez")
    @NotBlank(message = "El apellido es requerido")
    private String lastName;

    @Schema(description = "Correo electrónico del candidato", requiredMode = Schema.RequiredMode.REQUIRED, example = "juan.perez@email.com")
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    /**
     * Función de mapeo: convierte el modelo de creación en la entidad Candidato.
     *
     * Uso típico:
     * var entity = CandidatoCreateModel.FN_MODEL_TO_ENTITY.apply(createModel);
     * candidatoFacade.save(entity);
     */
    public static final Function<CandidatoCreateModel, Candidato> FN_MODEL_TO_ENTITY =
            model -> Objects.isNull(model) ? null : Candidato.builder()
                    .name(model.getName())
                    .lastName(model.getLastName())
                    .email(model.getEmail())
                    .build();
}
