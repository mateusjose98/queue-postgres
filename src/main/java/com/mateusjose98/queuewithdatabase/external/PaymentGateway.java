package com.mateusjose98.queuewithdatabase.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PaymentGateway {

    public String processPayment(String creditCardInfo) {
        log.info("Processing payment ...");
        try {
            Thread.sleep(800);
            return "transactionId_"+ UUID.randomUUID();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
