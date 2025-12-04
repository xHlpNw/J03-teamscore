package org.example.Task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayGeneratorServiceTest {
    @Test
    void invalidGeneratorType() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> new ArrayGeneratorService("invalidType")
        );

        assertEquals(
                "Некорректный тип генератора: invalidType",
                e.getMessage()
        );
    }

    private void assertJson(String type) {
        ArrayGeneratorService service = new ArrayGeneratorService(type);
        String json = service.generateJson(10);
        assertNotNull(json);
        assertFalse(json.isEmpty());
        assertTrue(json.startsWith("[") && json.endsWith("]"));
    }

    @Test
    void diceJsonGenerator() {
        assertJson("dice");
    }

    @Test
    void booleanJsonGenerator() {
        assertJson("boolean");
    }

    @Test
    void plateJsonGenerator() {
        assertJson("plate");
    }

    @Test
    void generateZeroLengthArray() {
        ArrayGeneratorService service = new ArrayGeneratorService("dice");
        String json = service.generateJson(0);
        assertEquals("[]", json);
    }
}
