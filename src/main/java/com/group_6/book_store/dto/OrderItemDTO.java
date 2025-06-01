package com.group_6.book_store.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long bookId;
    private Integer quantity;
    private BigDecimal unitPrice;
}