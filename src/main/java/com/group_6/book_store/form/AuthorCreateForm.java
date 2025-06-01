package com.group_6.book_store.form;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorCreateForm {
    @NotBlank(message = "Tên tác giả không được để trống")
    @Size(max = 100, message = "Tên tác giả không được vượt quá 100 ký tự")
    private String name;

    @Size(max = 1000, message = "Tiểu sử không được vượt quá 1000 ký tự")
    private String bio;

    @Size(max = 255, message = "URL ảnh không được vượt quá 255 ký tự")
    private String profileImage;
}