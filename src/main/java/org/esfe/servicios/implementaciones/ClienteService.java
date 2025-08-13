package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Cliente;
import org.esfe.repositorios.IClienteRepository;
import org.esfe.servicios.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;


    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }



    @Override
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public List<Cliente> buscarPorNombreApellidoOTelefono(String nombre, String apellido, String telefono) {
        return List.of();
    }

    @Override
    public List<Cliente> buscarPorNombreYApellidoConteniendo(String nombre, String apellido) {
        return List.of();
    }

    @Override
    public Page<Cliente> buscarPorNombreYApellidoConteniendo(String nombre, String apellido, Pageable pageable) {
        return clienteRepository.findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(nombre, apellido, pageable);
    }

    @Override
    public Optional<Cliente> buscarPorTelefono(String telefono) {
        return clienteRepository.findByTelefonoIgnoreCaseOrderByTelefonoAsc(telefono);
    }

    @Override
    public Cliente crearOEditar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarPorId(Integer id) {
        clienteRepository.deleteById(id);
    }
}
