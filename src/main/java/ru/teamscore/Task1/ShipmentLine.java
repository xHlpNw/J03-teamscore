package ru.teamscore.Task1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@Getter
@Setter
public class ShipmentLine {
    private final Item item;
    private final BigDecimal quantity;
    private final BigDecimal price;

    public BigDecimal amount() {
        return price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }
}
