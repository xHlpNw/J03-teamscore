package org.example.Task1;

import java.util.*;

/**
 * Документ отгрузки со склада.
 * Бывает двух типов: перемещение (на другой склад) и продажа (покупателю).
 *
 */
class ShipmentDocument {
    String documentId; // GUID документа
    Date documentDate; // дата документа
    String documentType; // тип отгрузки: sale - продажа, moving - перемещение
    String storage; // название склада отгрузки
    String storageOwner; // владелец склада отгрузки
    String customer; // получатель (только для продажи)
    String movingStorage; // название склада получения (только для перемещения)
    String movingStrageOwner; // владелец склада получения (только для перемещения)
    int itemsCount; // количество товаров в документе
    String[] itemsId; // список GUID товаров
    String[] itemsArticle; // список артикулов товаров
    String[] itemsTitle; // список наваний товаров
    double[] itemsQuantity; // список количества шт. товаров
    double[] itemsPrice; // список цен товаров

    /**
     * Суммарная стоимость товаров в документе.
     */
    double totalAmount() {
        double sum = 0;
        for (int i = 0; i < itemsCount; i++) {
            sum += Math.round(itemsQuantity[i] * itemsPrice[i] * 100) / 100.0;
        }
        return sum;
    }

    /**
     * Стоимость товара с переданным id.
     */
    double itemAmount(String id) {
        for (int i = 0; i < itemsCount; i++) {
            if (itemsId[i] == id) {
                return Math.round(itemsQuantity[i] * itemsPrice[i] * 100) / 100.0;
            }
        }
        return 0;
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    double promoSum(String[] promoArticles) {
        double sum = 0;
        for (int i = 0; i < itemsCount; i++) {
            for (int j = 0; j < promoArticles.length; j++) {
                if (itemsArticle[i] == promoArticles[j]) {
                    sum += Math.round(itemsQuantity[i] * itemsPrice[i] * 100) / 100.0;
                    break;
                }
            }
        }
        return sum;
    }

    /**
     * Является ли продажа оптовой для переданного минимального объема.
     * Для перемещений неприменимо!
     */
    boolean isWholesale(double minQuantity) {
        if (documentType.equals("moving")) {
            return false;
        }
        double sumQuantity = 0;
        for (int i = 0; i < itemsCount; i++) {
            if (itemsQuantity[i] >= minQuantity) {
                return true;
            }
            sumQuantity += itemsQuantity[i];
        }
        return sumQuantity >= minQuantity;
    }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     * Для продаж неприменимо!
     */
    boolean isInternalMovement() {
        return documentType.equals("moving") && storageOwner.equals(movingStrageOwner);
    }
}
