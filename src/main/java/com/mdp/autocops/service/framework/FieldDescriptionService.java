package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.FieldDescription;

import java.util.List;

public interface FieldDescriptionService {

    List<FieldDescription> getAll();

    FieldDescription getById(long id);

    FieldDescription getByName(String field_name);

    FieldDescription create(String field_name, String description);

    FieldDescription put(long id, String field_name, String description);

    FieldDescription delete(long id);
    
}
