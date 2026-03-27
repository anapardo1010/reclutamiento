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
@RequestMapping("/api/v1/candidatos")
@RequiredArgsConstructor
@Tag(name = "Candidatos", description = "API para gestionar los candidatos")
public class CandidatoControlador {

    private final CandidatoServicio candidatoServicio;

    @GetMapping
    @Operation(summary = "Obtener todos los candidatos", description = "Retorna la lista completa de candidatos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidatos obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<List<CandidatoModel>>> obtenerTodos() {
        return ResponseEntity.ok(candidatoServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar candidato por ID", description = "Retorna un candidato según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado correctamente"),
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
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseModel<CandidatoModel>> crear(
            @Parameter(description = "Datos del candidato a crear", required = true)
            @Valid @RequestBody CandidatoCreateModel createModel) {
        return new ResponseEntity<>(candidatoServicio.guardar(createModel), HttpStatus.CREATED);
    }
}
