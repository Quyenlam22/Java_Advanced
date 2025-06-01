package com.group_6.book_store.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Long authorId;
    private String imageUrl;
}