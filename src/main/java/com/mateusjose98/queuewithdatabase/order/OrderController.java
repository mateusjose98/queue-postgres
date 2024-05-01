package com.mateusjose98.queuewithdatabase.order;

import com.mateusjose98.queuewithdatabase.order.dtos.OrderCreatedResponse;
import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("impl1")
    public OrderCreatedResponse finalizeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.before(orderRequest);
    }

    @PostMapping("impl2")
    public OrderCreatedResponse finalizeOrder2(@RequestBody OrderRequest orderRequest) {
        return orderService.after(orderRequest);
    }




}
