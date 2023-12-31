package TP.AlquileresMicroServicio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TARIFAS")
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private Long ID;
    @Basic
    private Integer TIPO_TARIFA;
    @Basic
    private String DEFINICION;
    @Basic
    private Integer DIA_SEMANA;
    @Basic
    private Integer DIA_MES;
    @Basic
    private Integer MES;
    @Basic
    private Integer ANIO;
    @Basic
    private Double MONTO_FIJO_ALQUILER;
    @Basic
    private Double MONTO_MINUTO_FRACCION;
    @Basic
    private Double MONTO_KM;
    @Basic
    private Double MONTO_HORA;
}
