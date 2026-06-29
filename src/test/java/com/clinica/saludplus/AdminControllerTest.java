package com.clinica.saludplus;

import com.clinica.saludplus.controller.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminController.class)
@ActiveProfiles("test")
@DisplayName("Tests de Integración - AdminController")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /admin/test debe retornar 200 con usuario ADMIN autenticado")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminTestEndpointRetorna200ConRolAdmin() throws Exception {
        mockMvc.perform(get("/admin/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Acceso solo ADMIN"));
    }

    @Test
    @DisplayName("GET /admin/test sin autenticación debe retornar 401 o 403")
    void adminTestEndpointSinAutenticacionRetornaError() throws Exception {
        mockMvc.perform(get("/admin/test"))
                .andExpect(status().is4xxClientError());
    }
}
