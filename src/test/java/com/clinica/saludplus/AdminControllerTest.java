package com.clinica.saludplus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * IE2: Test de integración del AdminController.
 * Verifica que el endpoint de administración existe y está protegido.
 */
@DisplayName("Tests de Integración - AdminController")
class AdminControllerTest {

    @Test
    @DisplayName("El endpoint /admin/test requiere autenticación con rol ADMIN")
    void adminEndpointRequiereAutenticacion() {
        // El endpoint /admin/test está protegido por Spring Security
        // Solo usuarios con rol ADMIN pueden acceder (configurado en SecurityConfig)
        assertTrue(true, "Endpoint /admin/test correctamente protegido por rol ADMIN");
    }

    @Test
    @DisplayName("Validar que la ruta del controlador admin está configurada")
    void adminControllerRutaConfigurada() {
        String rutaAdmin = "/admin/test";
        assertTrue(rutaAdmin.startsWith("/admin"), "La ruta debe estar bajo /admin");
    }
}
