package com.espe.micro_empleado.controllers;

import com.espe.micro_empleado.models.entity.Empleado;
import com.espe.micro_empleado.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin // Habilita CORS para React
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Empleado empleado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(empleado));
    }

    @GetMapping
    public List<Empleado> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        return service.findById(id).map(empleadoDB -> {
            empleadoDB.setNombre(empleado.getNombre());
            empleadoDB.setApellido(empleado.getApellido());
            empleadoDB.setCedula(empleado.getCedula());
            empleadoDB.setTelefono(empleado.getTelefono());
            empleadoDB.setEmail(empleado.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(empleadoDB));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body((Empleado) Collections.singletonMap("mensaje", "Empleado no encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.findById(id).map(empleado -> {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}