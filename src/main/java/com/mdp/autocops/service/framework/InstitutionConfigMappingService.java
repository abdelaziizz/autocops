package com.mdp.autocops.service.framework;

import com.mdp.autocops.model.entity.InstitutionsConfigMapping;

import java.util.List;

public interface InstitutionConfigMappingService {

    List<InstitutionsConfigMapping> getAll();

    InstitutionsConfigMapping getById(long id);

    InstitutionsConfigMapping create(long configId, int imp_field, int exp_field);

    InstitutionsConfigMapping delete(long id);

    InstitutionsConfigMapping put(long id, long configId, int imp_field, int exp_field);

    List<InstitutionsConfigMapping> findByInstConfig(long id);


}
