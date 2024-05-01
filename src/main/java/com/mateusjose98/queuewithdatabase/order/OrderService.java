package com.mateusjose98.queuewithdatabase.order;

import com.mateusjose98.queuewithdatabase.order.dtos.OrderCreatedResponse;
import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import com.mateusjose98.queuewithdatabase.external.EmailService;
import com.mateusjose98.queuewithdatabase.external.PaymentGateway;
import com.mateusjose98.queuewithdatabase.external.StockService;
import com.mateusjose98.queuewithdatabase.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OrderService {

    final OrderRepository orderRepository;
    final PaymentGateway paymentGateway;
    final EmailService emailService;
    final StockService stockService;
    public OrderService(OrderRepository orderRepository, PaymentGateway paymentGateway, EmailService emailService,  StockService stockService) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
        this.emailService = emailService;
        this.stockService = stockService;
    }

    public OrderCreatedResponse before(OrderRequest orderRequest) {
        log.info("Order received {}", orderRequest);
        Order order = new Order(orderRequest);

        order = this.saveOrder(order);

        log.info("Order created {}", order);

        String transactionId = paymentGateway.processPayment(orderRequest.creditCardInfo());

        if(transactionId == null) {
            emailService.notifyError(orderRequest);
            return null; // refact
        } else {
            emailService.notify(orderRequest, transactionId);
        }

        stockService.updateStock(10, orderRequest.items().size());
        order.setStatus("Paid");

        return new OrderCreatedResponse(order.getId(), orderRequest.customerDocument());

    }

    private Order saveOrder(Order order) {
        try {
            Thread.sleep(300);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orderRepository.save(order);
    }

    public OrderCreatedResponse after(OrderRequest orderRequest) {
        log.info("Order received {}", orderRequest);
        Order order = new Order(orderRequest);
        this.saveOrder(order);

        log.info("Order created {}", order);

        return new OrderCreatedResponse(order.getId(), orderRequest.customerDocument());
    }
}
