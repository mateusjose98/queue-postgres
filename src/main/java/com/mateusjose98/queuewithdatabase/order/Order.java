package com.mateusjose98.queuewithdatabase.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import com.mateusjose98.queuewithdatabase.util.JsonUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(
        name = "orders",
        indexes = @Index(name = "idx_status", columnList = "status")
)
@NoArgsConstructor
@ToString
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerDocument;
    private String email;

    private String status;
    private String jsonValue;

    public Order(OrderRequest orderRequest) {
        this.customerDocument = orderRequest.customerDocument();
        this.email = orderRequest.email();
        this.status = "Pending";
        this.setJsonValue(JsonUtils.toJson(orderRequest));

    }
}
