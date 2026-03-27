package com.reclutamiento.repositorio;

import com.reclutamiento.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {
}

