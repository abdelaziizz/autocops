package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.FieldFormat;

import java.util.List;

public interface FieldFormatService {

    List<FieldFormat> getAll();

    FieldFormat create(long type_id, String format);

    FieldFormat getById(long id);

    FieldFormat delete(long id);

    FieldFormat put(long id, Long type_id, String newFormat);

    List<FieldFormat> getAllByType(long typeId);

}
