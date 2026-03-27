package com.reclutamiento.servicio;

import com.reclutamiento.modelo.CandidatoCreateModel;
import com.reclutamiento.modelo.CandidatoModel;
import com.reclutamiento.modelo.ResponseModel;
import com.reclutamiento.repositorio.CandidatoRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidatoServicio {

    private final CandidatoRepositorio candidatoRepositorio;

    public ResponseModel<List<CandidatoModel>> obtenerTodos() {
        var candidatos = candidatoRepositorio.findAll()
                .stream()
                .map(CandidatoModel.FN_ENTITY_TO_MODEL)
                .toList();

        log.info("Consultando todos los candidatos. Total: {}", candidatos.size());

        return ResponseModel.<List<CandidatoModel>>builder()
                .message("Candidatos obtenidos correctamente")
                .data(candidatos)
                .build();
    }

    public ResponseModel<CandidatoModel> obtenerPorId(Long id) {
        var candidato = candidatoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + id));

        log.info("Candidato encontrado con id: {}", id);

        return ResponseModel.<CandidatoModel>builder()
                .message("Candidato obtenido correctamente")
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(candidato))
                .build();
    }

    public ResponseModel<CandidatoModel> guardar(CandidatoCreateModel createModel) {
        var candidato = CandidatoCreateModel.FN_MODEL_TO_ENTITY.apply(createModel);
        var guardado = candidatoRepositorio.save(candidato);

        log.info("Candidato registrado con id: {}", guardado.getId());

        return ResponseModel.<CandidatoModel>builder()
                .message("Candidato creado correctamente")
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(guardado))
                .build();
    }
}
