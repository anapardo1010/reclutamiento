package com.reclutamiento.modelo;

import com.reclutamiento.entity.Candidato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo para crear un nuevo candidato")
public class CandidatoCreateModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre del candidato", required = true, example = "Juan")
    @NotBlank(message = "El nombre es requerido")
    private String name;

    @Schema(description = "Apellido del candidato", required = true, example = "Pérez")
    @NotBlank(message = "El apellido es requerido")
    private String lastName;

    @Schema(description = "Correo electrónico del candidato", required = true, example = "juan.perez@email.com")
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    public static final Function<CandidatoCreateModel, Candidato> FN_MODEL_TO_ENTITY =
            model -> Objects.isNull(model) ? null : Candidato.builder()
                    .name(model.getName())
                    .lastName(model.getLastName())
                    .email(model.getEmail())
                    .build();
}

