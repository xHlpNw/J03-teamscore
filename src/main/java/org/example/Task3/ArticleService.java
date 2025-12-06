package org.example.Task3;

import java.util.List;
import java.util.Optional;

import java.util.HashMap;

public class ArticleService {
    private final HashMap<String, Article> articles = new HashMap<>();

    public void addArticle(Article article) {
        if (articles.containsKey(article.getCode())) {
            throw new IllegalArgumentException("This article is duplicate");
        }
        articles.put(article.getCode(), article);
    }

    public Optional<SearchResult> searchByCode(String code) {
        return Optional.ofNullable(articles.get(code))
                .map(ArticleMapper::map);
    }

    public List<SearchResult> searchByTitle(String title) {
        String lowerCasedTitle = title.toLowerCase();

        return articles.values().stream()
                .filter(article -> article.getTitle().toLowerCase()
                        .contains(lowerCasedTitle))
                .map(ArticleMapper::map).toList();
    }
}
