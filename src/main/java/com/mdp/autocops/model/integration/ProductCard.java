package com.mdp.autocops.model.integration;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cards")
public class ProductCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_type_id")
    private int cardTypeId;

}
