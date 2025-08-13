package org.esfe.repositorios;

import org.esfe.modelos.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdministradorRepository extends JpaRepository<Administrador, Integer> {
    Optional<Administrador> findByIdOrderByIdAsc(Integer id);



    Optional<Administrador> findByNombreUsuarioContainingIgnoreCaseOrderByNombreUsuarioAsc(String nombreUsuario);
}
