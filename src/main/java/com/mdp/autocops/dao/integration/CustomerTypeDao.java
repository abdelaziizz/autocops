package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeDao extends JpaRepository<CustomerType, String> {
}
