package org.esfe.servicios.interfaces;

import org.esfe.modelos.Barbero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBarberoService {
    Page<Barbero> buscarTodosPaginados(Pageable pageable);

    List<Barbero> obtenerTodos();

    Optional<Barbero> buscarPorId(Integer id);

    Barbero crearOEditar(Barbero barbero);

    void eliminarPorId(Integer id);
}
