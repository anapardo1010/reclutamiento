package com.reclutamiento.modelo;

import com.reclutamiento.entity.Candidato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo para representar un candidato")
public class CandidatoModel implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public static final Function<Candidato, CandidatoModel> FN_ENTITY_TO_MODEL =
            entity -> Objects.isNull(entity) ? null : CandidatoModel.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .lastName(entity.getLastName())
                    .email(entity.getEmail())
                    .phone(entity.getPhone())
                    .createdAt(entity.getCreatedAt())
                    .build();
}
