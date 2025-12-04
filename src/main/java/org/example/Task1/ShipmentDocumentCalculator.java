package org.example.Task1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ShipmentDocumentCalculator {
    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    public double promoSum(ShipmentDocument doc, String[] promoArticles, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("Скидка должна быть в диапазоне 0-100%.");
        HashSet<String> promo = new HashSet<>(Arrays.asList(promoArticles));
        List<ShipmentLine> lines = doc.getLines();
        BigDecimal sum = BigDecimal.ZERO;
        boolean isSale = doc.getType() == ShipmentDocument.DocumentType.SALE;

        for (ShipmentLine line : lines) {
            if (promo.contains(line.getItem().getArticle())) {
                BigDecimal amount = line.amount();
                if (isSale) {
                    BigDecimal discountCoefficient = BigDecimal.ONE.subtract(
                            BigDecimal.valueOf(discountPercent)
                                    .divide(BigDecimal.valueOf(100)));
                    amount = amount.multiply(discountCoefficient)
                            .setScale(2, RoundingMode.HALF_UP);
                }
                sum = sum.add(amount);
            }
        }
        return sum.doubleValue();
    }

    /**
     * Стоимость товара с переданным id.
     */
    public double itemAmount(ShipmentDocument doc, String id) {
        List<ShipmentLine> lines = doc.getLines();
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
        if (!Double.isFinite(minQuantity))
            throw new IllegalArgumentException("Минимальное коичество должно быть конечным значением");
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
