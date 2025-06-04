package com.group_6.book_store.dto;

import com.group_6.book_store.entity.Order.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class OrderDTO {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private Status status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDTO> orderItems;
}