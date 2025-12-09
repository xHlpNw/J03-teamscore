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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaleDocumentTest {
    Storage storage = new Storage("Main", "Owner");
    String testId = "test_id";
    LocalDate testDate = LocalDate.of(2025, 12, 4);
    String testCustomer = "customer";

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
        SaleDocument doc = new SaleDocument(
                testId,
                testDate,
                storage,
                testCustomer,
                lines
        );

        assertNotNull(doc);
        assertEquals(testId, doc.getId());
        assertEquals(testDate, doc.getDate());
        assertEquals(testCustomer, doc.getCustomer());
        assertEquals(storage, doc.getStorage());
        assertEquals(lines.size(), doc.getItemsCount());
        assertEquals(lines, doc.getLines());
    }

    @Test
    void testType() {
        SaleDocument doc = new SaleDocument(
                testId,
                testDate,
                storage,
                testCustomer,
                new ArrayList<>()
        );

        assertEquals(ShipmentDocument.DocumentType.SALE, doc.getType());
    }

    @Nested
    class PromoSumTest {
        SaleDocument document = new SaleDocument(
                testId,
                testDate,
                storage,
                testCustomer,
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

        @Test
        void zeroDiscount() {
            assertEquals(
                    374.94,
                    document.promoSum(
                            new String[]{"art2", "art1"},
                            0
                    )
            );
        }

        @Test
        void maxDiscount() {
            assertEquals(
                    0,
                    document.promoSum(
                            new String[]{"art2", "art1"},
                            100
                    )
            );
        }

        @Test
        void emptyPromoList() {
            assertEquals(
                    0,
                    document.promoSum(
                            new String[]{},
                            10
                    )
            );
        }

        @Test
        void promoSum() {
            assertEquals(
                    13.5,
                    document.promoSum(
                            new String[]{ "art2" },
                            10
                    )
            );
            assertEquals(
                    299.95,
                    document.promoSum(
                            new String[]{ "art2", "art1" },
                            20
                    )
            );
        }

        @Nested
        class DiscountRangeTest {
            @Test
            void testNegativeDiscount() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> document.promoSum(new String[]{}, -1)
                );
            }

            @Test
            void testDiscountAbove100() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> document.promoSum(new String[]{}, 101)
                );
            }
        }
    }

    @Nested
    class IsWholesaleTest {
        SaleDocument document = new SaleDocument(
                testId,
                testDate,
                storage,
                testCustomer,
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
        @Test
        void zeroMinQuantity() {
            assertTrue(document.isWholesale(0));
        }

        @Test
        void minQuantityIsBigger() {
            assertFalse(document.isWholesale(100));
        }

        @Test
        void minQuantityIsSmaller() {
            assertTrue(document.isWholesale(1));
        }

        @Nested
        class IncorrectMinQuantity {
            @Test
            void InfinityMinQuantity() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> document.isWholesale(Double.POSITIVE_INFINITY)
                );
            }

            @Test
            void NanMinQuantity() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> document.isWholesale(Double.POSITIVE_INFINITY)
                );
            }
        }
    }
}
