package com.reclutamiento.servicio;

import com.reclutamiento.exception.ReclutamientoBusinessException;
import com.reclutamiento.exception.ReclutamientoNotFoundException;
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

    public ResponseModel<List<CandidatoModel>> obtenerTodos() { // Esta línea declara un método público llamado obtenerTodos que no recibe parámetros y devuelve un objeto ResponseModel que contiene una lista de CandidatoModel. Este método se usa para obtener todos los candidatos.
        var candidatos = candidatoRepositorio.findAll() // Esta línea declara una variable llamada candidatos y le asigna el resultado de llamar al método findAll() del repositorio de candidatos, que obtiene todos los candidatos de la base de datos.
                .stream() // Esta línea convierte la lista de candidatos en un stream, que es una forma de procesar datos de manera eficiente, especialmente cuando hay muchos datos.
                .map(CandidatoModel.FN_ENTITY_TO_MODEL) // Esta línea aplica una transformación a cada candidato en el stream, convirtiendo cada entidad Candidato (que es el objeto de la base de datos) en un CandidatoModel (que es el objeto que se envía al cliente), usando una función predefinida.
                .toList(); // Esta línea convierte el stream de vuelta en una lista de CandidatoModel.

        log.info("Consultando todos los candidatos. Total: {}", candidatos.size()); // Esta línea registra un mensaje de información en el log, indicando que se están consultando todos los candidatos y mostrando el total de candidatos encontrados.

        return ResponseModel.<List<CandidatoModel>>builder() // Esta línea comienza a construir un objeto ResponseModel que contendrá la lista de candidatos.
                .message("Candidatos obtenidos correctamente") // Esta línea establece el mensaje del ResponseModel como "Candidatos obtenidos correctamente".
                .data(candidatos) // Esta línea establece los datos del ResponseModel como la lista de candidatos obtenida.
                .build(); // Esta línea finaliza la construcción del ResponseModel y lo devuelve.
    }

    public ResponseModel<CandidatoModel> obtenerPorId(Long id) { // Esta línea declara un método público llamado obtenerPorId que recibe un parámetro Long llamado id y devuelve un ResponseModel con un CandidatoModel. Se usa para obtener un candidato específico por su ID.
        if (id == null || id <= 0) { // Esta línea verifica si el id es nulo o menor o igual a cero.
            throw new ReclutamientoBusinessException("El ID del candidato debe ser un número positivo"); // Si la condición es verdadera, lanza una excepción de negocio indicando que el ID debe ser positivo.
        }

        var candidato = candidatoRepositorio.findById(id) // Esta línea busca un candidato en el repositorio usando el id proporcionado.
                .orElseThrow(() -> new ReclutamientoNotFoundException("Candidato no encontrado con id: " + id)); // Si no se encuentra el candidato, lanza una excepción de no encontrado con un mensaje que incluye el id.

        log.info("Candidato encontrado con id: {}", id); // Esta línea registra en el log que se encontró el candidato con el id especificado.

        return ResponseModel.<CandidatoModel>builder() // Esta línea comienza a construir un ResponseModel para devolver el candidato encontrado.
                .message("Candidato obtenido correctamente") // Establece el mensaje como "Candidato obtenido correctamente".
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(candidato)) // Convierte la entidad Candidato a CandidatoModel y la establece como los datos del ResponseModel.
                .build(); // Finaliza la construcción y devuelve el ResponseModel.
    }

    public ResponseModel<CandidatoModel> guardar(CandidatoCreateModel createModel) { // Esta línea declara un método público llamado guardar que recibe un CandidatoCreateModel como parámetro y devuelve un ResponseModel con un CandidatoModel. Se usa para crear y guardar un nuevo candidato.
        if (createModel == null) { // Esta línea verifica si el createModel es nulo.
            throw new ReclutamientoBusinessException("Los datos del candidato no pueden ser nulos"); // Si es nulo, lanza una excepción de negocio indicando que los datos no pueden ser nulos.
        }

        if (createModel.getEmail() == null || createModel.getEmail().isBlank()) { // Esta línea verifica si el email en el createModel es nulo o está vacío.
            throw new ReclutamientoBusinessException("El email del candidato es obligatorio"); // Si es así, lanza una excepción de negocio indicando que el email es obligatorio.
        }
        if (createModel.getEmail() != null && candidatoRepositorio.existsByEmail(createModel.getEmail())) { // Esta línea verifica si el email no es nulo y ya existe un candidato con ese email en el repositorio.
            throw new ReclutamientoBusinessException("Ya existe un candidato registrado con el email: " + createModel.getEmail()); // Si es así, lanza una excepción de negocio indicando que ya existe un candidato con ese email.
        }

        var candidato = CandidatoCreateModel.FN_MODEL_TO_ENTITY.apply(createModel); // Esta línea convierte el CandidatoCreateModel en una entidad Candidato usando una función predefinida.
        var guardado = candidatoRepositorio.save(candidato); // Esta línea guarda la entidad Candidato en la base de datos usando el repositorio y asigna el resultado a la variable guardado.

        log.info("Candidato registrado con id: {}", guardado.getId()); // Esto es solo para mí. Esta línea registra en el log que el candidato fue registrado con su nuevo id.

        return ResponseModel.<CandidatoModel>builder() // Esta línea comienza a construir un ResponseModel para devolver el candidato guardado.
                .message("Candidato creado correctamente") // Establece el mensaje como "Candidato creado correctamente".
                .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(guardado)) // Convierte la entidad guardada a CandidatoModel y la establece como los datos.
                .build(); // Finaliza la construcción y devuelve el ResponseModel.
    }
}
