package org.esfe.repositorios;

import org.esfe.modelos.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaRepository extends JpaRepository<Cita, Integer>{
}
