package com.group_6.book_store.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private Long id;
    private String name;
    private String bio;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}