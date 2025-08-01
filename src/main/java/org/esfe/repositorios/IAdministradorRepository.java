package org.esfe.repositorios;

import org.esfe.modelos.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdministradorRepository extends JpaRepository<Administrador, Integer> {
}
