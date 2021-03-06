package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;

import java.util.List;

public interface InstitutionConfigMappingService {

    List<InstitutionsConfigMapping> getAll();

    InstitutionsConfigMapping getById(long id);

    InstitutionsConfigMapping create(long configId, int imp_field_index, String type, long exp_field, long imp_field,
                                     Integer start_index, Integer last_index, Boolean required);

    InstitutionsConfigMapping delete(long id);

    InstitutionsConfigMapping put(long id, long configId, int imp_field_index, String type, long exp_field, long imp_field,
                                  int start_index, int last_index, Boolean required);

    List<InstitutionsConfigMapping> findByInstConfig(long id);

    List<ExportField> getAvailableExport(long config_id);

    List<ImportField> getAvailableImport(long config_id);


}
