package com.group_6.book_store.controller;

import com.group_6.book_store.dto.CartDTO;
import com.group_6.book_store.form.CartItemCreateForm;
import com.group_6.book_store.form.CartItemUpdateForm;
import com.group_6.book_store.service.CartServiceV2;
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

    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO>> getAllCarts(@RequestParam Long userId) {
        List<CartDTO> carts = cartServiceV2.getAllCarts(userId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/cart/items")
    public ResponseEntity<List<CartDTO>> addItemToCart(@Valid @RequestBody CartItemCreateForm form, @RequestParam Long userId) {
        List<CartDTO> carts = cartServiceV2.addItemToCart(userId, form);
        return ResponseEntity.ok(carts);
    }

    @PatchMapping("/cart/items/{bookId}")
    public ResponseEntity<List<CartDTO>> updateCartItem(@PathVariable String bookId, @Valid @RequestBody CartItemUpdateForm form, @RequestParam Long userId) {
        List<CartDTO> carts = cartServiceV2.updateCartItem(userId, bookId, form);
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/cart/items/{bookId}")
    public ResponseEntity<List<CartDTO>> removeCartItem(@PathVariable String bookId, @RequestParam Long userId) {
        List<CartDTO> carts = cartServiceV2.removeCartItem(userId, bookId);
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartServiceV2.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}