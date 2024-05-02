package com.mateusjose98.queuewithdatabase;

import com.mateusjose98.queuewithdatabase.order.Order;
import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import com.mateusjose98.queuewithdatabase.external.EmailService;
import com.mateusjose98.queuewithdatabase.external.PaymentGateway;
import com.mateusjose98.queuewithdatabase.external.StockService;
import com.mateusjose98.queuewithdatabase.order.repository.OrderRepository;
import com.mateusjose98.queuewithdatabase.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.enable", havingValue = "true")
public class OrderProcessor {

    final OrderRepository orderRepository;
    final PaymentGateway paymentGateway;
    final EmailService emailService;
    final StockService stockService;
    final TransactionTemplate transactionTemplate;
    @Value("${batch.size}")
    private int batchSize;
    @Scheduled(fixedDelayString = "${period.job}", timeUnit = TimeUnit.SECONDS, initialDelay = 2)
    public void process() {

        boolean pending = true;

        while(pending) {
            pending = Boolean.TRUE.equals(transactionTemplate.execute(status -> {
                List<Order> pendingOrders = orderRepository.findByStatus("Pending", Limit.of(batchSize));
                if (pendingOrders.isEmpty()) {
                    return false;
                }
                pendingOrders.forEach(order -> {

                    OrderRequest orderRequest = JsonUtils.fromJson(order.getJsonValue(), OrderRequest.class);
                    String transactionId = paymentGateway.processPayment(orderRequest.creditCardInfo());
                    if (transactionId == null) {
                        emailService.notifyError(orderRequest);
                        log.error("Error processing payment for order {}", order.getId());
                        return;
                    } else {
                        orderRepository.updateStatus("Paid", order.getId());
                        emailService.notify(orderRequest, transactionId);
                    }
                    stockService.updateStock(10, orderRequest.items().size());
                });
                return true;
            }));
        }


    }
}
