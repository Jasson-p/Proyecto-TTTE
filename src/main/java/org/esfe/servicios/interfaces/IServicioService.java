package org.esfe.servicios.interfaces;

import org.esfe.modelos.Barbero;
import org.esfe.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IServicioService {
    Page<Servicio> buscarTodosPaginados(Pageable pageable);

    List<Servicio> obtenerTodos();

    Optional<Servicio> buscarPorId(Integer id);

    Servicio crearOEditar(Servicio servicio);

    void eliminarPorId(Integer id);
}
