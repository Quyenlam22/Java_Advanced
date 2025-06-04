package com.group_6.book_store.form;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderCreateForm {
    @NotNull(message = "ID người dùng không được để trống")
    private Long userId;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;

    @NotEmpty(message = "Đơn hàng phải có ít nhất một mục")
    private List<OrderItemCreateForm> orderItems;
}