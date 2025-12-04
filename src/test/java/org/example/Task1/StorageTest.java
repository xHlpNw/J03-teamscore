package org.example.Task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageTest {
    @Test
    void testCreation() {
        Storage storage = new Storage("Main Warehouse", "Owner1");

        assertNotNull(storage);
        assertEquals("Main Warehouse", storage.getName());
        assertEquals("Owner1", storage.getOwner());
    }

    @Test
    void testSetters() {
        Storage storage = new Storage("Main Warehouse", "Owner1");

        storage.setName("Updated Warehouse");
        storage.setOwner("Owner2");

        assertEquals("Updated Warehouse", storage.getName());
        assertEquals("Owner2", storage.getOwner());
    }
}
