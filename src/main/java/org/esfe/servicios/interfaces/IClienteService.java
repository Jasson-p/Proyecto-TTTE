package org.esfe.servicios.interfaces;

import org.esfe.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> obtenerTodos();

    Optional<Cliente> buscarPorId(Integer id);

    List<Cliente> buscarPorNombreApellidoOTelefono(String nombre, String apellido, String telefono);


    List<Cliente> buscarPorNombreYApellidoConteniendo(String nombre, String apellido);

    Page<Cliente> buscarPorNombreYApellidoConteniendo(String nombre, String apellido, Pageable pageable);

    Optional<Cliente> buscarPorTelefono(String telefono);

    Cliente crearOEditar(Cliente cliente);

    void eliminarPorId(Integer id);
}
