package ru.teamscore.Task3;

public class ArticleMapper {
    public static SearchResult map(Article article) {
        return new SearchResult(
                article.getCode(),
                article.getTitle(),
                article.getSupplier().calculateFinalPrice(
                        article.getBasePrice()
                ),
                article.getSupplier().getName(),
                article.getSupplier().getAddress(),
                article.getSupplier().getManufacturer().getName()
        );
    }
}
