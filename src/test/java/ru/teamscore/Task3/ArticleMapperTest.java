package ru.teamscore.Task3;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleMapperTest {
    @Test
    void mapMapsAllFields() {
        Manufacturer m = new Manufacturer("111","M1","A1");
        Dealer d = new Dealer("222","D1","A2", m, new BigDecimal("0.10"));
        Article a = new Article("C1","T1", new BigDecimal("100"), d);

        SearchResult r = ArticleMapper.map(a);

        assertEquals("C1", r.getCode());
        assertEquals("T1", r.getTitle());
        assertEquals(new BigDecimal("110.00"), r.getFinalPrice());
        assertEquals("D1", r.getSupplierName());
        assertEquals("A2", r.getSupplierAddress());
        assertEquals("M1", r.getManufacturerName());
    }
}
