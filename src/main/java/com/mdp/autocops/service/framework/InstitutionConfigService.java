package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;

import java.util.List;

public interface InstitutionConfigService {

    InstitutionConfig create(long id, long import_format, long export_format, Boolean fail_on_error, Boolean active, long service_id);

    List<InstitutionConfig> getAll();

    InstitutionConfig getById(long id);

    InstitutionConfig delete(long id);

    InstitutionConfig put(long id, long import_format, long export_format, Boolean fail_on_error, Boolean active, long service_id);

    List<InstitutionConfig> getByInst(long id);

    List<ServiceEntity> getAvailableServices(long id);


}
