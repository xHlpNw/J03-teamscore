package org.example.Task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BooleanArrayGeneratorTest {
    BooleandArrayGenerator gen = new BooleandArrayGenerator();

    @Test
    void testLength() {
        Boolean[] array = gen.generate(25);
        assertNotNull(array);
        assertEquals(25, array.length);
    }

    @Test
    void generatedValuesNotNull() {
        Boolean[] array = gen.generate(100);
        for (Boolean val : array) {
            assertNotNull(val);
        }
    }

    @Test
    void zeroLengthArray() {
        Boolean[] array = gen.generate(0);
        assertNotNull(array);
        assertEquals(0, array.length);
    }
}
