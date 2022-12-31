package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderServiceImpl;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

		OrderDto orderDto = orderServiceImpl.getOrderById(id);

		OrderDetailsResponse orderDetailsResponse;

		orderDetailsResponse = OrderDetailsResponse.builder().orderId(orderDto.getOrderId()).cost(orderDto.getCost()).items(orderDto.getItems()).status(orderDto.isStatus()).userId(orderDto.getUserId()).build();

		return orderDetailsResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {

		OrderDto orderDto = OrderDto.builder().userId(order.getUserId()).items(order.getItems()).cost(order.getCost()).build();

		orderServiceImpl.createOrder(orderDto);

		OrderDetailsResponse orderDetailsResponse;

		orderDetailsResponse = OrderDetailsResponse.builder().cost(orderDto.getCost()).items(orderDto.getItems()).status(true).userId(orderDto.getUserId()).build();

		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{

		OrderDto orderDto = OrderDto.builder().items(order.getItems()).cost(order.getCost()).userId(order.getUserId()).status(true).build();

		orderServiceImpl.updateOrderDetails(id,orderDto);

		OrderDetailsResponse orderDetailsResponse;

		orderDetailsResponse = OrderDetailsResponse.builder().items(orderDto.getItems()).cost(orderDto.getCost()).userId(orderDto.getUserId()).status(true).build();

		return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {

		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();

		orderServiceImpl.deleteOrder(id);
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {

		List<OrderDto> listOfOrderDtos = orderServiceImpl.getOrders();
		List<OrderDetailsResponse> list = new ArrayList<>();

		for (OrderDto order: listOfOrderDtos) {

			OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder().items(order.getItems()).cost(order.getCost()).userId(order.getUserId()).status(true).build();

			list.add(orderDetailsResponse);
		}

		return list;
	}
}
