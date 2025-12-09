package ru.teamscore.Task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiceArrayGeneratorTest {
    DiceArrayGenerator gen = new DiceArrayGenerator();

    @Test
    void testLength() {
        Integer[] array = gen.generate(25);
        assertNotNull(array);
        assertEquals(25, array.length);
    }

    @Test
    void valuesRange() {
        Integer[] array = gen.generate(1000);
        for (Integer val : array) {
            assertNotNull(val);
            assertTrue(val >= 1 && val <= 6);
        }
    }

    @Test
    void zeroLengthArray() {
        Integer[] array = gen.generate(0);
        assertNotNull(array);
        assertEquals(0, array.length);
    }
}
