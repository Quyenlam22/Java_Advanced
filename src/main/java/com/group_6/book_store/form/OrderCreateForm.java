package com.group_6.book_store.form;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateForm {
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotEmpty(message = "Order items are required")
    private List<OrderItemForm> orderItems;

    @AssertTrue(message = "Total amount must match sum of order items")
    private boolean isTotalAmountValid() {
        if (orderItems == null || orderItems.isEmpty()) {
            return false;
        }
        BigDecimal calculatedTotal = orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount.compareTo(calculatedTotal) == 0;
    }
}