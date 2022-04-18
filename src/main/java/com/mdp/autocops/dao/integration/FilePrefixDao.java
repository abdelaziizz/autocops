package com.mdp.autocops.dao.integration;

import com.mdp.autocops.model.entity.FilePrefix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePrefixDao extends JpaRepository<FilePrefix, Long> {
}
