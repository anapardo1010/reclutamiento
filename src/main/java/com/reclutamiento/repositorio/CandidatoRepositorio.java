package com.reclutamiento.repositorio;

import com.reclutamiento.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {

    public boolean existsByEmail(String email);

    public List<Candidato> findByActivoTrue(); // Método para obtener solo candidatos activos
}
