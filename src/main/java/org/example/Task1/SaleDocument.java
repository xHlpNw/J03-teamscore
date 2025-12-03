package org.example.Task1;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class SaleDocument extends ShipmentDocument {
    private final String customer; // получатель (только для продажи)

    public SaleDocument(
            String id,
            LocalDate date,
            Storage storage,
            String customer,
            List<ShipmentLine> lines
    ) {
        super(id, date, storage, lines);
        this.customer = customer;
    }

    @Override
    public DocumentType getType() { return DocumentType.SALE; }

    /**
     * Является ли продажа оптовой для переданного минимального объема.
     * Для перемещений неприменимо!
     */
    boolean isWholesale(double minQuantity) {
        BigDecimal sumQuantity = BigDecimal.ZERO;
        List<ShipmentLine> lines = getLines();
        for (ShipmentLine line : lines) {
            if (line.getQuantity().compareTo(BigDecimal.valueOf(minQuantity)) >= 0) {
                return true;
            }
            sumQuantity = sumQuantity.add(line.getQuantity());
        }
        return sumQuantity.compareTo(BigDecimal.valueOf(minQuantity)) >= 0;
    }
}
