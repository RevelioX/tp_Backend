package TP.AlquileresMicroServicio.services;

import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.repositories.TarifaRepository;
import TP.AlquileresMicroServicio.services.interfaces.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void delete(Long aLong) {
        tarifaRepository.deleteById(aLong);
    }
}
