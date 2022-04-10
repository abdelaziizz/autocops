package com.mdp.autocops.model.integration;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product_services")
public class Service {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "service_number")
    private String serviceNumber;

    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "service_status")
    private String serviceStatus;

    @Column(name = "min_count")
    private int minCount;

    @Column(name = "max_count")
    private int maxCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ServiceName> serviceNames = new HashSet<>();

    public void addServiceName(ServiceName serviceName){
        serviceNames.add(serviceName);
    }


}
