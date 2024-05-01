package com.mateusjose98.queuewithdatabase.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusjose98.queuewithdatabase.order.dtos.OrderRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@ToString
public class Order {

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
        try {
            // to json string
            this.setJsonValue(new ObjectMapper().writeValueAsString(orderRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
