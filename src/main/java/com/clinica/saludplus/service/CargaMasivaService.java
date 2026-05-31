package com.clinica.saludplus.service;

import com.clinica.saludplus.dto.PacienteDTO;
import com.clinica.saludplus.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<PacienteDTO> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            PacienteDTO dto = listaDto.get(i);

            Paciente paciente = new Paciente();
            paciente.setRun(dto.getRun());
            paciente.setNombres(dto.getNombres());
            paciente.setApellidos(dto.getApellidos());
            paciente.setFechaNacimiento(dto.getFechaNacimiento());
            paciente.setCorreo(dto.getCorreo());

            entityManager.persist(paciente);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}