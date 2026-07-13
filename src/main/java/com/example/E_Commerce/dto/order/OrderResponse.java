package com.example.E_Commerce.dto.order;

import com.example.E_Commerce.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private String shippingAddress;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

}