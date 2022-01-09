package com.mdp.autocops.dao;

import com.mdp.autocops.model.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesDao extends JpaRepository<ServiceEntity, Long> {
}
