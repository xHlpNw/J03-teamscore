package org.example.Task1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentDocumentCalculatorTest {

    ShipmentDocumentCalculator calc = new ShipmentDocumentCalculator();

    String testId = "test_id";
    LocalDate testDate = LocalDate.of(2025, 12, 4);
    Storage testStorage = new Storage("Main", "Owner");
    Storage testMovingStorage = new Storage("Reserve", "Owner2");
    String testCustomer = "Customer";

    MovementDocument movementDocument = new MovementDocument(
            testId,
            testDate,
            testStorage,
            testMovingStorage,
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

    SaleDocument saleDocument = new SaleDocument(
            testId,
            testDate,
            testStorage,
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



    @Nested
    class PromoSumTest {
        @Test
        void zeroDiscount() {
            assertEquals(
                    374.94,
                    calc.promoSum(
                            saleDocument,
                            new String[]{"art2", "art1"},
                            0
                    )
            );
        }

        @Test
        void maxDiscount() {
            assertEquals(
                    0,
                    calc.promoSum(
                            saleDocument,
                            new String[]{"art2", "art1"},
                            100
                    )
            );
        }

        @Test
        void emptyPromoList() {
            assertEquals(
                    0,
                    calc.promoSum(
                            saleDocument,
                            new String[]{},
                            10
                    )
            );
        }

        @Test
        void promoSumSaleDoc() {
            assertEquals(
                    13.5,
                    calc.promoSum(
                            saleDocument,
                            new String[]{ "art2" },
                            10
                    )
            );
            assertEquals(
                    299.95,
                    calc.promoSum(
                            saleDocument,
                            new String[]{ "art2", "art1" },
                            20
                    )
            );
        }

        @Test
        void promoSumMovementDoc() {
            assertEquals(
                    35,
                    calc.promoSum(
                            movementDocument,
                            new String[]{ "art2" },
                            10
                    )
            );
            assertEquals(
                    394.94,
                    calc.promoSum(
                            movementDocument,
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
                        () -> calc.promoSum(saleDocument, new String[]{}, -1)
                );
            }

            @Test
            void testDiscountAbove100() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calc.promoSum(saleDocument, new String[]{}, 101)
                );
            }
        }
    }

    @Nested
    class ItemAmountTest {
        @Test
        void didNotContainItem() {
            assertEquals(0, calc.itemAmount(saleDocument, "id3"));
        }

        @Test
        void singleLineWithItem() {
            assertEquals(359.94, calc.itemAmount(saleDocument, "id1"));
        }

        @Test
        void severalLinesWithItem() {
            assertEquals(35, calc.itemAmount(movementDocument, "id2"));
        }
    }

    @Nested
    class TotalAmountTest {
        @Test
        void saleDocumentAmount() {
            assertEquals(
                    374.94,
                    calc.totalAmount(saleDocument)
            );
        }

        @Test
        void movementDocumentAmount() {
            assertEquals(
                    394.94,
                    calc.totalAmount(movementDocument)
            );
        }

        @Test
        void emptyDocAmount() {
            SaleDocument doc = new SaleDocument(
                    testId,
                    testDate,
                    testStorage,
                    testCustomer,
                    new ArrayList<>()
            );
            assertEquals(0, calc.totalAmount(doc));
        }
    }

    @Nested
    class IsWholesaleTest {
        @Test
        void zeroMinQuantity() {
            assertTrue(calc.isWholesale(saleDocument, 0));
        }

        @Test
        void minQuantityIsBigger() {
            assertFalse(calc.isWholesale(saleDocument, 100));
        }

        @Test
        void minQuantityIsSmaller() {
            assertTrue(calc.isWholesale(saleDocument, 1));
        }

        @Nested
        class IncorrectMinQuantity {
            @Test
            void InfinityMinQuantity() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calc.isWholesale(saleDocument, Double.POSITIVE_INFINITY)
                );
            }

            @Test
            void NanMinQuantity() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calc.isWholesale(saleDocument, Double.POSITIVE_INFINITY)
                );
            }
        }
    }

    @Nested
    class IsInternalMovementTest {
        @Test
        void notInternalMovement() {
            assertFalse(calc.isInternalMovement(movementDocument));
        }

        @Test
        void oneStorageMovement() {
            assertTrue(calc.isInternalMovement(
                    new MovementDocument(
                            testId,
                            testDate,
                            testStorage,
                            testStorage,
                            new ArrayList<>()
                    )
            ));
        }

        @Test
        void sameOwnerMovement() {
            assertTrue(calc.isInternalMovement(
                    new MovementDocument(
                            testId,
                            testDate,
                            testStorage,
                            new Storage("NotMain", "Owner"),
                            new ArrayList<>()
                    )
            ));
        }

        @Test
        void sameStorageNameMovement() {
            assertFalse(calc.isInternalMovement(
                    new MovementDocument(
                            testId,
                            testDate,
                            testStorage,
                            new Storage("Main", "OtherOwner"),
                            new ArrayList<>()
                    )
            ));
        }
    }
}
