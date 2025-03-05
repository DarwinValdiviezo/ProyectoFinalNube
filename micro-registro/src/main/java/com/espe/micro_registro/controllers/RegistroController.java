package com.espe.micro_registro.controllers;

import com.espe.micro_registro.models.Empleado;
import com.espe.micro_registro.models.entity.EmpleadoRegistro;
import com.espe.micro_registro.models.entity.Registro;
import com.espe.micro_registro.services.RegistroService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://172.190.133.209:5173") // Habilita CORS para React
@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    @Autowired
    private RegistroService service;

    // ✅ Crear un nuevo registro
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Registro registro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(registro));
    }

    // ✅ Listar todos los registros
    @GetMapping
    public List<Registro> listar() {
        return service.findAll();
    }

    // ✅ Buscar un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Obtener empleados asignados a un registro
    @GetMapping("/{id}/empleados")
    public ResponseEntity<?> obtenerEmpleadosAsignados(@PathVariable Long id) {
        List<Map<String, Object>> empleados = service.obtenerEmpleadosAsignados(id);
        if (empleados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("mensaje", "No hay empleados asignados"));
        }
        return ResponseEntity.ok(empleados);
    }

    // ✅ Editar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Registro registro) {
        return service.findById(id).map(registroDB -> {
            registroDB.setTipo(registro.getTipo()); // "Asistencia" o "Evaluación"
            registroDB.setFecha(registro.getFecha());
            registroDB.setDescripcion(registro.getDescripcion());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(registroDB));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.findById(id).map(registro -> {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Asignar un empleado a un registro
    @PostMapping("/asignar-empleado/{registroId}")
    public ResponseEntity<?> asignarEmpleado(@PathVariable Long registroId, @RequestBody EmpleadoRegistro empleadoRegistro) {
        try {
            Optional<Empleado> empleadoAsignado = service.asignarEmpleadoARegistro(registroId, empleadoRegistro);
            return empleadoAsignado.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body((Empleado) Collections.singletonMap("mensaje", "Registro no encontrado o error en la asignación")));
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "No se pudo comunicar con el microservicio de empleados: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // ✅ Desasignar un empleado de un registro
    @PostMapping("/desasignar-empleado/{registroId}/empleado/{empleadoId}")
    public ResponseEntity<?> desasignarEmpleado(@PathVariable Long registroId, @PathVariable Long empleadoId) {
        try {
            Optional<Empleado> empleadoEliminado = service.desasignarEmpleadoDeRegistro(registroId, empleadoId);
            return empleadoEliminado.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "No se pudo comunicar con el microservicio de empleados: " + e.getMessage()));
        }
    }
}
