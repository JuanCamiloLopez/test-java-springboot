package com.api.rest.controller;

import com.api.rest.model.Empleado;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****RestTemplate:**

 `RestTemplate` es una clase proporcionada por Spring Framework que simplifica la comunicación con servicios RESTful a través de HTTP. Proporciona métodos convenientes para realizar solicitudes HTTP GET, POST, PUT y DELETE, manejar respuestas y convertir automáticamente las respuestas en objetos Java. En esencia, `RestTemplate` simplifica la interacción con servicios web basados en REST al abstraer gran parte de la complejidad de la comunicación HTTP.

 Puedes usar `RestTemplate` para realizar llamadas HTTP a otros servicios web, consumir APIs RESTful y realizar operaciones como obtener recursos, enviar datos, actualizar recursos, eliminar recursos, etc.
 **ResponseEntity:**

 `ResponseEntity` es una clase proporcionada por Spring Framework que representa una respuesta HTTP. Puede contener tanto el cuerpo de la respuesta como los metadatos asociados con la respuesta, como el código de estado HTTP, encabezados y más. `ResponseEntity` es utilizado para representar la respuesta que se recibe al realizar una llamada a un servicio web.

 Puedes usar `ResponseEntity` para manejar respuestas de servicios RESTful en tu aplicación. La clase `ResponseEntity` te permite acceder a los datos de la respuesta y a los metadatos asociados para realizar acciones como verificar el código de estado, analizar encabezados y procesar el cuerpo de la respuesta.
 **Testing de Controladores con ResponseEntity:**

 Al escribir pruebas para controladores en una aplicación Spring, es común utilizar `ResponseEntity` para verificar las respuestas que devuelve el controlador. Esto te permite verificar tanto el contenido de la respuesta como los metadatos de la misma, como el código de estado HTTP y los encabezados.

 Las pruebas de controladores con `ResponseEntity` te permiten simular solicitudes HTTP a tus controladores y verificar si las respuestas son coherentes con las expectativas. Puedes verificar el código de estado, analizar el contenido de la respuesta y comprobar que los encabezados sean correctos. Esto es especialmente útil para asegurarte de que tus controladores estén manejando adecuadamente las solicitudes y generando respuestas esperadas.

 En resumen, `RestTemplate` es una herramienta para realizar llamadas HTTP a servicios RESTful, mientras que `ResponseEntity` es una clase para representar respuestas HTTP en tu aplicación. Ambos son componentes útiles en el desarrollo y prueba de aplicaciones que interactúan con servicios web basados en REST.**/


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmpleadoControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Order(1)
    @Test
    void testGuardarEmpleado() {
        Empleado empleado = Empleado.builder()
                .id(1L)
                .nombre("ju")
                .apellido("lo")
                .email("ja@we")
                .build();
        ResponseEntity<Empleado> respuesta = testRestTemplate.postForEntity("http://localhost:8080/api/empleados", empleado, Empleado.class);
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        Empleado empleadoCreado = respuesta.getBody();
        assertNotNull(empleadoCreado);

        assertEquals(1L, empleadoCreado.getId());
        assertEquals("ju", empleadoCreado.getNombre());
        assertEquals("lo", empleado.getApellido());
        assertEquals("ja@we", empleadoCreado.getEmail());

    }

    @Test
    @Order(2)
    void listarEmpleado() {
        ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados", Empleado[].class);
        List<Empleado> empleados = Arrays.asList(respuesta.getBody());
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assertEquals(1, empleados.size());
        assertEquals(1L, empleados.get(0).getId());
        assertEquals("ju", empleados.get(0).getNombre());
        assertEquals("lo", empleados.get(0).getApellido());
        assertEquals("ja@we", empleados.get(0).getEmail());

    }

    @Test
    @Order(3)
    void obtenerEmpleadoPorId() {
        ResponseEntity<Empleado> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados/1", Empleado.class);
        Empleado empleado = respuesta.getBody();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assertNotNull(empleado);
        assertEquals(1L, empleado.getId());
        assertEquals("ju", empleado.getNombre());
        assertEquals("lo", empleado.getApellido());
        assertEquals("ja@we", empleado.getEmail());

    }

    /**
     * Esta línea de código utiliza RestTemplate (o en este caso, testRestTemplate) para realizar una solicitud HTTP DELETE a la URL "http://localhost:8080/api/empleados/{id}" con la ayuda de exchange.
     * <p>
     * ResponseEntity<Void> exchange:
     * <p>
     * Declara una variable llamada exchange que almacenará la respuesta de la solicitud HTTP.
     * ResponseEntity<Void> indica que esperamos recibir una respuesta sin contenido (cuerpo) en esta solicitud DELETE. La clase Void se usa aquí para indicar que no estamos esperando un cuerpo de respuesta.
     * testRestTemplate:
     * <p>
     * Es una instancia de RestTemplate configurada para pruebas. Proporciona métodos para realizar solicitudes HTTP en un entorno de prueba.
     * .exchange(...):
     * <p>
     * El método exchange de RestTemplate se utiliza para realizar solicitudes HTTP más flexibles y configurables.
     * Parámetros:
     * "http://localhost:8080/api/empleados/{id}": Es la URL de destino de la solicitud. {id} es un marcador de posición que se reemplazará por un valor real de pathVariables.
     * HttpMethod.DELETE: Indica que estamos realizando una solicitud HTTP DELETE.
     * null: En este caso, no estamos enviando un cuerpo de solicitud, por lo que se pasa null como cuerpo.
     * void.class: Indica que no estamos esperando un cuerpo de respuesta, por lo que usamos void.class.
     * pathVariables: Es un objeto que contiene los valores reales que se reemplazarán en los marcadores de posición de la URL.
     * En resumen, esta línea de código realiza una solicitud HTTP DELETE a la URL proporcionada, utilizando testRestTemplate.exchange(...). La respuesta de la solicitud se almacena en la variable exchange, pero como estamos esperando una respuesta sin contenido, no necesitamos acceder al cuerpo de la respuesta.
     **/
    @Test
    @Order(4)
    void eliminarEmpleado() {
        ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados", Empleado[].class);
        List<Empleado> empleados = Arrays.asList(respuesta.getBody());

        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", 1L);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("http://localhost:8080/api/empleados/{id}", HttpMethod.DELETE, null, void.class, pathVariables);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertFalse(exchange.hasBody());
        respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados", Empleado[].class);
        empleados = Arrays.asList(respuesta.getBody());
        assertEquals(0, empleados.size());
    }

}
