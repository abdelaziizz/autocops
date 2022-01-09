package com.mdp.autocops.service.impl;

import com.mdp.autocops.dao.InstitutionsConfigDao;
import com.mdp.autocops.model.entity.Format;
import com.mdp.autocops.model.entity.Institution;
import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class InstitutionConfigServiceImpl implements InstitutionConfigService {

    @Autowired
    InstitutionsConfigDao institutionsConfigDao;

    @Autowired
    InstitutionConfigMappingService institutionConfigMappingService;

    @Autowired
    InstitutionService institutionService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    FormatService formatService;


    @Override
    public InstitutionConfig create(long instId, long import_format, long export_format, Boolean fail_on_error, Boolean active, long service_id) {
        InstitutionConfig institutionConfigNew = new InstitutionConfig();
        Institution inst = new Institution();
        Format importFormat = new Format();
        Format exportFormat = new Format();
        ServiceEntity service = new ServiceEntity();
        try {
            inst = institutionService.getById(instId);
            importFormat = formatService.getById(import_format);
            exportFormat = formatService.getById(export_format);
            service = serviceService.getById(service_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        institutionConfigNew.setInstitution(inst);
        institutionConfigNew.setImport_format(importFormat);
        institutionConfigNew.setExport_format(exportFormat);
        institutionConfigNew.setFail_on_error(fail_on_error);
        institutionConfigNew.setService(service);
        institutionConfigNew.setActive(active);
        try {
            if (getAvailableServices(institutionConfigNew.getInstitution().getInst_id()).contains(institutionConfigNew.getService())) {
                institutionsConfigDao.save(institutionConfigNew);
                return institutionConfigNew;
            } else {
                log.info("There is already a configuration with this service for this institution");
                return null;
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public List<InstitutionConfig> getAll() {
        List<InstitutionConfig> institutionConfigs = new ArrayList<>();
        try {
            institutionConfigs = institutionsConfigDao.findAll();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfigs;
    }

    @Override
    public InstitutionConfig getById(long id) {
        Optional<InstitutionConfig> institutionConfig = null;
        try {
            institutionConfig = institutionsConfigDao.findById(id);
            if (!institutionConfig.isPresent()) log.info("Error retrieving institution configuration");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfig.get();
    }

    @Override
    public InstitutionConfig delete(long id) {
        Optional<InstitutionConfig> institutionConfig = null;
        try {
            institutionConfig = institutionsConfigDao.findById(id);
            if (!institutionConfig.isPresent()) {
                log.info("Error retrieving institution configuration");
                return null;
            } else {
                if (institutionConfigMappingService.findByInstConfig(id).size() == 0) {
                    institutionsConfigDao.delete(institutionConfig.get());
                    return institutionConfig.get();
                } else {
                    log.info("There are dependants on this Institution's Configuration");
                    return null;
                }

            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public InstitutionConfig put(long id, long import_format, long export_format, Boolean fail_on_error, Boolean active, long service_id) {
        Optional<InstitutionConfig> institutionConfigUpdate = null;
        ServiceEntity service = serviceService.getById(service_id);
        Format importFormat = formatService.getById(import_format);
        Format exportFormat = formatService.getById(export_format);

        try {
            institutionConfigUpdate = institutionsConfigDao.findById(id);
            if (!institutionConfigUpdate.isPresent()) log.info("Error retrieving institution configuration");
            else {

                if (importFormat != null)
                    institutionConfigUpdate.get().setImport_format(importFormat);
                if (exportFormat != null)
                    institutionConfigUpdate.get().setExport_format(exportFormat);
                if (fail_on_error != null) institutionConfigUpdate.get().setFail_on_error(fail_on_error);
                if (active != null) institutionConfigUpdate.get().setActive(active);
                if (service != null) institutionConfigUpdate.get().setService(service);
                institutionsConfigDao.save(institutionConfigUpdate.get());

            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return institutionConfigUpdate.get();
    }

    @Override
    public List<InstitutionConfig> getByInst(long id) {
        List<InstitutionConfig> configs = getAll();
        List<InstitutionConfig> instConfigs = new ArrayList<>();
        for (int i = 0; i < configs.size(); i++) {
            if (configs.get(i).getInstitution().getInst_id() == id) instConfigs.add(configs.get(i));
        }
        return instConfigs;
    }

    @Override
    public List<ServiceEntity> getAvailableServices(long id) {
        List<ServiceEntity> services = serviceService.getAll();
        List<InstitutionConfig> configs = getByInst(id);
        for (int i = 0; i < configs.size(); i++) {
            if (services.contains(configs.get(i).getService())) {
                services.remove(configs.get(i).getService());
            }
        }
        return services;
    }
}
