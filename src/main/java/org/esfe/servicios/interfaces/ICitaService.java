package org.esfe.servicios.interfaces;

import org.esfe.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICitaService {


    List<Cita> obtenerTodos();


    Page<Cita> buscarTodosPaginados(Pageable pageable);

    Optional<Cita> buscarPorId(Integer id);


    Page<Cita> buscarPorNombresDeServicioYCliente(String nombreServicio, String cliente, Pageable  pageable);

    Cita crearOEditar(Cita cita);

    void eliminarPorId(Integer id);

    List<Cita> findByFechaAndBarberoId(LocalDate fecha, Integer barberoId);
}
