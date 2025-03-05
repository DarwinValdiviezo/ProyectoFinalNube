package com.espe.micro_registro.repositories;

import com.espe.micro_registro.models.entity.EmpleadoRegistro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRegistroRepository extends CrudRepository<EmpleadoRegistro, Long> {
    void deleteByRegistroIdAndEmpleadoId(Long registroId, Long empleadoId);
}