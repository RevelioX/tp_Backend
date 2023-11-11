package TP.AlquileresMicroServicio.controllers.responses;

import TP.AlquileresMicroServicio.entities.Tarifa;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
public class AlquilerResponse {

    private Long id_cliente;
    private Long estado;
    private Long estacionRetiro;
    private Long estacionDevolucion;
    private LocalDateTime fechaHoraDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private Double monto;
}