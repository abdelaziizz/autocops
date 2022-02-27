package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;

import java.util.List;

public interface InstitutionConfigMappingService {

    List<InstitutionsConfigMapping> getAll();

    InstitutionsConfigMapping getById(long id);

    InstitutionsConfigMapping create(long configId, int imp_field_index, long typeId, long format_id, long exp_field, long imp_field, int start_index, int last_index);

    InstitutionsConfigMapping delete(long id);

    InstitutionsConfigMapping put(long id, long configId, int imp_field_index, long typeId, long formatId, long exp_field, long imp_field, int start_index, int last_index);

    List<InstitutionsConfigMapping> findByInstConfig(long id);

    List<ExportField> getAvailableExport(long config_id);
    List<ImportField> getAvailableImport(long config_id);


}
