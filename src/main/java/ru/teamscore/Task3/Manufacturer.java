package ru.teamscore.Task3;

import java.math.BigDecimal;

public class Manufacturer extends Supplier {
    public Manufacturer(String tin, String name, String address) {
        super(tin, name, address);
    }

    @Override
    public Manufacturer getManufacturer() {
        return this;
    }

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
        return basePrice;
    }
}
