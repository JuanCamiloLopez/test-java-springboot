package com.api.rest.controller;

import com.api.rest.model.Empleado;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmpleadoControllerWebTestClient {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void guardarEmpleado() {
        //guiven
        Empleado empleado = Empleado.builder().id(1L).nombre("ju").apellido("lo").email("1dd@dsad").build();
        //when
        webTestClient.post().uri("http://localhost:8080/api/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(empleado)
                .exchange()
                //then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(empleado.getId())
                .jsonPath("$.nombre").isEqualTo(empleado.getNombre())
                .jsonPath("$.apellido").isEqualTo(empleado.getApellido())
                .jsonPath("$.email").isEqualTo(empleado.getEmail());

    }

    @Test
    @Order(2)
    void obtenerPorId() {
        webTestClient.get().uri("http://localhost:8080/api/empleados/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("ju")
                .jsonPath("$.apellido").isEqualTo("lo")
                .jsonPath("$.email").isEqualTo("1dd@dsad");

    }

    @Test
    @Order(3)
    void listarEmpleados() {
        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("ju")
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(1));
    }

    @Test
    @Order(4)
    void obtenerLidtaDeEmpleados() {
        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Empleado.class)
                .consumeWith(response -> {
                    List<Empleado> empleados = response.getResponseBody();
                    Assertions.assertEquals(1, empleados.size());
                    Assertions.assertNotNull(empleados);
                });
    }

    @Test
    @Order(5)
    void testActualizarEmpleado() {
        Empleado empleadoActualizado = Empleado.builder().nombre("lao").apellido("lioa").email("dad@as").build();

        webTestClient.put().uri("http://localhost:8080/api/empleados/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(empleadoActualizado)
                .exchange()
                //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

    }

    @Test
    @Order(6)
    void eliminarEmleado() {
        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Empleado.class)
                .hasSize(1);

        webTestClient.delete().uri("http://localhost:8080/api/empleados/1").exchange().expectStatus().isOk();

        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Empleado.class)
                .hasSize(0);

        webTestClient.get().uri("http://localhost:8080/api/empleados/1").exchange().expectStatus().is4xxClientError();
    }


}
