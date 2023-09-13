package com.store.book.dto.book;

public record BookSearchParameters(String[] titles, String[] authors,
                                   String[] prices, String[] isbns) {
}
