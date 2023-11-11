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
@DiscriminatorValue("1")
public class TarifaSemana extends Tarifa{
    @Column(name = "DIA_SEMANA")
    private Integer diaSemana;
}
