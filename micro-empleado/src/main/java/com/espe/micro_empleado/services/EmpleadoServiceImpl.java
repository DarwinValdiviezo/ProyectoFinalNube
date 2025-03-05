package com.espe.micro_empleado.services;

import com.espe.micro_empleado.models.entity.Empleado;
import com.espe.micro_empleado.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository repository;

    @Override
    public List<Empleado> findAll() {
        return (List<Empleado>) repository.findAll();
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Empleado save(Empleado empleado) {
        return repository.save(empleado);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
