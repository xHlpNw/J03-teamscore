package ru.teamscore.Task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public abstract class Supplier {
    private final String tin;
    private String name;
    private String address;

    abstract Manufacturer getManufacturer();

    abstract BigDecimal calculateFinalPrice(BigDecimal basePrice);
}
