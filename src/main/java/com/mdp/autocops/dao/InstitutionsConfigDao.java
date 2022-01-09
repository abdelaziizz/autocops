package com.mdp.autocops.dao;

import com.mdp.autocops.model.entity.InstitutionConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionsConfigDao extends JpaRepository<InstitutionConfig, Long> {
}
