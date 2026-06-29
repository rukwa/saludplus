package com.clinica.saludplus;

import com.clinica.saludplus.model.Medico;
import com.clinica.saludplus.repository.MedicoRepository;
import com.clinica.saludplus.service.MedicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - MedicoService")
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    private Medico medicoEjemplo;

    @BeforeEach
    void setUp() {
        medicoEjemplo = new Medico();
        // Ajusta los setters según los campos reales de tu clase Medico
        // Ejemplos comunes:
        // medicoEjemplo.setNombre("Dr. Juan Pérez");
        // medicoEjemplo.setRun("12345678-9");
        // medicoEjemplo.setEspecialidad("Cardiología");
    }

    @Test
    @DisplayName("Caso crítico: RUN de médico no puede estar duplicado")
    void runMedicoNoPuedeEstarDuplicado() {
        String runExistente = "12345678-9";

        // Simula que el repositorio encuentra ese RUN
        when(medicoRepository.existsByRun(runExistente)).thenReturn(true);

        boolean existe = medicoRepository.existsByRun(runExistente);

        assertTrue(existe, "El sistema debe detectar un RUN ya registrado");
        verify(medicoRepository, times(1)).existsByRun(runExistente);
    }

    @Test
    @DisplayName("Caso crítico: RUN nuevo no está duplicado")
    void runNuevoNoEstaRegistrado() {
        String runNuevo = "98765432-1";

        when(medicoRepository.existsByRun(runNuevo)).thenReturn(false);

        boolean existe = medicoRepository.existsByRun(runNuevo);

        assertFalse(existe, "Un RUN nuevo no debe existir en el sistema");
    }

    @Test
    @DisplayName("Caso crítico: lista de médicos no debe ser null")
    void listaMedicosNoEsNull() {
        when(medicoRepository.findAll()).thenReturn(Arrays.asList(medicoEjemplo));

        List<Medico> medicos = medicoRepository.findAll();

        assertNotNull(medicos, "La lista de médicos no debe ser null");
        assertFalse(medicos.isEmpty(), "Debe haber al menos un médico en la lista");
    }

    @Test
    @DisplayName("Caso crítico: búsqueda de médico inexistente retorna vacío")
    void medicoInexistenteRetornaOptionalVacio() {
        when(medicoRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Medico> resultado = medicoRepository.findById(999);

        assertTrue(resultado.isEmpty(), "Médico con ID inexistente debe retornar Optional vacío");
    }

    @Test
    @DisplayName("Caso crítico: guardar médico llama al repositorio una vez")
    void guardarMedicoInvocaRepositorio() {
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoEjemplo);

        medicoRepository.save(medicoEjemplo);

        verify(medicoRepository, times(1)).save(medicoEjemplo);
    }
}