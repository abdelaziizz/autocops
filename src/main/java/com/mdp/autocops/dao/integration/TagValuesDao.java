package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.entity.TagValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagValuesDao extends JpaRepository<TagValue, String> {
}
