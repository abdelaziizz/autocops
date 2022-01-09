package com.mdp.autocops.dao;

import com.mdp.autocops.model.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionsDao extends JpaRepository<Institution, Long> {
}
