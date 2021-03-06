package com.bookshop.warehouse.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id private String id;
    private String isbn;
    private String title;
    private String author;
}
