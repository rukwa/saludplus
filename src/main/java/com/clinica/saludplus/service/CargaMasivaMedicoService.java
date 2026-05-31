package com.clinica.saludplus.service;

import com.clinica.saludplus.dto.MedicoDTO;
import com.clinica.saludplus.model.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargaMasivaMedicoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<MedicoDTO> listaDto) {
        int batchSize = 50;
        for (int i = 0; i < listaDto.size(); i++) {
            MedicoDTO dto = listaDto.get(i);
            Medico medico = new Medico();
            medico.setRun(dto.getRun());
            medico.setNombres(dto.getNombres());
            medico.setApellidos(dto.getApellidos());
            medico.setFechaNacimiento(dto.getFechaNacimiento());
            medico.setCorreo(dto.getCorreo());
            medico.setEspecialidad(dto.getEspecialidad());
            entityManager.persist(medico);
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}