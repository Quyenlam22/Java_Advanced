package com.group_6.book_store.controller;

import com.group_6.book_store.dto.TempCartDTO;
import com.group_6.book_store.dto.UserCartDTO;
import com.group_6.book_store.form.CartItemCreateForm;
import com.group_6.book_store.form.CartItemUpdateForm;
import com.group_6.book_store.service.CartServiceV2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class CartControllerV2 {

    private final CartServiceV2 cartServiceV2;

    public CartControllerV2(CartServiceV2 cartServiceV2) {
        this.cartServiceV2 = cartServiceV2;
    }

    // Lấy giỏ hàng (tự động tạo nếu chưa có)
    @GetMapping("/carts")
    public ResponseEntity<?> getCart(HttpServletRequest request, HttpServletResponse response) {
        Object cart = cartServiceV2.getCart(request, response);
        return ResponseEntity.ok(cart);
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/carts/items")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody CartItemCreateForm form, HttpServletRequest request, HttpServletResponse response) {
        Object cart = cartServiceV2.addItemToCart(form, request, response);
        return ResponseEntity.ok(cart);
    }

    // Cập nhật sản phẩm trong giỏ hàng
    @PatchMapping("/carts/items/{bookId}")
    public ResponseEntity<?> updateCartItem(@PathVariable String bookId, @Valid @RequestBody CartItemUpdateForm form, HttpServletRequest request, HttpServletResponse response) {
        Object cart = cartServiceV2.updateCartItem(bookId, form, request, response);
        return ResponseEntity.ok(cart);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/carts/items/{bookId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String bookId, HttpServletRequest request, HttpServletResponse response) {
        Object cart = cartServiceV2.removeCartItem(bookId, request, response);
        return ResponseEntity.ok(cart);
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/carts")
    public ResponseEntity<Void> clearCart(HttpServletRequest request, HttpServletResponse response) {
        cartServiceV2.clearCart(request, response);
        return ResponseEntity.noContent().build();
    }

    // Tạo giỏ hàng rỗng
    @PostMapping("/carts/create")
    public ResponseEntity<?> createEmptyCart(HttpServletRequest request, HttpServletResponse response) {
        Object cart = cartServiceV2.createEmptyCart(request, response);
        return ResponseEntity.ok(cart);
    }

    // Đồng bộ giỏ hàng từ cookie sang DB khi đăng nhập
    @PostMapping("/carts/sync")
    public ResponseEntity<UserCartDTO> syncCartFromCookieToDB(HttpServletRequest request, HttpServletResponse response) {
        UserCartDTO cart = cartServiceV2.syncCartFromCookieToDB(request, response);
        return ResponseEntity.ok(cart);
    }
}