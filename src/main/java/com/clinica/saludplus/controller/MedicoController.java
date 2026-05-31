package com.clinica.saludplus.controller;

import com.clinica.saludplus.dto.MedicoDTO;
import com.clinica.saludplus.model.Medico;
import com.clinica.saludplus.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> medicos = medicoService.findAll();
        if (medicos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(medicos);
    }

    @PostMapping
    public ResponseEntity<Medico> guardar(@RequestBody Medico medico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.save(medico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(medicoService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Integer id, @RequestBody Medico medico) {
        try {
            Medico med = medicoService.findById(id);
            med.setRun(medico.getRun());
            med.setNombres(medico.getNombres());
            med.setApellidos(medico.getApellidos());
            med.setFechaNacimiento(medico.getFechaNacimiento());
            med.setCorreo(medico.getCorreo());
            med.setEspecialidad(medico.getEspecialidad());
            medicoService.save(med);
            return ResponseEntity.ok(med);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            medicoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/masivo")
    public ResponseEntity<String> cargaMasiva(@RequestBody List<MedicoDTO> lista) {
        medicoService.guardarMasivo(lista);
        return ResponseEntity.ok("Carga masiva de médicos exitosa");
    }
}