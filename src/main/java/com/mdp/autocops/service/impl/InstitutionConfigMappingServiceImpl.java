package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.InstitutionsConfigMappingDao;
import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class InstitutionConfigMappingServiceImpl implements InstitutionConfigMappingService {

    @Autowired
    InstitutionsConfigMappingDao institutionsConfigMappingDao;

    @Autowired
    InstitutionConfigService institutionConfigService;

    @Override
    public List<InstitutionsConfigMapping> getAll() {
        List<InstitutionsConfigMapping> institutionsConfigMappings = new ArrayList<>();
        try {
            institutionsConfigMappings = institutionsConfigMappingDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionsConfigMappings;
    }

    @Override
    public InstitutionsConfigMapping getById(long id) {
        Optional<InstitutionsConfigMapping> institutionsConfigMapping = null;
        try {
            institutionsConfigMapping = institutionsConfigMappingDao.findById(id);
        } catch (Exception e) {
            log.info(e.getMessage());;
        }
        return institutionsConfigMapping.get();
    }

    @Override
    public InstitutionsConfigMapping create(long configId, int imp_field, int exp_field) {

        InstitutionConfig config = institutionConfigService.getById(configId);
        InstitutionsConfigMapping instConfigMapping = new InstitutionsConfigMapping();
        instConfigMapping.setInstitution_config(config);
        instConfigMapping.setImport_field(imp_field);
        instConfigMapping.setExport_field(exp_field);
        try {
            institutionsConfigMappingDao.save(instConfigMapping);
            return instConfigMapping;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public InstitutionsConfigMapping delete(long id) {
        Optional<InstitutionsConfigMapping> institutionsConfigMapping = null;
        try {
            institutionsConfigMapping = institutionsConfigMappingDao.findById(id);
            if (institutionsConfigMapping.isPresent())
                institutionsConfigMappingDao.delete(institutionsConfigMapping.get());
            else log.info("Error retrieving Institution Configuration Mapping");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionsConfigMapping.get();
    }

    @Override
    public InstitutionsConfigMapping put(long id, long configId, int imp_field, int exp_field) {

        Optional<InstitutionsConfigMapping> instConfigMapping = null;
        try {
            instConfigMapping = institutionsConfigMappingDao.findById(id);
            if (instConfigMapping.isPresent()) {
                InstitutionConfig config = institutionConfigService.getById(configId);
                if ( config != null ) instConfigMapping.get().setInstitution_config(config);
                Integer import_field = imp_field;
                Integer export_field = exp_field;
                if ( import_field != null ) instConfigMapping.get().setImport_field(import_field);
                if ( export_field != null ) instConfigMapping.get().setExport_field(export_field);
                institutionsConfigMappingDao.save(instConfigMapping.get());
            } else log.info("Error retrieving Institution Configuration Mapping");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return instConfigMapping.get();
    }

    public List<InstitutionsConfigMapping> findByInstConfig(long id) {
        List<InstitutionsConfigMapping> mappings = getAll();
        List<InstitutionsConfigMapping> mappingList = new ArrayList<>();
        for ( int i = 0 ; i < mappings.size() ; i++ ) {
            if (mappings.get(i).getInstitution_config().getId() == id) {
                mappingList.add(mappings.get(i));
            }
        }
        return mappingList;
    }
}
