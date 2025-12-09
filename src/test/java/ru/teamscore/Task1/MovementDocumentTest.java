package ru.teamscore.Task1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Nested
    class PromoSumTest {
        MovementDocument movementDocument = new MovementDocument(
                testId,
                testDate,
                storage,
                movingStorage,
                List.of(
                        new ShipmentLine(
                                new Item("id1", "art1", "title1"),
                                new BigDecimal("10.001"),
                                new BigDecimal("35.99")
                                // 10.001 * 35,99 = 359.93599
                        ),
                        new ShipmentLine(
                                new Item("id2", "art2", "title2"),
                                new BigDecimal("3"),
                                new BigDecimal("5")
                        ),
                        new ShipmentLine(
                                new Item("id2", "art2", "title2"),
                                new BigDecimal("4"),
                                new BigDecimal("5")
                        )
                )
        );

        @Test
        void emptyPromoList() {
            assertEquals(
                    0,
                    movementDocument.promoSum(
                            new String[]{}
                    )
            );
        }

        @Test
        void promoSum() {
            assertEquals(
                    35,
                    movementDocument.promoSum(
                            new String[]{ "art2" }
                    )
            );
            assertEquals(
                    394.94,
                    movementDocument.promoSum(
                            new String[]{ "art2", "art1" }
                    )
            );
        }

    }

    @Nested
    class IsInternalMovementTest {
        @Test
        void notInternalMovement() {
            MovementDocument document = new MovementDocument(
                    testId,
                    testDate,
                    storage,
                    movingStorage,
                    List.of(
                            new ShipmentLine(
                                    new Item("id1", "art1", "title1"),
                                    new BigDecimal("10.001"),
                                    new BigDecimal("35.99")
                                    // 10.001 * 35,99 = 359.93599
                            ),
                            new ShipmentLine(
                                    new Item("id2", "art2", "title2"),
                                    new BigDecimal("3"),
                                    new BigDecimal("5")
                            ),
                            new ShipmentLine(
                                    new Item("id2", "art2", "title2"),
                                    new BigDecimal("4"),
                                    new BigDecimal("5")
                            )
                    )
            );
            assertFalse(document.isInternalMovement());
        }

        @Test
        void oneStorageMovement() {
            assertTrue(new MovementDocument(
                    testId,
                    testDate,
                    storage,
                    storage,
                    new ArrayList<>()
            ).isInternalMovement());
        }

        @Test
        void sameOwnerMovement() {
            assertTrue(new MovementDocument(
                    testId,
                    testDate,
                    storage,
                    new Storage("NotMain", "Owner"),
                    new ArrayList<>()
            ).isInternalMovement());
        }

        @Test
        void sameStorageNameMovement() {
            assertFalse(new MovementDocument(
                    testId,
                    testDate,
                    storage,
                    new Storage("Main", "OtherOwner"),
                    new ArrayList<>()
            ).isInternalMovement());
        }
    }
}
