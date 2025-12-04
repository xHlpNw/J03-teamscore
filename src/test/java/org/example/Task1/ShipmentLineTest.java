package org.example.Task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShipmentLineTest {
    @Test
    void testShipmentLineCreation() {
        Item item = new Item("id1", "A1", "Test Item");
        ShipmentLine line = new ShipmentLine(item, BigDecimal.valueOf(3), BigDecimal.valueOf(10.5));

        assertNotNull(line);
        assertEquals(item, line.getItem());
        assertEquals(new BigDecimal("3"), line.getQuantity());
        assertEquals(new BigDecimal("10.5"), line.getPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 15.00, 30.00",
            "2.01, 3.99, 8.02", // 8.0199
            "1.01, 5.5, 5.56" // 5.555
    })
    void testAmountCalculation(
            String quantity,
            String price,
            String expectedAmount) {
        ShipmentLine line = new ShipmentLine(
                new Item("id1", "A1", "Test"),
                new BigDecimal(quantity),
                new BigDecimal(price)
        );

        assertEquals(
                new BigDecimal(expectedAmount),
                line.amount());
    }
}
