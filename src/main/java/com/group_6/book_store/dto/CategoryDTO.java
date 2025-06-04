package com.group_6.book_store.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
}