package TP.AlquileresMicroServicio.repositories;

import TP.AlquileresMicroServicio.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa,Long> {
}
