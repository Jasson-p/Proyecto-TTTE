package org.esfe.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "barbero")
public class Barbero {

    private Integer id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;
}
