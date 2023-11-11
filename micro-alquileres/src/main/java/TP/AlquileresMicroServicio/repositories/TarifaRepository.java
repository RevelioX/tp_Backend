package TP.AlquileresMicroServicio.repositories;

import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.entities.TarifaPromocion;
import TP.AlquileresMicroServicio.entities.TarifaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa,Long> {


    //Devuelve la TarifaPromocion correspondiente a la fecha que se le pasa
    Optional<TarifaPromocion> findTarifaPromocionByDiaAndMesAndAnio(int dia, int mes, int anio);

    //Devuelve la Tarifa correspondiente al dia de la semana
    Optional<TarifaSemana> findTarifaSemanaByDiaSemana(int dia);

}
