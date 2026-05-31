package com.clinica.saludplus.service;

import com.clinica.saludplus.dto.PacienteDTO;
import com.clinica.saludplus.model.Paciente;
import com.clinica.saludplus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CargaMasivaService cargaMasivaService;

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Paciente findById(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    public Paciente save(Paciente paciente) {
        if (paciente.getRun() != null && pacienteRepository.existsByRun(paciente.getRun())) {
            throw new RuntimeException("Error: El RUN " + paciente.getRun() + " ya existe");
        }
        return pacienteRepository.save(paciente);
    }

    public void delete(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public void guardarMasivo(List<PacienteDTO> lista) {
        cargaMasivaService.procesarCarga(lista);
    }
}