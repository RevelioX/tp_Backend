package TP.AlquileresMicroServicio.repositories;

import TP.AlquileresMicroServicio.entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler,Long> {
}
