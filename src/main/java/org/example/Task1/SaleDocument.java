package org.example.Task1;

import lombok.Getter;

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
}
