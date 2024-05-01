package com.mateusjose98.queuewithdatabase.external;

import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Async
    public void notifyError(OrderRequest orderRequest) {
        log.info("Sending error email to {}", orderRequest.email());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Async
    public void notify(OrderRequest orderRequest, String transactionId) {
        log.info("Sending email to {} with transactionId {}", orderRequest.email(), transactionId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
