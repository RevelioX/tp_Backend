package TP.AlquileresMicroServicio.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("2")
public class TarifaPromocion extends Tarifa{
    @Column(name = "DIA_MES")
    private Integer dia;
    @Column(name = "MES")
    private Integer mes;
    @Column(name = "ANIO")
    private Integer anio;
}
