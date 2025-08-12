package org.esfe.repositorios;

import org.esfe.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Integer>{







    List<Servicio> findByNombreContainingIgnoreCaseAndPrecioGreaterThanOrderByNombreAsc(String nombre, Double precio);

    Optional<Servicio> findByIdOrderByIdAsc(Integer id);
}
