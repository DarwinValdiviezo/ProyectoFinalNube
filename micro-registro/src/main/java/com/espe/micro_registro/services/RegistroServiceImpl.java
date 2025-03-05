package com.espe.micro_registro.services;

import com.espe.micro_registro.clients.EmpleadoClientRest;
import com.espe.micro_registro.models.Empleado;
import com.espe.micro_registro.models.entity.EmpleadoRegistro;
import com.espe.micro_registro.models.entity.Registro;
import com.espe.micro_registro.repositories.EmpleadoRegistroRepository;
import com.espe.micro_registro.repositories.RegistroRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private EmpleadoRegistroRepository empleadoRegistroRepository;

    @Autowired
    private EmpleadoClientRest empleadoClientRest;

    @Override
    public List<Registro> findAll() {
        return (List<Registro>) registroRepository.findAll();
    }

    @Override
    public Optional<Registro> findById(Long id) {
        return registroRepository.findById(id);
    }

    @Override
    @Transactional
    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        registroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Empleado> asignarEmpleadoARegistro(Long registroId, EmpleadoRegistro empleadoRegistro) {
        Optional<Registro> registroOpt = registroRepository.findById(registroId);
        if (registroOpt.isEmpty()) {
            return Optional.empty();
        }

        Registro registro = registroOpt.get();

        try {
            // ✅ Obtener el token JWT del usuario autenticado
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String token = authentication.getToken().getTokenValue();

            // ✅ Enviar el token en la solicitud al microservicio de empleados
            Empleado empleadoMicro = empleadoClientRest.buscarPorId(empleadoRegistro.getEmpleadoId(), "Bearer " + token);

            boolean yaRegistrado = registro.getEmpleadoRegistros().stream()
                    .anyMatch(er -> er.getEmpleadoId().equals(empleadoRegistro.getEmpleadoId()));

            if (yaRegistrado) {
                throw new IllegalArgumentException("El empleado ya está asignado a este registro.");
            }

            empleadoRegistro.setRegistroId(registroId);
            registro.addEmpleadoRegistro(empleadoRegistro);
            registroRepository.save(registro);

            return Optional.of(empleadoMicro);
        } catch (FeignException e) {
            throw new RuntimeException("Error al comunicar con el microservicio de empleados: " + e.getMessage());
        }
    }



    @Override
    @Transactional
    public Optional<Empleado> desasignarEmpleadoDeRegistro(Long registroId, Long empleadoId) {
        empleadoRegistroRepository.deleteByRegistroIdAndEmpleadoId(registroId, empleadoId);
        try {
            // ✅ Obtener el token JWT del usuario autenticado
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String token = authentication.getToken().getTokenValue();

            // ✅ Enviar el token en la solicitud al microservicio de empleados
            Empleado empleado = empleadoClientRest.buscarPorId(empleadoId, "Bearer " + token);
            return Optional.of(empleado);
        } catch (FeignException e) {
            throw new RuntimeException("Error al consultar el empleado en el microservicio: " + e.getMessage());
        }
    }


    @Override
    public List<Map<String, Object>> obtenerEmpleadosAsignados(Long registroId) {
        Optional<Registro> registroOpt = registroRepository.findById(registroId);
        if (registroOpt.isEmpty()) {
            return Collections.emptyList(); // Devuelve una lista vacía si no hay registro
        }

        Registro registro = registroOpt.get();
        List<EmpleadoRegistro> relaciones = registro.getEmpleadoRegistros();

        List<Map<String, Object>> empleadosAsignados = new ArrayList<>();

        // ✅ Obtener el token JWT del usuario autenticado
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getToken().getTokenValue();

        for (EmpleadoRegistro relacion : relaciones) {
            try {
                // ✅ Enviar el token en la solicitud al microservicio de empleados
                Empleado empleado = empleadoClientRest.buscarPorId(relacion.getEmpleadoId(), "Bearer " + token);

                if (empleado != null) {
                    Map<String, Object> empleadoData = new HashMap<>();
                    empleadoData.put("empleado", empleado);
                    empleadoData.put("horaEntrada", relacion.getHoraEntrada());
                    empleadoData.put("horaSalida", relacion.getHoraSalida());
                    empleadoData.put("calificacion", relacion.getCalificacion());
                    empleadoData.put("comentarios", relacion.getComentarios());
                    empleadosAsignados.add(empleadoData);
                }
            } catch (FeignException e) {
                System.err.println("Error al obtener empleado con ID " + relacion.getEmpleadoId() + ": " + e.getMessage());
            }
        }

        return empleadosAsignados;
    }

}
