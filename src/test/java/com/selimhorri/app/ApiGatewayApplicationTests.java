package com.selimhorri.app;

import org.junit.jupiter.api.Test;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // Usa un perfil de pruebas si tienes uno definido
class ApiGatewayApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        // Verifica que el contexto se haya cargado correctamente
    }
}
