package com.mdp.autocops.model.integration;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_accounts")
public class ProductAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "currency")
    private String currency;

    @Column(name = "service_number")
    private String serviceNumber;
}
