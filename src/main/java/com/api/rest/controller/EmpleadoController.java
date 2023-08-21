package com.api.rest.controller;

import com.api.rest.model.Empleado;
import com.api.rest.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.saveEmpleado(empleado);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.getAllEmpleado();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable("id") Long empleadoId) {
        return empleadoService.getEmpleadoById(empleadoId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        return empleadoService.getEmpleadoById(id)
                .map(empleadoGuardado -> {
                    empleadoGuardado.setNombre(empleado.getNombre());
                    empleadoGuardado.setApellido(empleado.getApellido());
                    empleadoGuardado.setEmail(empleado.getEmail());
                    Empleado empeladoActualizado = empleadoService.updateEmpleado(empleadoGuardado);
                    return new ResponseEntity<>(empeladoActualizado, HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable("id") Long id) {
        empleadoService.deleteEmpleado(id);
        return new ResponseEntity<String>("Empleado eliminado exitosamente", HttpStatus.OK);
    }

}
