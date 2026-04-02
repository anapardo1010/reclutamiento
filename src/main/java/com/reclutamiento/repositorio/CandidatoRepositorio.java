package com.reclutamiento.repositorio;

import com.reclutamiento.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {

    public boolean existsByEmail(String email);
}
