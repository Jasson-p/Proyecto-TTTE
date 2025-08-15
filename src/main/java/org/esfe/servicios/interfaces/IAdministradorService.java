package org.esfe.servicios.interfaces;

import org.esfe.modelos.Administrador;
import org.esfe.modelos.Barbero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAdministradorService {

    Optional<Administrador> buscarPorId(Integer id);

    Page<Administrador> buscarPorNombre(String nombreUsuario, Pageable pageable);

    Administrador crearOEditar(Administrador administrador);

    void eliminarPorId(Integer id);
}
