package com.clinica.saludplus.repository;

import com.clinica.saludplus.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    boolean existsByRun(String run);
}