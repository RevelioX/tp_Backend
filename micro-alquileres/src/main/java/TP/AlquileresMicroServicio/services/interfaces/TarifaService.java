package TP.AlquileresMicroServicio.services.interfaces;

import TP.AlquileresMicroServicio.entities.Tarifa;
import TP.AlquileresMicroServicio.entities.TarifaPromocion;
import TP.AlquileresMicroServicio.entities.TarifaSemana;

import java.util.List;

public interface TarifaService extends Service<Tarifa,Long>{

    public Tarifa getTarifaHoy();
}
