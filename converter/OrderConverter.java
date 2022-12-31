package com.driver.converter;

import com.driver.io.entity.OrderEntity;
import com.driver.shared.dto.OrderDto;
import lombok.experimental.UtilityClass;

@UtilityClass    // All methods are static and called using class name
public class OrderConverter {

    public static OrderDto entityToDto(OrderEntity orderEntity) {
        return OrderDto.builder().id(orderEntity.getId()).orderId(orderEntity.getOrderId()).cost(orderEntity.getCost()).items(orderEntity.getItems()).status(orderEntity.isStatus()).userId(orderEntity.getUserId()).build();
    }
}
