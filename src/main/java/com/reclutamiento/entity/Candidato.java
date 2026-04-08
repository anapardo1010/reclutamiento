package com.reclutamiento.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;



/**
 * Entidad que representa un candidato en el sistema.
 *
 * @author Ana Gabriela Pardo
 * @since 2026-03-26
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "candidatos",
        indexes = {
                @Index(name = "candidato_id_idx", columnList = "id"),
                @Index(name = "candidato_email_idx", columnList = "correo", unique = true),
                @Index(name = "candidato_apellido_idx", columnList = "apellido")
        })
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private Boolean activo = true;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellido", nullable = false)
    private String lastName;

    @Column(name = "correo", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono")
    private String phone;

    @CreationTimestamp
    @Column(name = "fecha_creacion")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(id, candidato.id) &&
                Objects.equals(email, candidato.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
