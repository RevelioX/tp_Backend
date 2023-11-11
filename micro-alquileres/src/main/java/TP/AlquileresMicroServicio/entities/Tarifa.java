package TP.AlquileresMicroServicio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TARIFAS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO_TARIFA",
        discriminatorType = DiscriminatorType.INTEGER)
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private Long ID;
    @Basic
    private String DEFINICION;
    @Basic
    private Double MONTO_FIJO_ALQUILER;
    @Basic
    private Double MONTO_MINUTO_FRACCION;
    @Basic
    private Double MONTO_KM;
    @Basic
    private Double MONTO_HORA;
}
