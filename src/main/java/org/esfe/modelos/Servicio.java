package org.esfe.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



@Entity
@Table(name = "servicio")
public class Servicio {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @NotBlank(message = "El nombre es requerido")
        private String nombre;

    @NotNull(message = "El precio es requerido") // Valida que el valor no sea null
    @Positive(message = "El precio debe ser un número positivo") // Valida que el valor sea mayor que cero
    private double precio;

    @NotBlank(message = "La imagen es requerido")
    private String imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
