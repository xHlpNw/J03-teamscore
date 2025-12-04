package org.example.Task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovementDocumentTest {
    Storage storage = new Storage("Main", "Owner");
    String testId = "test_id";
    LocalDate testDate = LocalDate.of(2025, 12, 4);
    Storage movingStorage = new Storage("Reserve", "Owner2");

    static List<List<ShipmentLine>> lineSets() {
        return List.of(
                List.of(), // empty
                List.of(new ShipmentLine(
                        new Item("id1", "A1", "X"),
                        BigDecimal.ONE,
                        BigDecimal.TEN
                )),
                List.of(
                        new ShipmentLine(
                                new Item("id1", "A1", "X"),
                                BigDecimal.ONE,
                                BigDecimal.TEN),
                        new ShipmentLine(
                                new Item("id2", "A2", "Y"),
                                BigDecimal.valueOf(3),
                                BigDecimal.valueOf(2))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("lineSets")
    void testConstructor(List<ShipmentLine> lines) {
        MovementDocument doc = new MovementDocument(
                testId,
                testDate,
                storage,
                movingStorage,
                lines
        );

        assertNotNull(doc);
        assertEquals(testId, doc.getId());
        assertEquals(testDate, doc.getDate());
        assertEquals(movingStorage, doc.getMovingStorage());
        assertEquals(storage, doc.getStorage());
        assertEquals(lines.size(), doc.getItemsCount());
        assertEquals(lines, doc.getLines());
    }

    @Test
    void testType() {
        MovementDocument doc = new MovementDocument(
                testId,
                testDate,
                storage,
                movingStorage,
                new ArrayList<>()
        );

        assertEquals(ShipmentDocument.DocumentType.MOVEMENT, doc.getType());
    }
}
