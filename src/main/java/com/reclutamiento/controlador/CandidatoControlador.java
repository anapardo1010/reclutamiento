package com.reclutamiento.controlador;

import com.reclutamiento.modelo.CandidatoCreateModel;
import com.reclutamiento.modelo.CandidatoModel;
import com.reclutamiento.modelo.ResponseModel;
import com.reclutamiento.servicio.CandidatoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidatos") //url
@RequiredArgsConstructor
@Tag(name = "Candidatos", description = "API para gestionar los candidatos")
public class CandidatoControlador {

    private final CandidatoServicio candidatoServicio;

    @GetMapping
    @Operation(summary = "Obtener todos los candidatos", description = "Retorna la lista completa de candidatos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay candidatos registrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<List<CandidatoModel>>> obtenerTodos() {
        return ResponseEntity.ok(candidatoServicio.obtenerTodos());
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener candidatos activos", description = "Retorna la lista de candidatos que están activos") // Descripción en Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatos activos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay candidatos activos"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<List<CandidatoModel>>> obtenerActivos() {
        return ResponseEntity.ok(candidatoServicio.obtenerActivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar candidato por ID", description = "Retorna un candidato según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<CandidatoModel>> obtenerPorId(
            @Parameter(description = "ID del candidato", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(candidatoServicio.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo candidato", description = "Registra un nuevo candidato en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un candidato con ese correo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<CandidatoModel>> crear(
            @Parameter(description = "Datos del candidato a crear", required = true)
            @Valid @RequestBody CandidatoCreateModel createModel) {
        return new ResponseEntity<>(candidatoServicio.guardar(createModel), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/activo")
    @Operation(summary = "Activar o desactivar un candidato", description = "Cambia el estado activo de un candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<CandidatoModel>> actualizarActivo(
            @Parameter(description = "ID del candidato", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del candidato", required = true)
            @RequestBody Boolean activo) {
        return ResponseEntity.ok(candidatoServicio.actualizarActivo(id, activo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un candidato", description = "Elimina un candidato del sistema") // Descripción en Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<Void>> eliminar(
            @Parameter(description = "ID del candidato", required = true, example = "1") // Descripción del parámetro
            @PathVariable Long id) {
        return ResponseEntity.ok(candidatoServicio.eliminar(id));
    }
}
