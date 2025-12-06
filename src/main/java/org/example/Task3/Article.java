package org.example.Task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Article {
    private final String code;
    private String title;
    private BigDecimal basePrice;
    private final Supplier supplier;
}
