package com.api.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

import com.api.rest.model.Empleado;
import com.api.rest.model.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryTest {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setup() {
        empleado = Empleado.builder()
                .nombre("antonella")
                .apellido("lopez torres")
                .email("bebitafiufiu@gmail")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardadEmpleado() {
        //guiven: se espesifica el escenario, las precondiciones
        Empleado empleado1 = Empleado.builder()
                .nombre("antonella")
                .apellido("lopez torres")
                .email("bebitafiufiu@gmail")
                .build();
        // when cuando se haga esta accion
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);
        //then lo que yo voy a esperar es lo siguiente
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados() {
        //given
        Empleado empleado2 = Empleado.builder()
                .nombre("silvana")
                .apellido("torres")
                .email("bebitafiufiu@gmail")
                .build();
        empleadoRepository.save(empleado2);
        empleadoRepository.save(empleado);
        //when
        List<Empleado> listaEmpleados = empleadoRepository.findAll();
        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para encontrar por id")
    @Test
    public void testEmpleadoPorId(){
        //given
        Empleado empleadoPorId = Empleado.builder()
                .nombre("arturito")
                .apellido("lopez")
                .email("artur@.com")
                .build();
        empleadoRepository.save(empleadoPorId);
        //When comportamiento o accion
        Empleado empleadoDB = empleadoRepository.findById(empleadoPorId.getId()).get();
        //Then
        assertThat(empleadoDB).isNotNull();
    }

    @DisplayName("Test actualizar empleado")
    @Test
    public void actualizarEmpleado(){
        //given
        empleadoRepository.save(empleado);
        //When
        Empleado epleadoActualizar = empleadoRepository.findById(empleado.getId()).get();
        epleadoActualizar.setNombre("juan bbcito");
        epleadoActualizar.setEmail("bbcito@fiufiu");
        epleadoActualizar.setApellido("bbcito");
        Empleado empleadoGuardado = empleadoRepository.save(epleadoActualizar);
        //then
        assertThat(empleadoGuardado.getNombre()).isEqualTo("juan bbcito");
        assertThat(empleadoGuardado.getApellido()).isEqualTo("bbcito");
    }

    @DisplayName("Test Eliminar empleado")
    @Test
    public void eliminarEmpleado(){
        //given
        Empleado empleadoParaEliminar = empleadoRepository.save(empleado);
        //when
        empleadoRepository.deleteById(empleadoParaEliminar.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleadoParaEliminar.getId());
        //then
        assertThat(empleadoOptional).isEmpty();

    }

}
