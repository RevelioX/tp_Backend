package TP.EstacionesMicroServicio.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ESTACIONES")
@NoArgsConstructor
@AllArgsConstructor

public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Basic
    private String NOMBRE;
    @Basic
    private LocalDateTime FECHA_HORA_CREACION;
    @Basic
    private Float LATITUD;
    @Basic
    private Float LONGITUD;

    public Float calcularDistancia(float longitud, float latitud){
        Float distancia = (Float)(float)Math.sqrt( Math.pow(longitud - LONGITUD,2) + Math.pow(latitud - LATITUD,2) );
        return distancia;
    }

}
