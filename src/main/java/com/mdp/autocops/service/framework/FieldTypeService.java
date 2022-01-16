package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.FieldType;

import java.util.List;

public interface FieldTypeService {

    List<FieldType> getAll();

    FieldType create(String type);

    FieldType delete(long id);

    FieldType getById(long id);

    FieldType put(long id, String newType);

}
