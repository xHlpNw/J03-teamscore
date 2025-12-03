package org.example.Task1;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MovementDocument extends ShipmentDocument {
    private final Storage movingStorage; // название склада получения (только для перемещения)

    public MovementDocument(
            String id,
            LocalDate date,
            Storage storage,
            Storage movingStorage,
            List<ShipmentLine> lines
    ) {
        super(id, date, storage, lines);
        this.movingStorage = movingStorage;
    }

    @Override
    public DocumentType getType() { return DocumentType.MOVEMENT; }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     * Для продаж неприменимо!
     */
    boolean isInternalMovement() {
        return getStorage().getOwner().equals(movingStorage.getOwner());
    }
}
