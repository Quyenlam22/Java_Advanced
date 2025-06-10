package com.group_6.book_store.mapper;

import com.group_6.book_store.dto.OrderDTO;
import com.group_6.book_store.dto.OrderItemDTO;
import com.group_6.book_store.entity.Order;
import com.group_6.book_store.entity.OrderItem;
import com.group_6.book_store.form.OrderCreateForm;
import com.group_6.book_store.form.OrderItemForm;
import com.group_6.book_store.form.OrderStatusUpdateForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "userInfo", expression = "java(mapUserInfo(order))")
    @Mapping(source = "cartId", target = "cartId")
    OrderDTO toDTO(Order order);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    OrderItemDTO toItemDTO(OrderItem orderItem);

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "cartId", target = "cartId")
    Order toEntity(OrderCreateForm form);

    OrderItem toOrderItemEntity(OrderItemForm form);

    void updateStatusFromForm(OrderStatusUpdateForm form, @MappingTarget Order order);

    default OrderDTO.UserInfo mapUserInfo(Order order) {
        OrderDTO.UserInfo userInfo = new OrderDTO.UserInfo();
        userInfo.setFullName(order.getFullName());
        userInfo.setPhone(order.getPhone());
        userInfo.setAddress(order.getAddress());
        return userInfo;
    }
}