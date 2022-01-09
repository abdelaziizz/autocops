package com.mdp.autocops.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long service_id;

    @Column(name = "service_name")
    private String service_name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;


}
