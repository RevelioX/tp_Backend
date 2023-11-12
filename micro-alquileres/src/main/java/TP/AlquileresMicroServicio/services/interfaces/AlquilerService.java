package TP.AlquileresMicroServicio.services.interfaces;

import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;

import java.util.List;

public interface AlquilerService extends Service<Alquiler,Long>{
    public Alquiler iniciarAlquiler(Long clientId, Long estacionId, Tarifa tarifaHoy);

    public Alquiler finalizarAlquiler(Long alquilerId, Long estacionId);

    public List<Alquiler> getAlquileresEstado(Long estado);
}
