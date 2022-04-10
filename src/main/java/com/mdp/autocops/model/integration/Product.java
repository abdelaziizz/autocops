package com.mdp.autocops.model.integration;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "institution_id")
    private long institution_id;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "contract_type")
    private String contractType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_name")
    private ProductName productName;

    @Column(name = "product_status")
    private String productStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CustomerType> customerTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductAccount> productAccounts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductCard> productCards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Service> services = new HashSet<>();

    public void addCustomerType(CustomerType customerType){
        customerTypes.add(customerType);
    }
    public void addProductAccount(ProductAccount productAccount){
        productAccounts.add(productAccount);
    }
    public void addProductCard(ProductCard productCard){
        productCards.add(productCard);
    }
    public void addService(Service service){
        services.add(service);
    }


}
