package ru.teamscore.Task1;

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

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    public double promoSum(String[] promoArticles) {
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
     * Стоимость товара с переданным id.
     */
    public double itemAmount(String id) {
        BigDecimal amount = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            if (Objects.equals(line.getItem().getId(), id)) {
                amount = amount.add(line.amount());
            }
        }
        return amount.doubleValue();
    }

    /**
     * Суммарная стоимость товаров в документе.
     */
    public double totalAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            sum = sum.add(line.amount());
        }
        return sum.doubleValue();
    }
}
