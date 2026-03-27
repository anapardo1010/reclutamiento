package com.reclutamiento.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Schema(description = "Modelo de respuesta estándar de la API")
public final class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @NonNull
    @Getter
    @Schema(description = "Mensaje descriptivo de la operación", example = "Operación realizada exitosamente")
    private String message;

    @NonNull
    @Getter
    @Schema(description = "Identificador de traza de la petición", example = "a3807e9f2ec0d8ad")
    private String traceId;

    @Nullable
    @Getter
    @Schema(description = "Datos del recurso consultado")
    private T data;

    private ResponseModel() {}

    // ========================= BUILDER ========================= //

    public static <T> ResponseModelBuilder<T> builder() {
        return new ResponseModelBuilder<>();
    }

    public static class ResponseModelBuilder<T> {
        private final ResponseModel<T> instance;

        private ResponseModelBuilder() {
            instance = new ResponseModel<>();
            instance.traceId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        }

        public ResponseModelBuilder<T> message(String message) {
            instance.message = message;
            return this;
        }

        public ResponseModelBuilder<T> data(T data) {
            instance.data = Objects.isNull(data) ? null : data;
            return this;
        }

        public ResponseModel<T> build() {
            return instance;
        }
    }
}

