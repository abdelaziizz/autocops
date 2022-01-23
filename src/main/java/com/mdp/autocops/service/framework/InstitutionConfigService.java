package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;

import java.util.List;

public interface InstitutionConfigService {

    InstitutionConfig create(long id, Integer reading_line, long import_format, long export_format, Boolean fail_on_error, Boolean active,
                             long service_id, String import_path, String export_path);

    List<InstitutionConfig> getAll();

    InstitutionConfig getById(long id);

    InstitutionConfig delete(long id);

    InstitutionConfig put(long id, Integer reading_line, long import_format, long export_format, Boolean fail_on_error, Boolean active,
                          long service_id, String import_path, String export_path);

    List<InstitutionConfig> getByInst(long id);

    List<ServiceEntity> getAvailableServices(long id);


}
