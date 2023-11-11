package TP.AlquileresMicroServicio.services;

import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.entities.TarifaPromocion;
import TP.AlquileresMicroServicio.entities.TarifaSemana;
import TP.AlquileresMicroServicio.repositories.TarifaRepository;
import TP.AlquileresMicroServicio.services.interfaces.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarifaServiceImpl implements TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    public Tarifa add(Tarifa entity) {
        return tarifaRepository.save(entity);
    }

    @Override
    public Optional<Tarifa> getById(Long aLong) {
        return tarifaRepository.findById(aLong);
    }

    @Override
    public List<Tarifa> getAll() {
        return tarifaRepository.findAll();
    }

    @Override
    public void update(Tarifa entity) {

    }

    /*
    @Override
    public void update(Tarifa entity) {
        Tarifa tarifa_find = tarifaRepository.findById(entity.getID()).orElse(null);
        if (tarifa_find == null)return;

        tarifa_find.setANIO(entity.getANIO());
        tarifa_find.setMES(entity.getMES());
        tarifa_find.setTIPO_TARIFA(entity.getTIPO_TARIFA());
        tarifa_find.setDEFINICION(entity.getDEFINICION());
        tarifa_find.setDIA_MES(entity.getDIA_MES());
        tarifa_find.setDIA_SEMANA(entity.getDIA_SEMANA());
        tarifa_find.setMONTO_FIJO_ALQUILER(entity.getMONTO_FIJO_ALQUILER());
        tarifa_find.setMONTO_KM(entity.getMONTO_KM());
        tarifa_find.setMONTO_HORA(entity.getMONTO_HORA());
        tarifa_find.setMONTO_MINUTO_FRACCION(entity.getMONTO_MINUTO_FRACCION());

        tarifaRepository.save(tarifa_find);
    }
    */
    @Override
    public void delete(Long aLong) {
        tarifaRepository.deleteById(aLong);
    }


    @Override
    public Tarifa getTarifaHoy() {
        LocalDate fechaActual = LocalDate.now();

        // Intenta obtener un DiaFestivo para la fecha actual
        Optional<TarifaPromocion> tarifaPromocion = tarifaRepository.findTarifaPromocionByDiaAndMesAndAnio(
                fechaActual.getDayOfMonth(), fechaActual.getMonthValue(), fechaActual.getYear());

        // Si existe un Dia Festivo, devuélvelo
        if (tarifaPromocion.isPresent()) {
            return tarifaPromocion.get();
        } else {
            // Si no hay un DiaFestivo, intenta obtener un DiaSemana para el día de la semana actual
            Optional<TarifaSemana> tarifaSemanaOptional = tarifaRepository.findTarifaSemanaByDiaSemana(
                    fechaActual.getDayOfWeek().getValue());

            // Si existe un DiaSemana, devuélvelo, de lo contrario, podrías lanzar una excepción o manejarlo según tus necesidades
            return tarifaSemanaOptional.orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se encontró un DiaSemana para el día actual"
            ));
        }
    }

}
