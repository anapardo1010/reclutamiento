package com.reclutamiento.controlador;

import com.reclutamiento.entity.Candidato;
import com.reclutamiento.servicio.CandidatoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoControlador {

    @Autowired
    private CandidatoServicio candidatoServicio;

    @GetMapping
    public ResponseEntity<List<Candidato>> obtenerTodos() {
        List<Candidato> candidatos = candidatoServicio.obtenerTodos();
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> obtenerPorId(@PathVariable Long id) {
        return candidatoServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidato> crear(@RequestBody Candidato candidato) {
        Candidato nuevoCandidato = candidatoServicio.guardar(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCandidato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> actualizar(@PathVariable Long id, @RequestBody Candidato candidato) {
        try {
            Candidato candidatoActualizado = candidatoServicio.actualizar(id, candidato);
            return ResponseEntity.ok(candidatoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        candidatoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Candidato>> obtenerPorEstado(@PathVariable String estado) {
        List<Candidato> candidatos = candidatoServicio.obtenerPorEstado(estado);
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/puesto/{puesto}")
    public ResponseEntity<List<Candidato>> obtenerPorPuesto(@PathVariable String puesto) {
        List<Candidato> candidatos = candidatoServicio.obtenerPorPuesto(puesto);
        return ResponseEntity.ok(candidatos);
    }
}

