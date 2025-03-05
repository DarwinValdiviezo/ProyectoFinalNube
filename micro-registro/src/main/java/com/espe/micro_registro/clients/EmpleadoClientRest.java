package com.espe.micro_registro.clients;

import com.espe.micro_registro.models.Empleado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "micro-empleado", url = "${EMPLEADO_URL}")
public interface EmpleadoClientRest {

    @GetMapping("/{id}")
    Empleado buscarPorId(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);
}
