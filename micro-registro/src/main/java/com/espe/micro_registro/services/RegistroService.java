package com.espe.micro_registro.services;

import com.espe.micro_registro.models.Empleado;
import com.espe.micro_registro.models.entity.EmpleadoRegistro;
import com.espe.micro_registro.models.entity.Registro;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RegistroService {
    List<Registro> findAll();
    Optional<Registro> findById(Long id);
    Registro save(Registro registro);
    void deleteById(Long id);

    // ✅ Asignar un empleado a un registro
    Optional<Empleado> asignarEmpleadoARegistro(Long registroId, EmpleadoRegistro empleadoRegistro);

    // ✅ Desasignar un empleado de un registro
    Optional<Empleado> desasignarEmpleadoDeRegistro(Long registroId, Long empleadoId);

    // ✅ Obtener lista de empleados asignados a un registro
    List<Map<String, Object>> obtenerEmpleadosAsignados(Long registroId);
}
