package TP.AlquileresMicroServicio.services;

import TP.AlquileresMicroServicio.controllers.responses.EstacionResponse;
import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.repositories.AlquilerRepository;
import TP.AlquileresMicroServicio.repositories.TarifaRepository;
import TP.AlquileresMicroServicio.services.interfaces.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    public Alquiler iniciarAlquiler(Long clientId,Long estacionId,Tarifa tarifaHoy){
        LocalDateTime fecha_hora_ahora = LocalDateTime.now();

        Alquiler nuevoAlquiler = new Alquiler();
        nuevoAlquiler.setESTADO(1L);
        nuevoAlquiler.setID_CLIENTE(clientId);
        nuevoAlquiler.setESTACION_RETIRO(estacionId);
        nuevoAlquiler.setTarifa(tarifaHoy);
        nuevoAlquiler.setFECHA_HORA_RETIRO(fecha_hora_ahora);

        return alquilerRepository.save(nuevoAlquiler);
    }

    public Alquiler finalizarAlquiler(Long alquilerId, Long estacionId){
        LocalDateTime fecha_hora_ahora = LocalDateTime.now();
        Alquiler alquiler = alquilerRepository.findById(alquilerId).orElseThrow();

        alquiler.setESTACION_DEVOLUCION(estacionId);
        alquiler.setFECHA_HORA_DEVOLUCION(fecha_hora_ahora);
        alquiler.setESTADO(2L);
        //Calcular Monto
        //Solicito la distancia entre las 2 estaciones
        String url = "http://localhost:7070/api/estaciones/distancia/" + alquiler.getESTACION_RETIRO() + "/" + estacionId;
        Float distancia = restTemplate.getForObject(url, Float.class);
        double distanciaValor = (distancia != null) ? distancia : 0.0f;
        //Obtengo la tarifa que usa el alquiler
        Tarifa tarifa = alquiler.getTarifa();
        double monto_fijo = tarifa.getMONTO_FIJO_ALQUILER().floatValue();
        double monto_km = tarifa.getMONTO_KM().floatValue() * Math.floor(distanciaValor/1000f);
        long minutosTotales = ChronoUnit.MINUTES.between(alquiler.getFECHA_HORA_RETIRO(),alquiler.getFECHA_HORA_DEVOLUCION());
        long horas = minutosTotales / 60;
        long minutosRestantes = minutosTotales % 60;
        double monto_tiempo = (horas * tarifa.getMONTO_HORA()) +
                (minutosRestantes > 31 ?
                    tarifa.getMONTO_HORA() : tarifa.getMONTO_MINUTO_FRACCION() * minutosRestantes);
        double monto_total = monto_fijo + monto_km + monto_tiempo;
        alquiler.setMONTO(monto_total);

        return alquilerRepository.save(alquiler);

    }
}
