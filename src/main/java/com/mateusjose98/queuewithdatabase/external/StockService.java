package com.mateusjose98.queuewithdatabase.external;

import com.mateusjose98.queuewithdatabase.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class StockService {

    public void updateStock(Integer product, Integer quantity) {
        log.info("Updating stock ...");
        Sleeper.sleep(1);
    }
}
