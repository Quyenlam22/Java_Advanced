package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.CartDTO;
import com.group_6.book_store.dto.CartItemDTO;
import com.group_6.book_store.entity.Cart;
import com.group_6.book_store.entity.CartItem;
import com.group_6.book_store.form.CartItemCreateForm;
import com.group_6.book_store.form.CartItemUpdateForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {
    @Mapping(source = "id", target = "id", numberFormat = "#")
    @Mapping(source = "user.id", target = "userId", numberFormat = "#")
    @Mapping(target = "bookItems", source = "cartItems")
    CartDTO toDTO(Cart cart);

    @Mapping(source = "book.id", target = "bookId", numberFormat = "#")
    CartItemDTO toItemDTO(CartItem cartItem);

    CartItem toCartItemEntity(CartItemCreateForm form);

    @Mapping(target = "quantity", source = "quantity") // Chỉ cập nhật quantity, các trường khác giữ nguyên
    void updateCartItemFromForm(CartItemUpdateForm form, @MappingTarget CartItem cartItem);
}