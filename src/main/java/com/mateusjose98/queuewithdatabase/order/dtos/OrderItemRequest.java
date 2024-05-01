package com.mateusjose98.queuewithdatabase.order.dtos;

public record OrderItemRequest(String productId, int quantity, double price) {}
