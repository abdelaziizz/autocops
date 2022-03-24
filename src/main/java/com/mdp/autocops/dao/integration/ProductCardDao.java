package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.ProductCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCardDao  extends JpaRepository<ProductCard, Integer> {
}
