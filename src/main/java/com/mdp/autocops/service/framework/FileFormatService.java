package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.FileFormat;

import java.util.List;

public interface FileFormatService {

    FileFormat create(String type);

    FileFormat delete(long id);

    List<FileFormat> getAll();

    FileFormat put(long id, String type);

    FileFormat getById(long id);
}
