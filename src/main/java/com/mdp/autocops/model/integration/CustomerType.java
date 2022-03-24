package com.mdp.autocops.model.integration;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customer_types")
public class CustomerType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_type")
    private String customerType;


}
