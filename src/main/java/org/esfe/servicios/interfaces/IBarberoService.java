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


    List<Barbero> buscarPorNombreApellidoOTelefono(String nombre, String apellido, String telefono);

    List<Barbero> buscarPorNombreYApellidoConteniendo(String nombre, String apellido);

    Optional<Barbero> buscarPorTelefono(String telefono);

    Barbero crearOEditar(Barbero barbero);

    void eliminarPorId(Integer id);
}
