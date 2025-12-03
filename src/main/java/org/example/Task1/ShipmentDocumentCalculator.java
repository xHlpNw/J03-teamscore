package org.example.Task1;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ShipmentDocumentCalculator {
    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    public double promoSum(ShipmentDocument doc, String[] promoArticles) {
        HashSet<String> promo = new HashSet<>(Arrays.asList(promoArticles));
        List<ShipmentLine> lines = doc.getLines();
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
    public double itemAmount(ShipmentDocument doc, String id) {
        List<ShipmentLine> lines = doc.getLines();
        for (ShipmentLine line : lines) {
            if (Objects.equals(line.getItem().getId(), id)) {
                return line.amount().doubleValue();
            }
        }
        return 0;
    }

    /**
     * Суммарная стоимость товаров в документе.
     */
    public double totalAmount(ShipmentDocument doc) {
        List<ShipmentLine> lines = doc.getLines();
        BigDecimal sum = BigDecimal.ZERO;
        for (ShipmentLine line : lines) {
            sum = sum.add(line.amount());
        }
        return sum.doubleValue();
    }

    /**
     * Является ли продажа оптовой для переданного минимального объема.
     * Для перемещений неприменимо!
     */
    boolean isWholesale(SaleDocument doc, double minQuantity) {
        List<ShipmentLine> lines = doc.getLines();
        BigDecimal sumQuantity = BigDecimal.ZERO;
        BigDecimal min = BigDecimal.valueOf(minQuantity);

        for (ShipmentLine line : lines) {
            if (line.getQuantity().compareTo(min) >= 0) {
                return true;
            }
            sumQuantity = sumQuantity.add(line.getQuantity());
        }
        return sumQuantity.compareTo(min) >= 0;
    }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     * Для продаж неприменимо!
     */
    boolean isInternalMovement(MovementDocument doc) {
        return doc.getStorage().getOwner()
                .equals(doc.getMovingStorage().getOwner());
    }
}
