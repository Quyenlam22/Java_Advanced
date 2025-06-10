package com.group_6.book_store.controller;

import com.group_6.book_store.dto.OrderDTO;
import com.group_6.book_store.form.OrderCreateForm;
import com.group_6.book_store.form.OrderStatusUpdateForm;
import com.group_6.book_store.service.OrderServiceV2;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    public OrderControllerV2(OrderServiceV2 orderServiceV2) {
        this.orderServiceV2 = orderServiceV2;
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Chỉ admin xem tất cả đơn hàng
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        Page<OrderDTO> orders = orderServiceV2.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/me")
    @PreAuthorize("hasRole('ROLE_USER')") // Người dùng đã đăng nhập xem đơn hàng của mình
    public ResponseEntity<Page<OrderDTO>> getMyOrders(@RequestParam Long userId, Pageable pageable) {
        Page<OrderDTO> orders = orderServiceV2.getMyOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('ROLE_USER')") // Người dùng đã đăng nhập xem chi tiết đơn hàng
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id, @RequestParam Long userId) {
        OrderDTO order = orderServiceV2.getOrder(id, userId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('ROLE_USER')") // Yêu cầu đăng nhập để tạo đơn hàng
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderCreateForm form, @RequestParam Long userId) {
        OrderDTO order = orderServiceV2.createOrder(userId, form);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders/from-cart")
    @PreAuthorize("hasRole('ROLE_USER')") // Yêu cầu đăng nhập để tạo đơn từ giỏ hàng
    public ResponseEntity<OrderDTO> createOrderFromCart(@RequestParam Long userId, @RequestParam String shippingAddress) {
        OrderDTO order = orderServiceV2.createOrderFromCart(userId, shippingAddress);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/orders/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Chỉ admin cập nhật trạng thái
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateForm form) {
        OrderDTO order = orderServiceV2.updateOrderStatus(id, form);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Chỉ admin xóa đơn hàng
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderServiceV2.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}