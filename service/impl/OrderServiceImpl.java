package com.driver.service.impl;

import com.driver.converter.OrderConverter;
import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity=OrderEntity.builder().orderId(orderDto.getOrderId()).cost(orderDto.getCost()).items(orderDto.getItems()).status(orderDto.isStatus()).userId(orderDto.getUserId()).build();
        orderRepository.save(orderEntity);   // save to DB
        return orderDto;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            System.out.println("Order Not Found");
            return null;
        }else {
            // Convert Entity To DTO

            OrderDto orderDto=OrderDto.builder().id(orderEntity.getId()).orderId(orderEntity.getOrderId()).cost(orderEntity.getCost()).items(orderEntity.getItems()).status(orderEntity.isStatus()).userId(orderEntity.getUserId()).build();
            return orderDto;
        }
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            System.out.println("Order Not Updated");
            return null;
        }else {

            orderRepository.updateOrderEntity(order.getId(), order.getOrderId(), order.getItems(), order.getCost(), order.getUserId(), order.isStatus());
            OrderDto updatedOrderDto=order;
            updatedOrderDto.setId(orderEntity.getId());

            return updatedOrderDto;
        }
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        if (orderEntity == null) System.out.println("Order Not Found to Delete");
        else
            orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderEntity> listOfOrderEntities= (List<OrderEntity>) orderRepository.findAll();
        List<OrderDto> listOfOrderDtos=new ArrayList<>();
        for(OrderEntity order :listOfOrderEntities) listOfOrderDtos.add(OrderConverter.entityToDto(order));
        return listOfOrderDtos;
    }
}