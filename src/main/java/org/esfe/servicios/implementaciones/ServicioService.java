package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Barbero;
import org.esfe.modelos.Servicio;
import org.esfe.servicios.interfaces.IServicioService;
import org.esfe.repositorios.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServicioService implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepository;


    @Override
    public Page<Barbero> buscarPorNombreYApellidoConteniendo(String nombre, String apellido, Pageable pageable) {
        return null;
    }

    @Override
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    @Override
    public Optional<Servicio> buscarPorId(Integer id) {
        return servicioRepository.findByIdOrderByIdAsc(id);
    }

    @Override
    public List<Servicio> buscarPorNombreyPrecio() {
        return List.of();
    }

    @Override
    public List<Servicio> buscarPorNombreyPrecio(String nombre, Double precio) {
        return null;
    }

    @Override
    public Page<Servicio> buscarPorNombreyPrecio(String nombre, Double precio, Pageable pageable) {
        return servicioRepository.findByNombreContainingIgnoreCaseAndPrecioGreaterThanOrderByNombreAsc( nombre,  precio, pageable);
    }


    @Override
    public Servicio crearOEditar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public void eliminarPorId(Integer id) {
        servicioRepository.deleteById(id);
    }
}
