package ru.teamscore.Task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemTest {
    @Test
    void testCreation() {
        Item item = new Item("id1", "A123", "Test Item");

        assertNotNull(item);
        assertEquals("id1", item.getId());
        assertEquals("A123", item.getArticle());
        assertEquals("Test Item", item.getTitle());
    }

    @Test
    void testSetters() {
        Item item = new Item("id1", "A123", "Test Item");

        item.setId("id2");
        item.setArticle("B456");
        item.setTitle("Updated Item");

        assertEquals("id2", item.getId());
        assertEquals("B456", item.getArticle());
        assertEquals("Updated Item", item.getTitle());
    }
}
