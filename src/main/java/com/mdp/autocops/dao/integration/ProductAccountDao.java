package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.ProductAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAccountDao extends JpaRepository<ProductAccount, Long> {
}
