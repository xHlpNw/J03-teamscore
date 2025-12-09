package ru.teamscore.Task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlateArrayGeneratorTest {
    PlateArrayGenerator gen = new PlateArrayGenerator();

    @Test
    void testLength() {
        String[] array = gen.generate(25);
        assertNotNull(array);
        assertEquals(25, array.length);
    }

    @Test
    void generatedValuesNotNull() {
        String[] array = gen.generate(100);
        for (String val : array) {
            assertNotNull(val);
        }
    }

    @Test
    void correctFormat() {
        String plateRegex = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$";
        String[] array = gen.generate(100);
        for (String val : array) {
            assertTrue(val.matches(plateRegex));
        }
    }
}
