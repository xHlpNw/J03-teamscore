package ru.teamscore.Task1;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
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
        if (!Double.isFinite(minQuantity))
            throw new IllegalArgumentException("Минимальное коичество должно быть конечным значением");
        BigDecimal sumQuantity = BigDecimal.ZERO;
        BigDecimal min = BigDecimal.valueOf(minQuantity);

        for (ShipmentLine line : getLines()) {
            if (line.getQuantity().compareTo(min) >= 0) {
                return true;
            }
            sumQuantity = sumQuantity.add(line.getQuantity());
        }
        return sumQuantity.compareTo(min) >= 0;
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    public double promoSum(String[] promoArticles, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("Скидка должна быть в диапазоне 0-100%.");
        HashSet<String> promo = new HashSet<>(Arrays.asList(promoArticles));
        List<ShipmentLine> lines = getLines();
        BigDecimal sum = BigDecimal.ZERO;

        for (ShipmentLine line : lines) {
            if (promo.contains(line.getItem().getArticle())) {
                BigDecimal amount = line.amount();
                BigDecimal discountCoefficient = BigDecimal.ONE.subtract(
                    BigDecimal.valueOf(discountPercent)
                            .divide(BigDecimal.valueOf(100)));
                amount = amount.multiply(discountCoefficient)
                        .setScale(2, RoundingMode.HALF_UP);
                sum = sum.add(amount);
            }
        }
        return sum.doubleValue();
    }
}
