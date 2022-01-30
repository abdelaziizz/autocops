package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.ImportField;

import java.util.List;

public interface ImportFieldService {

    List<ImportField> getAll();

    ImportField getById(long id);

    List<ImportField> getAllByService(long service_id);

    ImportField create(long service_id, String field_name, String parent_name);

    ImportField put(long id, long service_id, String field_name, String parent_name);

    ImportField delete(long id);


}
