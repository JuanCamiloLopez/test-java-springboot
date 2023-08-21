package com.api.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.api.rest.model.Empleado;
import com.api.rest.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 1. `@WebMvcTest`
 * Esta es una anotación proporcionada por el framework Spring Boot Test. Se utiliza para probar los controladores MVC en una aplicación Spring Boot. Configura la infraestructura necesaria para probar el controlador, como la configuración de Spring MVC, el servidor y el contexto de la aplicación web.
 * <p>
 * 2. `public class EmpleadoControllerTests`
 * Esta es una clase de prueba que se utiliza para probar la funcionalidad de la clase `EmpleadoController`. Está anotada con `@WebMvcTest`, lo que significa que solo cargará los componentes necesarios para probar el controlador.
 * <p>
 * 3. `@Autowired private MockMvc mockMvc`
 * Esto es una inyección de dependencia del tipo de clase `MockMvc`. `MockMvc` es una clase de Spring que proporciona una forma de probar los controladores Spring MVC simulando solicitudes HTTP y verificando las respuestas. Te permite realizar solicitudes HTTP y verificar las respuestas en tus pruebas.
 * <p>
 * 4. `@MockBean private EmpleadoService empleadoService`
 * Esto es una inyección de dependencia del tipo de clase `EmpleadoService` con la anotación `@MockBean`. La anotación `@MockBean` se utiliza para crear un objeto simulado de la clase `EmpleadoService`. Al simular el servicio, puedes aislar el controlador y probar su comportamiento de forma independiente de la implementación real del servicio.
 * <p>
 * 5. `@Autowired private ObjectMapper objectMapper`
 * Esto es una inyección de dependencia del tipo de clase `ObjectMapper`. `ObjectMapper` es una clase de la biblioteca Jackson que se utiliza para convertir objetos a JSON y viceversa. Se utiliza comúnmente en aplicaciones Spring para la serialización y deserialización JSON.
 * <p>
 * En resumen, este código configura una clase de prueba para el `EmpleadoController` y proporciona la infraestructura necesaria para probar el controlador. También crea un objeto simulado de la clase `EmpleadoService` e lo inyecta en el controlador con fines de prueba. La clase `MockMvc` se utiliza para simular solicitudes HTTP y verificar las respuestas, y la clase `ObjectMapper` se utiliza para la serialización y deserialización JSON.
 **/
@WebMvcTest
public class EmpleadoControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpleadoService empleadoService;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Paso 1: Preparación (given):
     * <p>
     * Aquí se crea un objeto Empleado con valores simulados para ser utilizado en la prueba.
     * Se utiliza el método given() de la clase Mockito para definir un comportamiento simulado para el servicio empleadoService. En este caso, se configura para que el método saveEmpleado() retorne el argumento que se le pasa (lo que simula la funcionalidad de guardar el empleado).
     * Paso 2: Ejecución (when):
     * <p>
     * En esta sección, se realiza la solicitud HTTP POST simulando la creación de un nuevo empleado a través de la URL /api/empleados.
     * Se establece el tipo de contenido de la solicitud como JSON y se envía el objeto empleado serializado como contenido.
     * El resultado de esta acción se almacena en la variable response.
     * Paso 3: Verificación (then):
     * <p>
     * Aquí se verifica la respuesta obtenida después de realizar la solicitud HTTP.
     * El método andDo(print()) imprime información de depuración sobre la respuesta, lo que puede ser útil para el diagnóstico.
     * El método andExpect(status().isCreated()) verifica que el estado de la respuesta sea 201 Created, lo que indica que la solicitud de creación fue exitosa.
     * Luego, se utilizan las expresiones jsonPath() para verificar que ciertos campos en la respuesta JSON coincidan con los valores del objeto empleado.
     * En resumen, este código de prueba simula una solicitud HTTP POST para crear un nuevo empleado, luego verifica que la respuesta tenga el estado correcto y que los campos de la respuesta JSON coincidan con los valores proporcionados en el objeto empleado. Se utilizan bibliotecas como Mockito para simular el comportamiento del servicio y ObjectMapper para serializar el objeto en formato JSON.
     **/
    @Test
    void testGuardarEmpleado() throws Exception {
        //given
        Empleado empleado = Empleado.builder()
                .id(1L)
                .nombre("Christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
        given(empleadoService.saveEmpleado(any(Empleado.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/empleados").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(empleado)));

        //then
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$.apellido").value(empleado.getApellido()))
                .andExpect(jsonPath("$.email").value(empleado.getEmail()));
    }


    /**
     * Paso 1: Realización de la solicitud (when):
     * <p>
     * La línea mockMvc.perform(get("/api/empleados")) realiza una solicitud HTTP GET a la ruta /api/empleados de tu aplicación simulada, utilizando el objeto mockMvc. Esta acción simula una solicitud a la API para obtener una lista de empleados.
     * Paso 2: Verificación del estado de respuesta (then):
     * <p>
     * El método andExpect(status().isOk()) verifica que el estado de la respuesta HTTP sea 200 OK. En otras palabras, se verifica que la solicitud haya tenido éxito y se haya devuelto una respuesta válida.
     * Paso 3: Impresión de información de depuración (andDo(print())):
     * <p>
     * El método andDo(print()) imprime información de depuración sobre la respuesta. Esto puede ser útil para inspeccionar los detalles de la respuesta HTTP durante la ejecución de las pruebas.
     * Paso 4: Verificación de contenido JSON (andExpect(jsonPath(...))):
     * <p>
     * El método andExpect(jsonPath("$.size()").value(listaEmpleados.size())) se utiliza para verificar contenido específico en la respuesta JSON.
     * jsonPath("$.size()") define un punto de referencia en el JSON de la respuesta para realizar la verificación. En este caso, $ representa el nivel raíz del JSON, y $.size() hace referencia a la propiedad "size" en el JSON.
     * .value(listaEmpleados.size()) verifica que el valor de la propiedad "size" en el JSON sea igual al tamaño de la lista de empleados (listaEmpleados.size()).
     * Explicación de jsonPath() y value():
     * <p>
     * jsonPath() es un método proporcionado por Spring Test que permite seleccionar partes específicas de un documento JSON para su posterior verificación.
     * jsonPath("$.size()") selecciona el valor de la propiedad "size" en el JSON.
     * .value(listaEmpleados.size()) verifica que el valor seleccionado sea igual al tamaño de la lista de empleados.
     * En resumen, este código realiza una solicitud HTTP GET a la ruta /api/empleados, verifica que el estado de la respuesta sea 200 OK, imprime información de depuración sobre la respuesta y luego verifica que la propiedad "size" en el JSON de la respuesta sea igual al tamaño de la lista de empleados que se espera. La combinación de jsonPath() y value() permite verificar contenido específico en la respuesta JSON de manera eficiente durante las pruebas.
     **/
    @Test
    void ListarEmpleados() throws Exception {
        //given
        List<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(Empleado.builder().nombre("salome").apellido("popo").email("bombon@gmeil.com").build());
        listaEmpleados.add(Empleado.builder().nombre("salome").apellido("popo").email("comidabombon@gmeil.com").build());
        listaEmpleados.add(Empleado.builder().nombre("salome").apellido("popo").email("ombon@gmeil.com").build());
        listaEmpleados.add(Empleado.builder().nombre("salome").apellido("popo").email("idaybombon@gmeil.com").build());
        given(empleadoService.getAllEmpleado()).willReturn(listaEmpleados);
        //when
        ResultActions response = mockMvc.perform(get("/api/empleados"));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(listaEmpleados.size()));


    }

    @Test
    void empleadoPorId() throws Exception {
        //given
        long idEmpleado = 1L;
        Empleado empleado = Empleado.builder().nombre("juan").apellido("lolo").email("jua@jua.com").build();
        given(empleadoService.getEmpleadoById(idEmpleado)).willReturn(Optional.of(empleado));
        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}", idEmpleado));
        //then
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$.apellido").value(empleado.getApellido()));

    }

    @Test
    void empleadoNoEncontrado() throws Exception {
        //given
        long empleadoid = 1L;
        Empleado empleado = Empleado.builder().nombre("pedro").apellido("lolo").email("juan@.com").build();
        given(empleadoService.getEmpleadoById(empleadoid)).willReturn(Optional.empty());
        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}", empleadoid));
        //then
        response.andExpect(status().isNotFound()).andDo(print());
    }

    /**
     * .contentType(MediaType.APPLICATION_JSON):
     * <p>
     * En una solicitud HTTP, el encabezado Content-Type especifica el tipo de contenido del cuerpo de la solicitud. En este caso, se está configurando el encabezado Content-Type para indicar que el cuerpo de la solicitud está en formato JSON.
     * MediaType.APPLICATION_JSON es una constante proporcionada por la clase org.springframework.http.MediaType que representa el tipo de contenido application/json.
     * .content(objectMapper.writeValueAsString(empleadoActualizado)):
     * <p>
     * El método .content(...) establece el contenido del cuerpo de la solicitud HTTP. Aquí, se está configurando el cuerpo de la solicitud con el objeto empleadoActualizado serializado en formato JSON.
     * objectMapper es un objeto de la clase com.fasterxml.jackson.databind.ObjectMapper, que es parte de la biblioteca Jackson utilizada para la serialización y deserialización de objetos Java en formato JSON.
     * .writeValueAsString(empleadoActualizado) toma el objeto empleadoActualizado y lo convierte en una representación de cadena JSON. Esta cadena JSON es lo que se envía como contenido en la solicitud HTTP.
     * En resumen, esta parte del código configura la solicitud HTTP PUT con el encabezado Content-Type establecido en application/json y el cuerpo de la solicitud configurado como la representación JSON del objeto empleadoActualizado. El objectMapper se utiliza para convertir el objeto Java en un formato JSON adecuado para ser enviado como contenido en la solicitud.
     **/
    @Test
    void actualizarEmpleado() throws Exception {
        //given
        long empleadoid = 1L;
        Empleado empleado = Empleado.builder().nombre("pedro").apellido("lolo").email("juan@.com").build();
        Empleado empleadoActualizado = Empleado.builder().nombre("pedropablo").apellido("lalo").email("jsdan@.com").build();
        given(empleadoService.getEmpleadoById(empleadoid)).willReturn(Optional.of(empleado));
        given(empleadoService.updateEmpleado(any(Empleado.class))).willAnswer((invocation) -> invocation.getArgument(0));
        //when
        ResultActions response = mockMvc.perform(put("/api/empleados/{id}", empleadoid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoActualizado)));
        //Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre").value(empleadoActualizado.getNombre()))
                .andExpect(jsonPath("$.apellido").value(empleadoActualizado.getApellido()))
                .andExpect(jsonPath("$.email").value(empleadoActualizado.getEmail()));
    }

    @Test
    void actualizarEmpleadoNulo() throws Exception {
        //given
        long empleadoid = 1L;
        Empleado empleado = Empleado.builder().nombre("pedro").apellido("lolo").email("juan@.com").build();
        Empleado empleadoActualizado = Empleado.builder().nombre("pedropablo").apellido("lalo").email("jsdan@.com").build();
        given(empleadoService.getEmpleadoById(empleadoid)).willReturn(Optional.empty());
        given(empleadoService.updateEmpleado(any(Empleado.class))).willAnswer((invocation) -> invocation.getArgument(0));
        //when
        ResultActions response = mockMvc.perform(put("/api/empleados/{id}", empleadoid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoActualizado)));
        //Then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void eliminarEmpleado() throws Exception{
        //given
        long id = 1L;
        willDoNothing().given(empleadoService).deleteEmpleado(id);
        //when
        ResultActions response = mockMvc.perform(delete("/api/empleados/{id}", id));
        //then
        response.andExpect(status().isOk()).andDo(print());

    }
}





