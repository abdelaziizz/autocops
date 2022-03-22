package com.mdp.autocops.model.integration;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "name")
public class Name {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lang")
    private String lang;

    @Column(name = "description")
    private String description;

}
