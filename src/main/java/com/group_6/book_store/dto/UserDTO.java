package com.group_6.book_store.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String address;
}