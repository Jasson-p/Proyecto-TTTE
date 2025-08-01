package org.esfe.servicios.interfaces;

import org.esfe.modelos.Barbero;
import org.esfe.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICitaService {
    Page<Cita> buscarTodosPaginados(Pageable pageable);

    List<Cita> obtenerTodos();

    Optional<Cita> buscarPorId(Integer id);

    Cita crearOEditar(Cita cita);

    void eliminarPorId(Integer id);
}
