package com.reclutamiento.servicio;

import com.reclutamiento.entity.Candidato;
import com.reclutamiento.repositorio.CandidatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoServicio {

    @Autowired
    private CandidatoRepositorio candidatoRepositorio;

    public List<Candidato> obtenerTodos() {
        return candidatoRepositorio.findAll();
    }

    public Optional<Candidato> obtenerPorId(Long id) {
        return candidatoRepositorio.findById(id);
    }

    public Candidato guardar(Candidato candidato) {
        return candidatoRepositorio.save(candidato);
    }

    public Candidato actualizar(Long id, Candidato candidatoActualizado) {
        return candidatoRepositorio.findById(id)
                .map(candidato -> {
                    candidato.setNombre(candidatoActualizado.getNombre());
                    candidato.setApellido(candidatoActualizado.getApellido());
                    candidato.setEmail(candidatoActualizado.getEmail());
                    candidato.setTelefono(candidatoActualizado.getTelefono());
                    candidato.setPuesto(candidatoActualizado.getPuesto());
                    candidato.setExperiencia(candidatoActualizado.getExperiencia());
                    candidato.setEstado(candidatoActualizado.getEstado());
                    return candidatoRepositorio.save(candidato);
                })
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        candidatoRepositorio.deleteById(id);
    }

    public List<Candidato> obtenerPorEstado(String estado) {
        return candidatoRepositorio.findByEstado(estado);
    }

    public List<Candidato> obtenerPorPuesto(String puesto) {
        return candidatoRepositorio.findByPuesto(puesto);
    }
}

