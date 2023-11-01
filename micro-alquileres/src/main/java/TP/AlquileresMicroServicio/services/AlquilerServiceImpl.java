package TP.AlquileresMicroServicio.services;

import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.repositories.AlquilerRepository;
import TP.AlquileresMicroServicio.services.interfaces.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Override
    public Alquiler add(Alquiler entity) {
        return alquilerRepository.save(entity);
    }

    @Override
    public Optional<Alquiler> getById(Long aLong) {
        return alquilerRepository.findById(aLong);
    }

    @Override
    public List<Alquiler> getAll() {
        return alquilerRepository.findAll();
    }

    @Override
    public void update(Alquiler entity) {
        Alquiler alquiler_find = alquilerRepository.findById(entity.getID()).orElse(null);
        if (alquiler_find == null) return;

        alquiler_find.setESTADO(entity.getESTADO());
        alquiler_find.setMONTO(entity.getMONTO());
        alquiler_find.setESTACION_DEVOLUCION(entity.getESTACION_DEVOLUCION());
        alquiler_find.setESTACION_RETIRO(entity.getESTACION_RETIRO());
        alquiler_find.setID_CLIENTE(entity.getID_CLIENTE());
        alquiler_find.setTarifa(entity.getTarifa());
        alquiler_find.setFECHA_HORA_DEVOLUCION(entity.getFECHA_HORA_DEVOLUCION());
        alquiler_find.setFECHA_HORA_RETIRO(entity.getFECHA_HORA_RETIRO());

        alquilerRepository.save(alquiler_find);
    }

    @Override
    public void delete(Long aLong) {
        alquilerRepository.deleteById(aLong);
    }
}
