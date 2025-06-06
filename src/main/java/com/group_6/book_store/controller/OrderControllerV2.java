package com.group_6.book_store.controller;

import com.group_6.book_store.dto.OrderDTO;
import com.group_6.book_store.form.OrderCreateForm;
import com.group_6.book_store.form.OrderStatusUpdateForm;
import com.group_6.book_store.service.OrderServiceV2;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    public OrderControllerV2(OrderServiceV2 orderServiceV2) {
        this.orderServiceV2 = orderServiceV2;
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        Page<OrderDTO> orders = orderServiceV2.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/me")
    public ResponseEntity<Page<OrderDTO>> getMyOrders(@RequestParam Long userId, Pageable pageable) {
        Page<OrderDTO> orders = orderServiceV2.getMyOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id, @RequestParam Long userId) {
        OrderDTO order = orderServiceV2.getOrder(id, userId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderCreateForm form, @RequestParam Long userId) {
        OrderDTO order = orderServiceV2.createOrder(userId, form);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders/from-cart")
    public ResponseEntity<OrderDTO> createOrderFromCart(@RequestParam Long userId, @RequestParam String shippingAddress) {
        OrderDTO order = orderServiceV2.createOrderFromCart(userId, shippingAddress);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateForm form) {
        OrderDTO order = orderServiceV2.updateOrderStatus(id, form);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderServiceV2.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}