package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.order.OrderRequest;
import com.example.E_Commerce.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long id);

    void cancelOrder(Long id);

}