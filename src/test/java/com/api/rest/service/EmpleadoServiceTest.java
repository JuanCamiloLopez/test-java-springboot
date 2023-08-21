package com.api.rest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.api.rest.exception.ResourseNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.model.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {
    /**
     * la anotacion mock me crea un simulacro de la instancia y inyecta los simulacros que se crean con el mock
     **/
    @Mock
    private EmpleadoRepository empleadoRepository;

    /**
     * con esta anotacion me encargo de inyectar dentro del simularo creado con el  @mock, la clase que estamos instanciando
     **/
    @InjectMocks

    private EmpleadoServiceImpl empleadoService;
    /**
     * creo una instancia de empleado y la paso por un objeto builder para poder usarla en mis test, ahora bien el beforeEach me ejecuta esta funcion cada vez que ejecuto un test
     **/
    private Empleado empleado;

    @BeforeEach
    public void setup() {
        empleado = Empleado.builder().id(1l).nombre("salome").email("roman lopez").email("salome.@lopez.com").build();
    }

    @DisplayName("Test guardar un empleado")
    @Test
    public void testGuardarEmpleado() {
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        //when
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);
        //then
        assertThat(empleadoGuardado).isNotNull();

    }

    /**
     * La función proporcionada es un método de prueba llamado testGuardarEmpleadThrowException(). Vamos a analizarlo paso a paso y explicar cada método que se utiliza dentro de él:
     * <p>
     * given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.of(empleado));: Este método given() es parte de un marco de pruebas llamado Mockito y se utiliza para configurar un comportamiento simulado para una llamada de método. En este caso, se configura el comportamiento simulado de la llamada al método findByEmail() del objeto empleadoRepository. Se le dice que cuando se llame a este método con el email del empleado actual, debe devolver un objeto Optional que contiene el objeto empleado.
     * <p>
     * assertThrows(ResourseNotFoundException.class, ()->{ empleadoService.saveEmpleado(empleado); });: Este método assertThrows() también es parte de un marco de pruebas y se utiliza para verificar que se produce una excepción esperada durante la ejecución de un bloque de código. En este caso, se verifica que se arroje una excepción de tipo ResourseNotFoundException cuando se llama al método saveEmpleado() del objeto empleadoService con el objeto empleado actual.
     * <p>
     * verify(empleadoRepository, never()).save(any(Empleado.class));: El método verify() también es parte de Mockito y se utiliza para verificar si se ha llamado a un método específico con ciertos parámetros. En este caso, se verifica que nunca se haya llamado al método save() del objeto empleadoRepository con cualquier objeto de la clase Empleado.
     * <p>
     * En resumen, el método de prueba testGuardarEmpleadThrowException() configura un comportamiento simulado para una llamada de método, verifica que se arroje una excepción específica y verifica que cierto método no se haya llamado durante la ejecución del código de prueba.
     * <p>
     * Espero que esta explicación te haya sido útil. Si tienes más preguntas, no dudes en hacerlas.
     **/
    @DisplayName("Test guardar un empleado con Throw exception")
    @Test
    public void testGuardarEmpleadThrowException() {
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.of(empleado));
        //when
        assertThrows(ResourseNotFoundException.class, () -> {
            empleadoService.saveEmpleado(empleado);
        });
        //then
        verify(empleadoRepository, never()).save(any(Empleado.class));

    }

    @DisplayName("Test listar empleados")
    @Test
    void testListarEmpleados() {
        //given
        Empleado empleadoDos = Empleado.builder().id(2L).nombre("antonella").apellido("fiufiu").email("caramelo@chocolate").build();
        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleadoDos));
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleado();
        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar lista vacia")
    @Test
    void testListaVaciaEmpleados() {
        //given
        Empleado empleadoDos = Empleado.builder().id(2L).nombre("antonella").apellido("fiufiu").email("caramelo@chocolate").build();
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());
        //when
        List<Empleado> listaEmpledoss = empleadoService.getAllEmpleado();
        //then
        assertThat(listaEmpledoss).isEmpty();
        assertThat(listaEmpledoss.size()).isEqualTo(0);
    }

    @DisplayName("Test empleado por id ")
    @Test
    void testEmpleadoId() {
        //given
        given(empleadoRepository.findById(1l)).willReturn(Optional.of(empleado));
        //when
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();
        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test actualizar Empleado")
    @Test
    void testActualizarEmpleado() {
        //given
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("jj@lopez.com");
        empleado.setNombre("jjc");
        //when
        Empleado empledoActualizado = empleadoService.updateEmpleado(empleado);
        //Then
        assertThat(empledoActualizado.getEmail()).isEqualTo("jj@lopez.com");
        assertThat(empledoActualizado.getNombre()).isEqualTo("jjc");

    }

    @DisplayName("Test Eliminar Empleado")
    @Test
    void testEliminarEmpleado() {
        //given
        long empleadId = 1L;
        willDoNothing().given(empleadoRepository).deleteById(empleadId);
        //when
        empleadoService.deleteEmpleado(empleadId);
        //then
        verify(empleadoRepository, times(1)).deleteById(empleadId);
    }

}
