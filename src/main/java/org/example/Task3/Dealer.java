package org.example.Task3;

import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Setter
public class Dealer extends Supplier {
    private final Manufacturer manufacturer;
    private BigDecimal markupPercent;

    public Dealer(
            String tin,
            String name,
            String address,
            Manufacturer manufacturer,
            BigDecimal markupPercent
    ) {
        super(tin, name, address);
        this.manufacturer = manufacturer;
        this.markupPercent = markupPercent;
    }

    @Override
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
        return basePrice.multiply(markupPercent)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
