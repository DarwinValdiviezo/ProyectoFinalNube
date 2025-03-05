package com.espe.micro_registro.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado_registro")
public class EmpleadoRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @Column(name = "registro_id", nullable = false)
    private Long registroId;

    @Column(name = "hora_entrada", nullable = true)
    private String horaEntrada;

    @Column(name = "hora_salida", nullable = true)
    private String horaSalida;

    @Column(name = "calificacion", nullable = true)
    private Double calificacion;

    @Column(name = "comentarios", nullable = true)
    private String comentarios;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Long registroId) {
        this.registroId = registroId;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmpleadoRegistro)) return false;
        EmpleadoRegistro other = (EmpleadoRegistro) obj;
        return this.empleadoId != null && this.empleadoId.equals(other.empleadoId) &&
                this.registroId != null && this.registroId.equals(other.registroId);
    }
}