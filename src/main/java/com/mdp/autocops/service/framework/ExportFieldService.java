package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.ExportField;

import java.util.List;

public interface ExportFieldService {

    List<ExportField> getAll();

    ExportField getById(long id);

    List<ExportField> getAllByService(long service_id);

    ExportField create(long service_id, String field_name);

    ExportField put(long id, long service_id, String field_name);

    ExportField delete(long id);


}
