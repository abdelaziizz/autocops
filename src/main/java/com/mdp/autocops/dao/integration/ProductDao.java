package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.integration.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, String> {
}
