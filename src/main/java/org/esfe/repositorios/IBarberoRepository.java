package org.esfe.repositorios;

import org.esfe.modelos.Barbero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBarberoRepository extends JpaRepository<Barbero, Integer> {

    Optional<Barbero> findByIdOrderByIdAsc(Integer id);

    List<Barbero> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCaseOrderByNombreAsc(String nombre, String apellido);


    Optional<Barbero> findByTelefonoIgnoreCaseOrderByTelefonoAsc(String telefono);

    Page<Barbero> buscarTodosPaginados(Pageable pageable);
}
