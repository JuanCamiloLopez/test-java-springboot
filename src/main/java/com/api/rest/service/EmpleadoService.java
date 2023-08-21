package com.api.rest.service;

import com.api.rest.model.Empleado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    Empleado saveEmpleado(Empleado empleado);

    List<Empleado> getAllEmpleado();

    Optional<Empleado> getEmpleadoById(Long id);

    Empleado updateEmpleado(Empleado empleadoActualizado);

    void deleteEmpleado(Long id);


}
