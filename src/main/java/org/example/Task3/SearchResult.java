package org.example.Task3;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SearchResult {
    private final String code;
    private final String title;
    private final BigDecimal finalPrice;
    private final String supplierName;
    private final String supplierAddress;
    private final String manufacturerName;
}
