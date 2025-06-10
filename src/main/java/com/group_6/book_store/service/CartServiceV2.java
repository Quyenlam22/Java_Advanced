package com.group_6.book_store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group_6.book_store.dto.TempCartDTO;
import com.group_6.book_store.dto.UserCartDTO;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceV2 {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartMapper cartMapper;
    private final ObjectMapper objectMapper;

    // Định nghĩa UUID mặc định cho giỏ hàng tạm thời
    private static final UUID DEFAULT_TEMP_CART_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public CartServiceV2(CartRepository cartRepository, UserRepository userRepository,
                         BookRepository bookRepository, CartMapper cartMapper, ObjectMapper objectMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartMapper = cartMapper;
        this.objectMapper = objectMapper;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String username = auth.getName();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        }
        return null;
    }

    public Object getCart(HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserIdWithItems(finalUser.getId())
                    .orElseGet(() -> {
                        Cart newCart = createCartForUser(finalUser);
                        newCart.setCartItems(new ArrayList<>());
                        return cartRepository.save(newCart);
                    });
            clearTempCartCookie(response);
            return cartMapper.toUserCartDTO(cart);
        } else {
            Cart tempCart = getOrCreateTempCart(request, response);
            if (tempCart.getCartItems() == null || tempCart.getCartItems().isEmpty()) {
                tempCart.setCartItems(new ArrayList<>());
                updateCartCookie(tempCart, request, response);
            }
            return cartMapper.toTempCartDTO(tempCart);
        }
    }

    @Transactional
    public Object addItemToCart(CartItemCreateForm form, HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(Long.valueOf(form.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + form.getBookId()));

        if (book.getStock() < form.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserId(finalUser.getId())
                    .orElseGet(() -> createCartForUser(finalUser));

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
            clearTempCartCookie(response);
            return cartMapper.toUserCartDTO(cart);
        } else {
            return updateTempCartInCookie(form, request, response);
        }
    }

    @Transactional
    public Object updateCartItem(String bookId, CartItemUpdateForm form, HttpServletRequest request, HttpServletResponse response) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID must not be empty");
        }

        User user = getCurrentUser();

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserIdWithItems(finalUser.getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + finalUser.getId()));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

            Book book = cartItem.getBook();
            if (book.getStock() < form.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }

            cartMapper.updateCartItemFromForm(form, cartItem);
            cart = cartRepository.save(cart);
            clearTempCartCookie(response);
            return cartMapper.toUserCartDTO(cart);
        } else {
            return updateTempCartInCookie(bookId, form, request, response);
        }
    }

    @Transactional
    public Object removeCartItem(String bookId, HttpServletRequest request, HttpServletResponse response) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID must not be empty");
        }

        User user = getCurrentUser();

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserIdWithItems(finalUser.getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + finalUser.getId()));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

            cart.getCartItems().remove(cartItem);
            cart = cartRepository.save(cart);
            clearTempCartCookie(response);
            return cartMapper.toUserCartDTO(cart);
        } else {
            return removeTempCartItemFromCookie(bookId, request, response);
        }
    }

    @Transactional
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserId(finalUser.getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + finalUser.getId()));
            cart.getCartItems().clear();
            cartRepository.save(cart);
            clearTempCartCookie(response);
        } else {
            clearTempCartCookie(response);
        }
    }

    @Transactional
    public Object createEmptyCart(HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();

        if (user != null) {
            final User finalUser = user;
            Cart cart = cartRepository.findByUserId(finalUser.getId()).orElseGet(() -> createCartForUser(finalUser));
            cart.getCartItems().clear();
            cart = cartRepository.save(cart);
            clearTempCartCookie(response);
            return cartMapper.toUserCartDTO(cart);
        } else {
            Cart tempCart = new Cart();
            tempCart.setCartItems(new ArrayList<>());
            updateCartCookie(tempCart, request, response);
            return cartMapper.toTempCartDTO(tempCart);
        }
    }

    @Transactional
    public UserCartDTO syncCartFromCookieToDB(HttpServletRequest request, HttpServletResponse response) {
        User user = getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User must be authenticated to sync cart");
        }

        final User finalUser = user;
        Cart cart = cartRepository.findByUserId(finalUser.getId())
                .orElseGet(() -> createCartForUser(finalUser));

        Cart tempCart = getOrCreateTempCart(request, response);
        for (CartItem tempItem : tempCart.getCartItems()) {
            Book book = bookRepository.findById(tempItem.getBook().getId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + tempItem.getBook().getId()));
            if (book.getStock() >= tempItem.getQuantity()) {
                CartItem existingItem = cart.getCartItems().stream()
                        .filter(item -> item.getBook().getId().equals(tempItem.getBook().getId()))
                        .findFirst()
                        .orElse(null);
                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + tempItem.getQuantity());
                } else {
                    CartItem newItem = new CartItem();
                    newItem.setBook(book);
                    newItem.setQuantity(tempItem.getQuantity());
                    newItem.setCart(cart);
                    cart.getCartItems().add(newItem);
                }
            } else {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }
        }

        cart = cartRepository.save(cart);
        clearTempCartCookie(response);
        return cartMapper.toUserCartDTO(cart);
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private Object updateTempCartInCookie(CartItemCreateForm form, HttpServletRequest request, HttpServletResponse response) {
        Cart tempCart = getOrCreateTempCart(request, response);
        Book book = bookRepository.findById(Long.valueOf(form.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + form.getBookId()));

        if (book.getStock() < form.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        if (tempCart.getCartItems() == null) {
            tempCart.setCartItems(new ArrayList<>());
        } else if (!(tempCart.getCartItems() instanceof ArrayList)) {
            tempCart.setCartItems(new ArrayList<>(tempCart.getCartItems()));
        }

        CartItem existingItem = tempCart.getCartItems().stream()
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
            cartItem.setBook(book);
            tempCart.getCartItems().add(cartItem);
        }

        updateCartCookie(tempCart, request, response);
        return cartMapper.toTempCartDTO(tempCart);
    }

    private Object updateTempCartInCookie(String bookId, CartItemUpdateForm form, HttpServletRequest request, HttpServletResponse response) {
        Cart tempCart = getOrCreateTempCart(request, response);
        CartItem cartItem = tempCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

        Book book = cartItem.getBook();
        if (book.getStock() < form.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
        }

        if (tempCart.getCartItems() == null) {
            tempCart.setCartItems(new ArrayList<>());
        } else if (!(tempCart.getCartItems() instanceof ArrayList)) {
            tempCart.setCartItems(new ArrayList<>(tempCart.getCartItems()));
        }

        cartMapper.updateCartItemFromForm(form, cartItem);
        updateCartCookie(tempCart, request, response);
        return cartMapper.toTempCartDTO(tempCart);
    }

    private Object removeTempCartItemFromCookie(String bookId, HttpServletRequest request, HttpServletResponse response) {
        Cart tempCart = getOrCreateTempCart(request, response);
        CartItem cartItem = tempCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(Long.valueOf(bookId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found for book id: " + bookId));

        if (tempCart.getCartItems() == null) {
            tempCart.setCartItems(new ArrayList<>());
        } else if (!(tempCart.getCartItems() instanceof ArrayList)) {
            tempCart.setCartItems(new ArrayList<>(tempCart.getCartItems()));
        }

        tempCart.getCartItems().remove(cartItem);
        updateCartCookie(tempCart, request, response);
        return cartMapper.toTempCartDTO(tempCart);
    }

    private void clearTempCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cart_data", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private Cart getOrCreateTempCart(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart_data".equals(cookie.getName())) {
                    try {
                        String decodedCartJson = new String(Base64.getDecoder().decode(cookie.getValue()));
                        System.out.println("Received cookie value: " + cookie.getValue());
                        System.out.println("Decoded JSON: " + decodedCartJson);
                        TempCartDTO tempCartDTO = objectMapper.readValue(decodedCartJson, TempCartDTO.class);
                        System.out.println("Parsed TempCartDTO items count: " + (tempCartDTO.getCartItems() != null ? tempCartDTO.getCartItems().size() : 0));

                        Cart tempCart = new Cart();
                        if (tempCartDTO.getCartItems() != null) {
                            tempCart.setCartItems(tempCartDTO.getCartItems().stream().map(item -> {
                                CartItem cartItem = new CartItem();
                                cartItem.setBook(bookRepository.findById(item.getBookId())
                                        .orElseThrow(() -> new RuntimeException("Book not found: " + item.getBookId())));
                                cartItem.setQuantity(item.getQuantity());
                                return cartItem;
                            }).toList());
                        } else {
                            tempCart.setCartItems(new ArrayList<>());
                        }
                        return tempCart;
                    } catch (Exception e) {
                        System.out.println("Decoding error: " + e.getMessage());
                        Cart tempCart = new Cart();
                        tempCart.setCartItems(new ArrayList<>());
                        return tempCart;
                    }
                }
            }
        }
        System.out.println("No cart_data cookie found");
        Cart tempCart = new Cart();
        tempCart.setCartItems(new ArrayList<>());
        updateCartCookie(tempCart, request, response);
        return tempCart;
    }

    private void updateCartCookie(Cart cart, HttpServletRequest request, HttpServletResponse response) {
        try {
            UUID cartId = DEFAULT_TEMP_CART_ID; // Luôn sử dụng id mặc định cho giỏ hàng tạm thời
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart_data".equals(cookie.getName())) {
                        try {
                            String decodedCartJson = new String(Base64.getDecoder().decode(cookie.getValue()));
                            TempCartDTO tempCartDTO = objectMapper.readValue(decodedCartJson, TempCartDTO.class);
                            if (tempCartDTO.getId() != null) {
                                cartId = tempCartDTO.getId(); // Giữ id hiện có nếu có
                            }
                            break;
                        } catch (Exception e) {
                            // Bỏ qua lỗi, giữ id mặc định
                        }
                    }
                }
            }

            TempCartDTO tempCartDTO = new TempCartDTO();
            tempCartDTO.setId(cartId); // Đảm bảo sử dụng cartId đã xác định
            tempCartDTO.setCartItems(cart.getCartItems().stream().map(item -> {
                TempCartDTO.CartItemDTO dto = new TempCartDTO.CartItemDTO();
                dto.setBookId(item.getBook().getId());
                dto.setQuantity(item.getQuantity());
                return dto;
            }).toList());
            System.out.println("Serialized TempCartDTO: " + objectMapper.writeValueAsString(tempCartDTO));

            String cartJson = objectMapper.writeValueAsString(tempCartDTO);
            String encodedCartJson = Base64.getEncoder().encodeToString(cartJson.getBytes());
            System.out.println("Encoded cookie value: " + encodedCartJson);
            Cookie cookie = new Cookie("cart_data", encodedCartJson);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            System.out.println("Serialization error: " + e.getMessage());
            throw new RuntimeException("Failed to serialize cart to cookie", e);
        }
    }
}