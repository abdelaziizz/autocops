package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.entity.DefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultValuesDao extends JpaRepository<DefaultValue, Long> {
}
