package org.example.Task3;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SupplierTests {
    @Test
    void manufacturerReturnsItself() {
        Manufacturer m = new Manufacturer("111", "M1", "Addr");
        assertSame(m, m.getManufacturer());
    }

    @Test
    void manufacturerFinalPriceIsBasePrice() {
        Manufacturer m = new Manufacturer("111", "M1", "Addr");
        BigDecimal base = new BigDecimal("100.50");
        assertEquals(base, m.calculateFinalPrice(base));
    }

    @Test
    void dealerReturnsManufacturer() {
        Manufacturer man = new Manufacturer("111", "M1", "Addr");
        Dealer d = new Dealer(
                "222",
                "D1",
                "Addr2",
                man,
                new BigDecimal("0.20")
        );
        assertSame(man, d.getManufacturer());
    }

    @Test
    void dealerCalculatesMarkupCorrectly() {
        Manufacturer man = new Manufacturer("111", "M1", "Addr");
        Dealer d = new Dealer(
                "222",
                "D1",
                "Addr2",
                man,
                new BigDecimal("0.20")
        );
        BigDecimal base = new BigDecimal("100.00");
        assertEquals(new BigDecimal("120.00"), d.calculateFinalPrice(base));
    }

    @Test
    void dealerCalculatesRoundedPrice() {
        Manufacturer man = new Manufacturer("111", "M1", "Addr");
        Dealer d = new Dealer("222", "D1", "Addr2", man, new BigDecimal("0.01"));
        BigDecimal base = new BigDecimal("0.99");
        // 1.01 * 0.99 = 0.9999
        assertEquals(new BigDecimal("1.00"), d.calculateFinalPrice(base));
    }
}
