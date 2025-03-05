package com.espe.micro_registro.repositories;

import com.espe.micro_registro.models.entity.Registro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends CrudRepository<Registro, Long> {
}