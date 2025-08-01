package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Barbero;
import org.esfe.repositorios.IBarberoRepository;
import org.esfe.servicios.interfaces.IBarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarberoService implements IBarberoService {

    @Autowired
    private IBarberoRepository barberoRepository;

    @Override
    public Page<Barbero> buscarTodosPaginados(Pageable pageable) {
        return barberoRepository.findAll(pageable);
    }

    @Override
    public List<Barbero> obtenerTodos() {
        return barberoRepository.findAll();
    }

    @Override
    public Optional<Barbero> buscarPorId(Integer id) {
        return barberoRepository.findById(id);
    }

    @Override
    public Barbero crearOEditar(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    @Override
    public void eliminarPorId(Integer id) {
        barberoRepository.deleteById(id);
    }
}
