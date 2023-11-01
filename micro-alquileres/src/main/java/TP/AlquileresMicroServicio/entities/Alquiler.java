package TP.AlquileresMicroServicio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private Long ID;
    @Basic
    private Long ID_CLIENTE;
    @Basic
    private Long ESTADO;
    @Basic
    private Long ESTACION_RETIRO;
    @Basic
    private Long ESTACION_DEVOLUCION;
    @Basic
    private LocalDateTime FECHA_HORA_RETIRO;
    @Basic
    private LocalDateTime FECHA_HORA_DEVOLUCION;
    @Basic
    private Double MONTO;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TARIFA")
    private Tarifa tarifa;

}
