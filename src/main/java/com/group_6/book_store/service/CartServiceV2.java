package com.group_6.book_store.service;

import com.group_6.book_store.dto.CartDTO;
import com.group_6.book_store.entity.Book;
import com.group_6.book_store.entity.Cart;
import com.group_6.book_store.entity.CartItem;
import com.group_6.book_store.entity.User;
import com.group_6.book_store.form.CartItemCreateForm;
import com.group_6.book_store.form.CartItemUpdateForm;
import com.group_6.book_store.mapper.CartMapper;
import com.group_6.book_store.repository.BookRepository;
import com.group_6.book_store.repository.CartRepository;
import com.group_6.book_store.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CartServiceV2 {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartMapper cartMapper;

    public CartServiceV2(CartRepository cartRepository, UserRepository userRepository,
                         BookRepository bookRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartMapper = cartMapper;
    }

    public List<CartDTO> getAllCarts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseGet(() -> createCartForUser(user));

        return Collections.singletonList(cartMapper.toDTO(cart));
    }

    @Transactional
    public List<CartDTO> addItemToCart(Long userId, CartItemCreateForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createCartForUser(user));

        Book book = bookRepository.findById(Long.valueOf(form.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + form.getBookId()));

        if (book.getStock() < form.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(Long.valueOf(form.getBookId())))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + form.getQuantity());
            if (book.getStock() < existingItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }
        } else {
            CartItem cartItem = cartMapper.toCartItemEntity(form);
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cart.getCartItems().add(cartItem);
        }

        cart = cartRepository.save(cart);
        return Collections.singletonList(cartMapper.toDTO(cart));
    }

    @Transactional
    public List<CartDTO> updateCartItem(Long userId, String bookId, CartItemUpdateForm form) {
        // Validate bookId
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID must not be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

        Book book = cartItem.getBook();
        if (book.getStock() < form.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        // Chỉ cập nhật quantity từ form, các trường khác (như bookId, cartId) giữ nguyên
        // Nhờ nullValuePropertyMappingStrategy = IGNORE trong CartMapper
        cartMapper.updateCartItemFromForm(form, cartItem);
        cart = cartRepository.save(cart);
        return Collections.singletonList(cartMapper.toDTO(cart));
    }

    @Transactional
    public List<CartDTO> removeCartItem(Long userId, String bookId) {
        // Validate bookId
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID must not be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

        cart.getCartItems().remove(cartItem);
        cart = cartRepository.save(cart);
        return Collections.singletonList(cartMapper.toDTO(cart));
    }

    @Transactional
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}