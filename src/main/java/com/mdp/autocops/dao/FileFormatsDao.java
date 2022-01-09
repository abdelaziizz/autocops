package com.mdp.autocops.dao;

import com.mdp.autocops.model.entity.FileFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileFormatsDao extends JpaRepository<FileFormat, Long> {

}
