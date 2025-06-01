package com.group_6.book_store.form;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemUpdateForm {
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer quantity;

    @NotNull(message = "Giá đơn vị không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá đơn vị phải lớn hơn 0")
    private BigDecimal unitPrice;
}