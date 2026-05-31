package com.clinica.saludplus.service;

import com.clinica.saludplus.dto.MedicoDTO;
import com.clinica.saludplus.model.Medico;
import com.clinica.saludplus.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CargaMasivaMedicoService cargaMasivaMedicoService;

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Medico findById(Integer id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public Medico save(Medico medico) {
        if (medico.getRun() != null && medicoRepository.existsByRun(medico.getRun())) {
            throw new RuntimeException("Error: El RUN " + medico.getRun() + " ya existe");
        }
        return medicoRepository.save(medico);
    }

    public void delete(Integer id) {
        medicoRepository.deleteById(id);
    }

    public void guardarMasivo(List<MedicoDTO> lista) {
        cargaMasivaMedicoService.procesarCarga(lista);
    }
}