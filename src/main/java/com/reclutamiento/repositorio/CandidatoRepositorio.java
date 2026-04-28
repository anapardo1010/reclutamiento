package com.reclutamiento.repositorio;

import com.reclutamiento.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Candidato.
 *
 * Teoría breve:
 * - En aplicaciones Java con Spring Data JPA, un "Repository" es la abstracción que actúa como DAO
 *   (Data Access Object). Se encarga de la comunicación con la base de datos mediante JPA/Hibernate.
 * - Define operaciones CRUD básicas heredadas de JpaRepository y permite declarar consultas derivadas
 *   (naming conventions) o consultas explícitas con @Query (JPQL o SQL nativo).
 * - Relación con POO: el repository representa la capa de persistencia (separación de responsabilidades).
 *   Las entidades son objetos (modelos) y el repository opera sobre esas entidades; los servicios usan
 *   el repository para implementar la lógica de negocio.
 */
@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {

    /**
     * Comprueba si existe un candidato con el email dado.
     */
    boolean existsByEmail(String email);

    /**
     * Devuelve solo los candidatos activos.
     */
    List<Candidato> findByActivoTrue(); // Método para obtener solo candidatos activos

    /**
     * Busca un candidato por su correo (derivado por nombre de método).
     */
    Candidato findByEmail(String email); // Método para buscar un candidato por su correo electrónico

    /**
     * Ejemplo de consulta JPQL con @Query: búsqueda por email ignorando mayúsculas/minúsculas.
     * Retorna Optional para evitar nulls y facilitar manejo de ausencia.
     */
    @Query("SELECT c FROM Candidato c WHERE LOWER(c.email) = LOWER(:email)")
    Optional<Candidato> findByEmailIgnoreCase(@Param("email") String email);

    /**
     * Ejemplo de native query (Postgres) para buscar por fragmento de nombre (case-insensitive).
     * Útil como ejemplo didáctico; nativeQuery=true ejecuta SQL directamente contra la tabla.
     */
    @Query(value = "SELECT * FROM candidatos WHERE nombre ILIKE %:name%", nativeQuery = true)
    List<Candidato> searchByNameLike(@Param("name") String name);
}
