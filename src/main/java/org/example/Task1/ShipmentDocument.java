package org.example.Task1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
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
     * Суммарная стоимость товаров в документе.
     */
    double totalAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            sum = sum.add(line.amount());
        }
        return sum.doubleValue();
    }

    /**
     * Стоимость товара с переданным id.
     */
    double itemAmount(String id) {
        for (ShipmentLine line : lines) {
            if (Objects.equals(line.getItem().getId(), id)) {
                return line.amount().doubleValue();
            }
        }
        return 0;
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    double promoSum(String[] promoArticles) {
        HashSet<String> promo = new HashSet<>(Arrays.asList(promoArticles));
        BigDecimal sum = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            if (promo.contains(line.getItem().getArticle())) {
                sum = sum.add(line.amount());
            }
        }
        return sum.doubleValue();
    }

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

    public final List<ShipmentLine> getLines() { return lines; }

    public final void addLine(ShipmentLine line) {
        lines.add(line);
    }

    public final void removeLine(ShipmentLine line) {
        lines.remove(line);
    }

    public final ShipmentLine getLine(int index) {
        return lines.get(index);
    }

    public final double getItemsCount() { return lines.size(); }
}
