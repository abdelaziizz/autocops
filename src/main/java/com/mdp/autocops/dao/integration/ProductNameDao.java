package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNameDao extends JpaRepository<ProductName, Long> {
}
