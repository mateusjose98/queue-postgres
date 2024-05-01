package com.mateusjose98.queuewithdatabase.order.repository;

import com.mateusjose98.queuewithdatabase.order.Order;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = "-2"))
    List<Order> findByStatus(String pending, Limit limit);

    @Modifying
    @Query("update Order o set o.status = ?1 where o.id = ?2")
    @Transactional
    void updateStatus(String status, Long id);

}
