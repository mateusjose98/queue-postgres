package com.mateusjose98.queuewithdatabase.order.dtos;

import java.util.List;

public record OrderRequest(String customerDocument, String email, String creditCardInfo, List<OrderItemRequest> items) {}