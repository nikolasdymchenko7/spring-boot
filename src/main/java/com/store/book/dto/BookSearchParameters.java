package com.store.book.dto;

public record BookSearchParameters(String[] titles, String[] authors,
                                   String[] prices, String[] isbns) {
}
