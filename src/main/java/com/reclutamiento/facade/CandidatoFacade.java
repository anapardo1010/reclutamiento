package com.reclutamiento.facade;

import com.reclutamiento.entity.Candidato;
import com.reclutamiento.repositorio.CandidatoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Fachada (Facade) para operaciones de persistencia sobre la entidad Candidato.
 *
 * Propósito y comportamiento:
 * - La facade envuelve al repositorio (`CandidatoRepositorio`) y centraliza llamadas a la
 *   persistencia (consultas, guardados, flush, borrados). Esto facilita la reutilización y
 *   mantiene la capa de servicios desacoplada del detalle de acceso a datos.
 *
 * Regla arquitectónica aplicada en este proyecto (muy importante para el ejercicio):
 * - Una `Facade` puede llamar únicamente al `Repository` (o a otras infraestructuras relacionadas
 *   con persistencia).
 * - Un `Service` deberá llamar únicamente a su propia `Facade` y, si es necesario, a otros `Service`s.
 *   Un `Service` nunca debe llamar directamente a otro `Facade` ni directamente a un `Repository`.
 *
 * Razones para la regla:
 * 1. Separación de responsabilidades: el `Repository` conoce la persistencia, la `Facade` adapta y
 *    compone operaciones de persistencia, y el `Service` implementa la lógica de negocio.
 * 2. Testabilidad: al mockear la `Facade` en las pruebas del `Service` se aísla la lógica de negocio
 *    sin depender de la BD ni del repositorio.
 * 3. Mantenibilidad: evita dependencias cruzadas entre servicios y facades, y permite cambiar la
 *    implementación de persistencia sin afectar la lógica de negocio.
 *
 * Ejemplo de flujo recomendado:
 * Controller -> Service -> (su) Facade -> Repository -> BD
 * Si un Service necesita coordinación con otro Service, la llamada debe ser Service -> Service.
 */
@Component
@RequiredArgsConstructor
public class CandidatoFacade {
    // Se usa inyección por constructor (Lombok @RequiredArgsConstructor) en lugar de @Autowired
    private final CandidatoRepositorio candidatoRepositorio;

    @Transactional(readOnly = true)
    public Optional<Candidato> findById(Long id) {
        return candidatoRepositorio.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Candidato> findByEmail(String email) {
        return Optional.ofNullable(candidatoRepositorio.findByEmail(email));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return candidatoRepositorio.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Candidato> findAllActive() {
        return candidatoRepositorio.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Candidato> findAll() {
        return candidatoRepositorio.findAll();
    }

    @Transactional
    public Candidato save(Candidato candidato) {
        return candidatoRepositorio.save(candidato);
    }

    // Método para guardar cambios parciales (por ejemplo activar/desactivar)
    @Transactional
    public Candidato saveAndFlush(Candidato candidato) {
        Candidato saved = candidatoRepositorio.save(candidato);
        candidatoRepositorio.flush();
        return saved;
    }

    @Transactional
    public void delete(Candidato candidato) {
        candidatoRepositorio.delete(candidato);
    }

    /**
     * Envuelve la consulta JPQL findByEmailIgnoreCase definida en el repositorio.
     * Devuelve Optional para un manejo seguro de ausencia.
     */
    @Transactional(readOnly = true)
    public Optional<Candidato> findByEmailIgnoreCase(String email) {
        return candidatoRepositorio.findByEmailIgnoreCase(email);
    }

    /**
     * Envuelve la consulta nativa searchByNameLike definida en el repositorio.
     * Busca candidatos cuyo nombre contenga el fragmento (case-insensitive en Postgres con ILIKE).
     */
    @Transactional(readOnly = true)
    public List<Candidato> searchByNameLike(String fragment) {
        return candidatoRepositorio.searchByNameLike(fragment);
    }
}
