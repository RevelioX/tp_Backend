package TP.AlquileresMicroServicio.services.interfaces;

import TP.AlquileresMicroServicio.entities.Alquiler;
import TP.AlquileresMicroServicio.entities.Tarifa;

public interface AlquilerService extends Service<Alquiler,Long>{
    public Alquiler iniciarAlquiler(Long clientId, Long estacionId, Tarifa tarifaHoy);

    Alquiler finalizarAlquiler(Long alquilerId, Long estacionId);
}
