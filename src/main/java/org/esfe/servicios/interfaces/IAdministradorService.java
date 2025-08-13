package org.esfe.servicios.interfaces;

import org.esfe.modelos.Administrador;
import org.esfe.modelos.Barbero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAdministradorService {

    Page<Administrador> buscarTodosPaginados(Pageable pageable);

    List<Administrador> obtenerTodos();

    Optional<Administrador> buscarPorId(Integer id);

    Optional<Administrador> buscarPorNombre(String nombre);

    Administrador crearOEditar(Administrador administrador);

    void eliminarPorId(Integer id);
}
