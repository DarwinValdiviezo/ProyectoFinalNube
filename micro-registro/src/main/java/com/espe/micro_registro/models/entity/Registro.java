package com.espe.micro_registro.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String tipo; // "Asistencia" o "Evaluación"

    @Column(nullable = true)
    private String descripcion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fecha;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_id") // Esto asegura que la columna registro_id se maneje correctamente
    private List<EmpleadoRegistro> empleadoRegistros = new ArrayList<>();

    public Registro() {
        empleadoRegistros = new ArrayList<>();
    }

    public void addEmpleadoRegistro(EmpleadoRegistro empleadoRegistro) {
        empleadoRegistros.add(empleadoRegistro);
    }

    public void removeEmpleadoRegistro(EmpleadoRegistro empleadoRegistro) {
        empleadoRegistros.remove(empleadoRegistro);
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<EmpleadoRegistro> getEmpleadoRegistros() {
        return empleadoRegistros;
    }

    public void setEmpleadoRegistros(List<EmpleadoRegistro> empleadoRegistros) {
        this.empleadoRegistros = empleadoRegistros;
    }

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
        this.fecha = LocalDate.now();
    }
}