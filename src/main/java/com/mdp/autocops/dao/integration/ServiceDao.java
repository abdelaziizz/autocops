package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDao extends JpaRepository<Service, String> {
}
