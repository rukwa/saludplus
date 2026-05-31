package com.clinica.saludplus.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Data
public class MedicoDTO {
    private String run;
    private String nombres;
    private String apellidos;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private String correo;
    private String especialidad;
}