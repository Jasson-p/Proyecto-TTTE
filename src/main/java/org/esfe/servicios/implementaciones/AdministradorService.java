package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Administrador;
import org.esfe.repositorios.IAdministradorRepository;
import org.esfe.servicios.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService implements IAdministradorService {

    @Autowired
    private IAdministradorRepository administradorRepository;

    @Override
    public Page<Administrador> buscarTodosPaginados(Pageable pageable) {
        return administradorRepository.findAll(pageable);
    }

    @Override
    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }

    @Override
    public Optional<Administrador> buscarPorId(Integer id) {
        return administradorRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public Optional<Administrador> buscarPorNombre(String nombreUsuario){
        return  administradorRepository.findByNombreUsuarioContainingIgnoreCaseOrderByNombreUsuarioAsc(nombreUsuario);
    }

    @Override
    public Administrador crearOEditar(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void eliminarPorId(Integer id) {
        administradorRepository.deleteById(id);
    }
}
