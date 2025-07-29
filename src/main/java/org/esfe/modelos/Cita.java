package org.esfe.modelos;

import jakarta.persistence.*; // Importa las anotaciones de JPA
import java.time.LocalDate;   // Para la fecha (solo día, mes, año)
import java.time.LocalTime;   // Para la hora (solo hora, minuto, segundo)


@Entity
@Table(name = "Cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Usamos Integer para permitir valores nulos si no ha sido guardado aún

    @Column(name = "idServicio", nullable = false)
    private Integer idServicio;

    @Column(name = "idBarbero", nullable = false)
    private Integer idBarbero;

    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(Integer idBarbero) {
        this.idBarbero = idBarbero;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

