package com.mdp.autocops.dao;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.FieldDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportFieldDao extends JpaRepository<ExportField, Long> {

}
