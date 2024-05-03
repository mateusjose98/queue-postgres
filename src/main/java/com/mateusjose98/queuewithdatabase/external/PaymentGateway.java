package com.mateusjose98.queuewithdatabase.external;

import com.mateusjose98.queuewithdatabase.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PaymentGateway {

    public String processPayment(String creditCardInfo) {
        log.info("Processing payment ...");
        Sleeper.sleep(3);
        return UUID.randomUUID().toString();
    }
}
