package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.integration.ServiceName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceNameDao  extends JpaRepository<ServiceName, Long> {
}
