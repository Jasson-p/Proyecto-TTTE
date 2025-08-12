package org.esfe.repositorios;

import org.esfe.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Integer>{

    Page<Servicio> buscarTodosPaginados(Pageable pageable);

    Optional<Servicio> findByIdOrderIdAsc(Integer id);

    List<Servicio> findByNombreContainingIgnoreCaseAndPrecioGreaterThanOrderByNameAsc();
}
