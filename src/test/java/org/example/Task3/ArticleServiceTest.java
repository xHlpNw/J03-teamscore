package org.example.Task3;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleServiceTest {
    @Test
    void addArticleAddsSuccessfully() {
        ArticleService service = new ArticleService();
        Article a = new Article(
                "C1",
                "Title",
                new BigDecimal("10"),
                new Manufacturer("1","M","A")
        );
        service.addArticle(a);
        assertTrue(service.searchByCode("C1").isPresent());
    }

    @Test
    void addArticleThrowsOnDuplicate() {
        ArticleService service = new ArticleService();
        Article a = new Article("C1",
                "T",
                new BigDecimal("10"),
                new Manufacturer("1","M","A")
        );
        service.addArticle(a);
        assertThrows(IllegalArgumentException.class, () -> service.addArticle(a));
    }

    @Test
    void searchByCodeReturnsMappedResult() {
        ArticleService service = new ArticleService();
        Manufacturer m = new Manufacturer("1","M","A");
        Article a = new Article("C1","T", new BigDecimal("100"), m);
        service.addArticle(a);

        Optional<SearchResult> r = service.searchByCode("C1");
        assertTrue(r.isPresent());
        assertEquals("C1", r.get().getCode());
        assertEquals(new BigDecimal("100"), r.get().getFinalPrice());
    }

    @Test
    void searchByCodeReturnsEmptyForMissing() {
        ArticleService service = new ArticleService();
        assertTrue(service.searchByCode("X").isEmpty());
    }

    @Test
    void searchByTitleFindsCaseInsensitiveSubstring() {
        ArticleService service = new ArticleService();
        Manufacturer m = new Manufacturer("1","M","A");

        service.addArticle(
                new Article("C1","Smartphone X", new BigDecimal("10"), m)
        );
        service.addArticle(
                new Article("C2","SMART Home", new BigDecimal("20"), m)
        );
        service.addArticle(
                new Article("C3","Other", new BigDecimal("30"), m)
        );

        List<SearchResult> r = service.searchByTitle("smart");
        assertEquals(2, r.size());
        assertTrue(r.stream().anyMatch(x -> x.getCode().equals("C1")));
        assertTrue(r.stream().anyMatch(x -> x.getCode().equals("C2")));
    }

    @Test
    void searchByTitleReturnsEmptyListIfNoneMatch() {
        ArticleService service = new ArticleService();
        Manufacturer m = new Manufacturer("1","M","A");

        service.addArticle(
                new Article("C1","Phone", new BigDecimal("10"), m)
        );

        assertTrue(service.searchByTitle("TV").isEmpty());
    }
}
