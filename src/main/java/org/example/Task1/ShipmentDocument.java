package org.example.Task1;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

/**
 * Документ отгрузки со склада.
 * Бывает двух типов: перемещение (на другой склад) и продажа (покупателю).
 */
@RequiredArgsConstructor
public abstract class ShipmentDocument {
    private final String id; // GUID документа
    private final LocalDate date; // дата документа
    private final Storage storage; // название склада отгрузки
    private final List<ShipmentLine> lines;

    /**
     * Тип документа
     * SALE - продажа
     * MOVEMENT - перемещение
     */
    public enum DocumentType {
        SALE,
        MOVEMENT
    }

    public abstract DocumentType getType();

    public final String getId() { return id; }

    public final LocalDate getDate() { return date; }

    public final Storage getStorage() { return storage; }

    public final List<ShipmentLine> getLines() { return Collections.unmodifiableList(lines); }

    public final double getItemsCount() { return lines.size(); }
}
