package ru.teamscore.Task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShipmentDocumentTest {
    private static class TestDocument extends ShipmentDocument {
        public TestDocument(List<ShipmentLine> lines) {
            super(
                    "test_id",
                    LocalDate.of(2025, 12, 4),
                    new Storage("Main", "Owner"),
                    lines
            );
        }

        @Override
        public DocumentType getType() {
            return null;
        }
    }

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

    @Test
    void testCreation() {
        TestDocument doc = new TestDocument(new ArrayList<ShipmentLine>());

        assertEquals("test_id", doc.getId());
        assertEquals(LocalDate.of(2025, 12, 4), doc.getDate());
        assertEquals(new Storage("Main", "Owner"), doc.getStorage());
        assertEquals(0, doc.getItemsCount());
    }

    @Test
    void testGetLinesUnmodifiable() {
        List<ShipmentLine> mutable = new ArrayList<>();
        ShipmentDocument doc = new TestDocument(mutable);
        List<ShipmentLine> returned = doc.getLines();

        Assertions.assertNotSame(mutable, returned);

        assertThrows(
                UnsupportedOperationException.class,
                () -> returned.add(new ShipmentLine(null, null, null))
        );
    }

    @ParameterizedTest
    @MethodSource("lineSets")
    void testGetItemsCount(List<ShipmentLine> lines) {
        Storage storage = new Storage("Main", "Owner");
        ShipmentDocument doc = new TestDocument(new ArrayList<>(lines));

        assertEquals(lines.size(), doc.getItemsCount());
        assertEquals(lines, doc.getLines());
    }

    @Nested
    class ItemAmountTest {
        @Test
        void didNotContainItem() {
            TestDocument testDocument = new TestDocument(
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
                            )
                    )
            );
            assertEquals(0, testDocument.itemAmount("id3"));
        }

        @Test
        void singleLineWithItem() {
            TestDocument testDocument = new TestDocument(
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
                            )
                    )
            );
            assertEquals(359.94, testDocument.itemAmount("id1"));
        }

        @Test
        void severalLinesWithItem() {
            TestDocument testDocument = new TestDocument(
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
            assertEquals(35, testDocument.itemAmount("id2"));
        }
    }

    @Nested
    class TotalAmountTest {
        @Test
        void totalAmount() {
            TestDocument testDocument = new TestDocument(
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
                            )
                    )
            );
            assertEquals(
                    374.94,
                    testDocument.totalAmount()
            );
        }


        @Test
        void emptyDocAmount() {
            TestDocument testDocument = new TestDocument(
                    new ArrayList<>()
            );
            assertEquals(0, testDocument.totalAmount());
        }
    }
}
