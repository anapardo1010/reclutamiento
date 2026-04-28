package com.reclutamiento.servicio;

import com.reclutamiento.exception.ReclutamientoBusinessException;
import com.reclutamiento.exception.ReclutamientoNotFoundException;
import com.reclutamiento.facade.CandidatoFacade;
import com.reclutamiento.modelo.CandidatoCreateModel;
import com.reclutamiento.modelo.CandidatoModel;
import com.reclutamiento.modelo.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de negocio para gestionar candidatos.
 *
 * Teoría breve:
 * - Java es un lenguaje orientado a objetos donde las clases modelan entidades y comportamientos.
 * - En aplicaciones Spring Boot, los servicios (annotados con @Service) contienen la lógica de negocio
 *   y actúan como puente entre la capa web (controladores) y la persistencia (repositorios/facades).
 * - Relación con POO: los servicios encapsulan comportamientos, usan abstracción e inyección de dependencias
 *   para favorecer la separación de responsabilidades y facilitar pruebas unitarias.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CandidatoServicio {

    private final CandidatoFacade candidatoFacade;

    /**
     * Obtener todos los candidatos.
     *
     * @return ResponseModel que contiene la lista de todos los CandidatoModel.
     */
    public ResponseModel<List<CandidatoModel>> obtenerTodos() {
        var candidatos = candidatoFacade.findAll()
                .stream()
                .map(CandidatoModel.FN_ENTITY_TO_MODEL)
                .toList();

        log.info("Consultando todos los candidatos. Total: {}", candidatos.size());

        return ResponseModel.<List<CandidatoModel>>builder()
                .message("Candidatos obtenidos correctamente")
                .data(candidatos)
                .build();
    }

    /**
     * Obtener solo los candidatos activos.
     *
     * @return ResponseModel con la lista de CandidatoModel activos.
     */
    public ResponseModel<List<CandidatoModel>> obtenerActivos() {
        var candidatos = candidatoFacade.findAllActive()
                .stream()
                .map(CandidatoModel.FN_ENTITY_TO_MODEL)
                .toList();

        log.info("Consultando candidatos activos. Total: {}", candidatos.size());

        return ResponseModel.<List<CandidatoModel>>builder()
                .message("Candidatos activos obtenidos correctamente")
                .data(candidatos)
                .build();
    }

    /**
     * Obtener un candidato por su identificador.
     *
     * @param id Identificador del candidato.
     * @return ResponseModel con el CandidatoModel correspondiente.
     * @throws ReclutamientoBusinessException si el id es inválido.
     * @throws ReclutamientoNotFoundException si no existe el candidato.
     */
    public ResponseModel<CandidatoModel> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new ReclutamientoBusinessException("El ID del candidato debe ser un número positivo");
        }

        var candidato = candidatoFacade.findById(id)
                .orElseThrow(() -> new ReclutamientoNotFoundException("Candidato no encontrado con id: " + id));

        log.info("Candidato encontrado con id: {}", id);

        return ResponseModel.<CandidatoModel>builder()
                .message("Candidato obtenido correctamente")
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(candidato))
                .build();
    }

    /**
     * Crear y guardar un nuevo candidato.
     *
     * @param createModel Modelo con los datos necesarios para la creación.
     * @return ResponseModel con el CandidatoModel creado.
     * @throws ReclutamientoBusinessException si los datos son inválidos o el email ya existe.
     */
    public ResponseModel<CandidatoModel> guardar(CandidatoCreateModel createModel) {
        if (createModel == null) {
            throw new ReclutamientoBusinessException("Los datos del candidato no pueden ser nulos");
        }

        if (createModel.getEmail() == null || createModel.getEmail().isBlank()) {
            throw new ReclutamientoBusinessException("El email del candidato es obligatorio");
        }

        if (candidatoFacade.existsByEmail(createModel.getEmail())) {
            throw new ReclutamientoBusinessException("Ya existe un candidato registrado con el email: " + createModel.getEmail());
        }

        var candidato = CandidatoCreateModel.FN_MODEL_TO_ENTITY.apply(createModel);
        candidato.setActivo(true);
        var guardado = candidatoFacade.save(candidato);

        log.info("Candidato registrado con id: {}", guardado.getId());

        return ResponseModel.<CandidatoModel>builder()
                .message("Candidato creado correctamente")
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(guardado))
                .build();
    }

    /**
     * Activar o desactivar un candidato y actualizar la fecha de modificación.
     *
     * @param id Identificador del candidato.
     * @param activo Nuevo estado activo/inactivo.
     * @return ResponseModel con el CandidatoModel actualizado.
     * @throws ReclutamientoBusinessException si el id es inválido.
     * @throws ReclutamientoNotFoundException si no existe el candidato.
     */
    public ResponseModel<CandidatoModel> actualizarActivo(Long id, Boolean activo) {
        if (id == null || id <= 0) {
            throw new ReclutamientoBusinessException("El ID del candidato debe ser un número positivo");
        }

        var candidato = candidatoFacade.findById(id)
                .orElseThrow(() -> new ReclutamientoNotFoundException("Candidato no encontrado con id: " + id));

        candidato.setActivo(activo);
        candidato.setUpdatedAt(LocalDateTime.now());
        var guardado = candidatoFacade.saveAndFlush(candidato);

        log.info("Candidato con id: {} actualizado a activo: {}", id, activo);

        return ResponseModel.<CandidatoModel>builder()
                .message("Estado del candidato actualizado correctamente")
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(guardado))
                .build();
    }

    /**
     * Eliminar un candidato por su id.
     *
     * @param id Identificador del candidato a eliminar.
     * @return ResponseModel sin contenido.
     * @throws ReclutamientoBusinessException si el id es inválido.
     * @throws ReclutamientoNotFoundException si no existe el candidato.
     */
    public ResponseModel<Void> eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new ReclutamientoBusinessException("El ID del candidato debe ser un número positivo");
        }

        var candidato = candidatoFacade.findById(id)
                .orElseThrow(() -> new ReclutamientoNotFoundException("Candidato no encontrado con id: " + id));

        candidatoFacade.delete(candidato);

        log.info("Candidato con id: {} ha sido eliminado", id);

        return ResponseModel.<Void>builder()
                .message("Candidato eliminado correctamente")
                .data(null)
                .build();
    }
}
