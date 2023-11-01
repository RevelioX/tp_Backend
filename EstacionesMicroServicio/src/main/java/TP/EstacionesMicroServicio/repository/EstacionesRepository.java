package TP.EstacionesMicroServicio.repository;

import TP.EstacionesMicroServicio.entity.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstacionesRepository extends JpaRepository<Estacion, Integer> {

}
