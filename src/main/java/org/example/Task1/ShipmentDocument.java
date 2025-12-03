package org.example.Task1;

import java.math.BigDecimal;
import java.util.*;

/**
 * Документ отгрузки со склада.
 * Бывает двух типов: перемещение (на другой склад) и продажа (покупателю).
 */
class ShipmentDocument {
    String documentId; // GUID документа
    Date documentDate; // дата документа
    String documentType; // тип отгрузки: sale - продажа, moving - перемещение
    Storage storage; // название склада отгрузки
    String customer; // получатель (только для продажи)
    Storage movingStorage; // название склада получения (только для перемещения)
    int itemsCount; // количество товаров в документе
    private final List<ShipmentLine> lines = new ArrayList<>();

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
     * Является ли продажа оптовой для переданного минимального объема.
     * Для перемещений неприменимо!
     */
    boolean isWholesale(double minQuantity) {
        if (documentType.equals("moving")) {
            return false;
        }
        BigDecimal sumQuantity = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            if (line.getQuantity().compareTo(BigDecimal.valueOf(minQuantity)) >= 0) {
                return true;
            }
            sumQuantity = sumQuantity.add(line.getQuantity());
        }
        return sumQuantity.compareTo(BigDecimal.valueOf(minQuantity)) >= 0;
    }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     * Для продаж неприменимо!
     */
    boolean isInternalMovement() {
        return documentType.equals("moving") && storage.getOwner().equals(movingStorage.getOwner());
    }
}
